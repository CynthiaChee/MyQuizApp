package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    //Initializing variables
    Integer myScore = 0, currentQ = 1;
    ProgressBar myProgressBar;
    TextView welcomeMsg, qNumber, qCategory, qDesc;
    Button answerOne, answerTwo, answerThree, submitButton, selectedAns;
    String[][] qBank = new String[5][]; //question bank containing string arrays of questions + answers
    String correctAnswer;
    Button[] answerButtons = new Button[3]; //array of answer buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Find view by ID
        welcomeMsg = findViewById(R.id.WelcomeMsg);
        myProgressBar = findViewById(R.id.progressBar);
        qNumber = findViewById(R.id.qNumber);
        qCategory = findViewById(R.id.qCategory);
        qDesc = findViewById(R.id.qDesc);

        //Find view by ID for each answer button and setting the tags
        answerOne = findViewById(R.id.answerOne);
        answerButtons[0] = answerOne;
        answerOne.setTag(1);

        answerTwo = findViewById(R.id.answerTwo);
        answerButtons[1] = answerTwo;
        answerTwo.setTag(2);

        answerThree = findViewById(R.id.answerThree);
        answerButtons[2] = answerThree;
        answerThree.setTag(3);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setTag(0);

        /* qBank is a multi-dimensional array with 5 questions (questions are set in strings.xml
        Format goes as such:
        qBank[q-index][0] = Category
        qBank[q-index][1] = Question details
        qBank[q-index][2] = Choice 1
        qBank[q-index][3] = Choice 2
        qBank[q-index][4] = Choice 3
        qBank[q-index][1] = Correct answer
         */
        qBank[0] = getResources().getStringArray(R.array.firstQ);
        qBank[1] = getResources().getStringArray(R.array.secondQ);
        qBank[2] = getResources().getStringArray(R.array.thirdQ);
        qBank[3] = getResources().getStringArray(R.array.fourthQ);
        qBank[4] = getResources().getStringArray(R.array.fifthQ);

        loadQuiz();
    }

    public void loadQuiz(){
        //Get userName from intent and place it in the welcome message
        String welcomeTextString = "Welcome " + getIntent().getStringExtra("name") + "!";
        welcomeMsg.setText(welcomeTextString);

        //setting the current question and answers
        qCategory.setText(qBank[currentQ-1][0]);
        qDesc.setText(qBank[currentQ-1][1]);
        answerOne.setText(qBank[currentQ-1][2]);
        answerTwo.setText(qBank[currentQ-1][3]);
        answerThree.setText(qBank[currentQ-1][4]);
        correctAnswer = qBank[currentQ-1][5];

        //setting the progress bar and current question number
        myProgressBar.setProgress(currentQ);
        qNumber.setText(currentQ.toString() + "/5");

        //clear button colours for next question
        answerOne.setBackgroundColor(getResources().getColor(R.color.answer_button_color));
        answerTwo.setBackgroundColor(getResources().getColor(R.color.answer_button_color));
        answerThree.setBackgroundColor(getResources().getColor(R.color.answer_button_color));
    }

    @Override
    public void onClick(View view) {
        //if answer 1, 2 or 3 selected, highlight the button with selected answer colour
        if(view.getId() == R.id.answerOne){
            answerOne.setBackgroundColor(getResources().getColor(R.color.selected_answer_color));
            selectedAns = answerOne;
        }
        else if(view.getId() == R.id.answerTwo){
            answerTwo.setBackgroundColor(getResources().getColor(R.color.selected_answer_color));
            selectedAns = answerTwo;
        }
        else if(view.getId() == R.id.answerThree){
            answerThree.setBackgroundColor(getResources().getColor(R.color.selected_answer_color));
            selectedAns = answerThree;
        }
    }

    public void submitButtonClicked(View view){
        //check whether submit button is clicked via the button tag
        Integer submitTag = Integer.parseInt(view.getTag().toString());
        Integer selectedAnswer;

        //if submit button is clicked
        if (submitTag == 0) {
            //get user's selected answer and compare it with the correct answer
            //change button colour to green or red if correct/incorrect and show message with Toast
            selectedAnswer = Integer.parseInt(selectedAns.getTag().toString());
            if (selectedAnswer == Integer.parseInt(qBank[currentQ-1][5])) {
                selectedAns.setBackgroundColor(getResources().getColor(R.color.correct_answer_color));
                Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                myScore++;
            }
            else {
                selectedAns.setBackgroundColor(getResources().getColor(R.color.incorrect_answer_color));
                answerButtons[Integer.parseInt(qBank[currentQ-1][5])-1].setBackgroundColor(getResources().getColor(R.color.correct_answer_color));
                Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            }

            //change button label to "NEXT" (for next question) and change button tag
            submitButton.setText("NEXT");
            submitButton.setTag(1);
        }
        //if next button selected
        else
        {
            //clear the selection from previous answer, increment current question
            selectedAns = null;
            currentQ++;

            //ensure only up to 5 questions are displayed, else relay results back to MainActivity
            if(currentQ <= 5){
                submitButton.setText("SUBMIT");
                submitButton.setTag(0);
                loadQuiz();
            }
            else {
                submitButton.setTag(3);
                Intent myResultsIntent = new Intent(QuizActivity.this, MainActivity.class);
                myResultsIntent.putExtra("score", myScore);
                setResult(RESULT_FIRST_USER, myResultsIntent);
                finish();
            }
        }
    }
}
