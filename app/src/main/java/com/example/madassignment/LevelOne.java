package com.example.madassignment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.VideoView;
import android.widget.RelativeLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


import java.util.Arrays;
import java.util.Random;

public class LevelOne extends AppCompatActivity {

    private VideoView videoViewBackground;
    private TextView textViewLeftNumber, textViewOperator, textViewRightNumber;
    private Button buttonChoice1, buttonChoice2, buttonChoice3;
    private int num1, num2, result, correctAnswer;
    public int score = 0;
    private int questionCount = 1;
    private TextView questionCountTextView;
    private boolean quizEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);

        videoViewBackground = findViewById(R.id.level_background_video);
        textViewLeftNumber = findViewById(R.id.textViewLeftNumber);
        textViewOperator = findViewById(R.id.textViewOperator);
        textViewRightNumber = findViewById(R.id.textViewRightNumber);
        buttonChoice1 = findViewById(R.id.buttonChoice1);
        buttonChoice2 = findViewById(R.id.buttonChoice2);
        buttonChoice3 = findViewById(R.id.buttonChoice3);
        questionCountTextView = findViewById(R.id.question_counter_l1);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_level_01);
        videoViewBackground.setVideoURI(videoUri);
        videoViewBackground.start();

        // Loop the video
        videoViewBackground.setOnCompletionListener(mediaPlayer -> videoViewBackground.start());

        // Set the layout parameters of VideoView to match the parent (fill the screen)
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        videoViewBackground.setLayoutParams(layoutParams);

        Button exitButton = findViewById(R.id.to_menu_level_one_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to start the HomePage activity - modified to level select
                Intent intent = new Intent(LevelOne.this, LevelSelect.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity to prevent the user from going back
            }
        });

        updateQuestionCount();
        generateQuestion();

        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(Integer.parseInt(buttonChoice1.getText().toString()));
            }
        });

        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(Integer.parseInt(buttonChoice2.getText().toString()));
            }
        });

        buttonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(Integer.parseInt(buttonChoice3.getText().toString()));
            }
        });
    }

    private void generateQuestion() {
        Random random = new Random();
        num1 = random.nextInt(12) + 1;
        num2 = random.nextInt(12) + 1;
        String[] operators = {"+", "×", "÷"};

        if (questionCount > 10) {
            return;
        }

        // Ensure subtraction operation doesn't produce negative results
        if (num2 > num1 && Arrays.asList(operators).contains("-")) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        String operator = operators[random.nextInt(operators.length)];

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "×":
                result = num1 * num2;
                break;
            case "÷":
                // Ensure division produces whole numbers
                while (num1 % num2 != 0) {
                    num1 = random.nextInt(12) + 1;
                    num2 = random.nextInt(12) + 1;
                }
                result = num1 / num2;
                break;
        }

        textViewLeftNumber.setText(String.valueOf(num1));
        textViewOperator.setText(operator);
        textViewRightNumber.setText(String.valueOf(num2));

        correctAnswer = result;

        // Generate random choices
        int[] choices = {correctAnswer, correctAnswer + 1, correctAnswer - 1};
        shuffleArray(choices);

        buttonChoice1.setText(String.valueOf(choices[0]));
        buttonChoice2.setText(String.valueOf(choices[1]));
        buttonChoice3.setText(String.valueOf(choices[2]));
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void checkAnswer(int answer) {
        if (answer == correctAnswer) {
            score += 10; // Increase score by 10 for each correct answer
        }

        if (questionCount >= 10 && !quizEnded) { // Check if the 10th question is reached and the quiz hasn't ended
            showFinalScore(); // Start FinalScore activity
        } else if (!quizEnded) { // Generate a new question only if the quiz hasn't ended
            generateQuestion();
            questionCount++; // Increment question count after generating a new question
            updateQuestionCount();
        }
    }


    private void showFinalScore() {
        quizEnded = true; // Set quizEnded to true to prevent further questions
        // Start a new activity to display the final score
        Intent intent = new Intent(LevelOne.this, FinalScore.class);
        intent.putExtra("score", score); // Pass the score to the new activity
        startActivity(intent);
        finish(); // Finish the current activity to prevent the user from going back
    }


    private void updateQuestionCount() {
        // Update the question count TextView
        questionCountTextView.setText("Question : " + questionCount);
    }


    /*
    private void showScorePopup() {
        if (score > 60) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Great Job!")
                    .setMessage("Your total score: " + score)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Reset variables and finish activity or perform other actions
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Try Harder!")
                    .setMessage("Your total score: " + score)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Reset variables and finish activity or perform other actions
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }
    */

}
