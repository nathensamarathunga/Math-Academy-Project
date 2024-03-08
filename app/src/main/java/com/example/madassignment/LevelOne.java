package com.example.madassignment;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.media.MediaPlayer;
import android.os.Bundle;


public class LevelOne extends AppCompatActivity {

    private VideoView videoViewBackground;
    private TextView textViewLeftNumber, textViewOperator, textViewRightNumber;
    private Button buttonChoice1, buttonChoice2, buttonChoice3;
    private int num1, num2, result, correctAnswer;
    public int score = 0;
    private int questionCount = 1;
    private TextView questionCountTextView;
    private boolean quizEnded = false;
    private int timePerQuestion; // Time per question in seconds
    private CountDownTimer timer;
    private TextView textViewTimer;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;


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
        textViewTimer = findViewById(R.id.textViewTimer);
        correctSound = MediaPlayer.create(this, R.raw.correct_answer);
        wrongSound = MediaPlayer.create(this, R.raw.wrong_answer);


        // Receive time variable from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            timePerQuestion = extras.getInt("timePerQuestion");
        }

        // Set up video background
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_level_01);
        videoViewBackground.setVideoURI(videoUri);
        videoViewBackground.start();
        videoViewBackground.setOnCompletionListener(mediaPlayer -> videoViewBackground.start());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        videoViewBackground.setLayoutParams(layoutParams);

        // Set up exit button
        Button exitButton = findViewById(R.id.to_menu_level_one_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelOne.this, LevelSelect.class);
                startActivity(intent);
                finish();
            }
        });

        updateQuestionCount();
        generateQuestion();

        // Set up button click listeners
        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedAnswer = Integer.parseInt(buttonChoice1.getText().toString());
                if (selectedAnswer == correctAnswer) {
                    playCorrectSound();
                } else {
                    playWrongSound();
                }
                // Add a delay of 500 milliseconds before checking the answer
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAnswer(selectedAnswer);
                    }
                }, 500);
            }
        });

        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedAnswer = Integer.parseInt(buttonChoice2.getText().toString());
                if (selectedAnswer == correctAnswer) {
                    playCorrectSound();
                } else {
                    playWrongSound();
                }
                // Add a delay of 500 milliseconds before checking the answer
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAnswer(selectedAnswer);
                    }
                }, 500);
            }
        });

        buttonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedAnswer = Integer.parseInt(buttonChoice3.getText().toString());
                if (selectedAnswer == correctAnswer) {
                    playCorrectSound();
                } else {
                    playWrongSound();
                }
                // Add a delay of 500 milliseconds before checking the answer
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAnswer(selectedAnswer);
                    }
                }, 500);
            }
        });

        //start timer

        if (timePerQuestion != -1) {
            // For other levels, start the timer
            startTimer();
        } else {
            // For level 1, indicate unlimited time
            textViewTimer.setText("Time : ∞");
        }
    }

    private void startTimer() {
        if (timePerQuestion != -1) {
            if (timer != null) {
                timer.cancel();
            }
            timer = new CountDownTimer(timePerQuestion * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Update timer display
                    textViewTimer.setText("Time : " + millisUntilFinished / 1000 + "s");
                }

                public void onFinish() {
                    // Time's up, handle accordingly
                    textViewTimer.setText("Time's Up!");
                    checkAnswer(-1); // Pass -1 as answer to indicate time's up
                }
            }.start();
        }
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

        // Start the timer for the next question
        startTimer();
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
        if (timePerQuestion != -1) {
            if (timer != null) {
                timer.cancel();
            }
        }

        if (answer == correctAnswer) {
            score += 10; // Increase score by 10 for each correct answer
        }

        if (questionCount >= 10 && !quizEnded) {
            showFinalScore();
        } else if (!quizEnded) {
            generateQuestion();
            questionCount++;
            updateQuestionCount();
            if (timePerQuestion != -1) {
                startTimer();
            }
        }
    }

    private void showFinalScore() {
        quizEnded = true;
        Intent intent = new Intent(LevelOne.this, FinalScore.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    private void updateQuestionCount() {
        questionCountTextView.setText("Question : " + questionCount);
    }

    private void playCorrectSound() {
        // Your code to play the correct sound
        if (correctSound != null) {
            correctSound.start();
        }
    }

    private void playWrongSound() {
        // Your code to play the wrong sound
        if (wrongSound != null) {
            wrongSound.start();
        }
    }

}
