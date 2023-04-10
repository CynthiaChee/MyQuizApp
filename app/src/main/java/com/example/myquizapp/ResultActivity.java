package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    //Initializing variables
    Integer myScore;
    String userName;
    TextView congratulationsText, yourScoreText, finalScoreText;
    Button newQuizButton, finishButton;
    Boolean promptNewQuiz; //Checks whether the user wants to start a new quiz or to finish. True if new quiz, False if finish.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Find view by ID
        congratulationsText = findViewById(R.id.congratulationsText);
        yourScoreText = findViewById(R.id.yourScoreText);
        finalScoreText = findViewById(R.id.finalScoreText);
        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        //Get userName and user's score from intent and set messages using data obtained
        userName = getIntent().getStringExtra("name");
        myScore = getIntent().getIntExtra("score", -1);
        congratulationsText.setText("Congratulations, " + userName + "!");
        finalScoreText.setText(myScore.toString() + "/5");
    }

    public void promptNewQuiz(View view){
        //set promptNewQuiz to true and send information via intent to MainActivity to prompt a new quiz
        promptNewQuiz = true;
        Intent newQuizIntent  = new Intent(ResultActivity.this, MainActivity.class);
        newQuizIntent.putExtra("newQuiz", promptNewQuiz);
        newQuizIntent.putExtra("name", userName);
        setResult(2, newQuizIntent);
        finish();
    }

    public void finishQuiz(View view){
        //set promptNewQuiz to false and send information via intent to MainActivity to finish the quiz
        promptNewQuiz = false;
        Intent finishIntent = new Intent(ResultActivity.this, MainActivity.class);
        finishIntent.putExtra("newQuiz", promptNewQuiz);
        setResult(2, finishIntent);
        finish();
    }
}
