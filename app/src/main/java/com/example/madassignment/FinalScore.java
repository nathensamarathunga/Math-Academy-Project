package com.example.madassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;


public class FinalScore extends AppCompatActivity {

    private VideoView videoViewBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        Button backButton = findViewById(R.id.back_to_home_button);

        videoViewBackground = findViewById(R.id.confetti_final_score_page_video);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.confetti_video_final_score_page);
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

        // Retrieve the score from the intent
        int score = getIntent().getIntExtra("score", 0);

        // Find the TextView
        TextView scoreTextView = findViewById(R.id.score_count_final_score);

        // Update the TextView with the score
        scoreTextView.setText(String.valueOf(score));

        // Find the well done TextView
        TextView wellDoneTextView = findViewById(R.id.well_done_text);

        // Update the well done TextView based on the score
        if (score < 50) {
            wellDoneTextView.setText("Try Harder");
        } else {
            wellDoneTextView.setText("Good Job!");
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define an Intent to navigate back to the HomePage activity
                Intent intent = new Intent(FinalScore.this, HomePage.class);
                // Start the HomePage activity
                startActivity(intent);
                // Finish the current activity (FinalScore)
                finish();
            }
        });
    }
}
