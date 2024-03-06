package com.example.madassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FinalScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        // Retrieve the score from the intent
        int score = getIntent().getIntExtra("score", 0);

        // Find the TextView
        TextView scoreTextView = findViewById(R.id.score_count_final_score);
        // Update the TextView with the score
        scoreTextView.setText(String.valueOf(score));
    }
}