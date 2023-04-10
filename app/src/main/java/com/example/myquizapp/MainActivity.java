package com.example.myquizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Initializing variables
    EditText inputName;
    String userName="";
    Button startButton;
    Integer resultScore = 0;
    boolean promptNewQuiz = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find view by ID
        inputName = findViewById(R.id.nameEditText);

        startButton = findViewById(R.id.startButton);

        //Starts Quiz Activity when the start button is clicked
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userName is set to the input given by user
                userName =  inputName.getText().toString();
                inputName.setText(userName);
                Intent myIntent = new Intent(MainActivity.this, QuizActivity.class);
                myIntent.putExtra("name", userName);
                startActivityForResult(myIntent, RESULT_FIRST_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //receive data from QuizIntent (username and quiz results)
        if(resultCode == 1){
            resultScore = data.getIntExtra("score", -1);
            //send data to ResultsIntent
            Intent resultsIntent = new Intent(MainActivity.this, ResultActivity.class);
            resultsIntent.putExtra("name", userName);
            resultsIntent.putExtra("score", resultScore);
            startActivityForResult(resultsIntent, 2);
        }
        //receive data from ResultsIntent (prompt for new quiz or finish quiz)
        else if(resultCode == 2){
            userName = data.getStringExtra("name");
            promptNewQuiz = data.getBooleanExtra("newQuiz", true);

            //If user selects Finish (i.e. no new quiz prompted), close the app
            if(promptNewQuiz == false){
                finish();
        }
    }

}}
