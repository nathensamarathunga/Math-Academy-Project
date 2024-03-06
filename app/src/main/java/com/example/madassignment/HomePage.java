package com.example.madassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class HomePage extends AppCompatActivity {
    private TextView highScoreTextView;
    private VideoView videoViewBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton playGameButton = findViewById(R.id.play_game_button);

        videoViewBackground = findViewById(R.id.homepage_video_1);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.homepage_background_video_2);
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

        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to start the LevelSelect activity
                Intent intent = new Intent(HomePage.this, LevelSelect.class);
                startActivity(intent);
            }

        });

    }

    private void updateHighScore() {
        // Get the score from LevelOne activity (assuming you've navigated from LevelOne to HomePage)
        LevelOne levelOneActivity = new LevelOne();
        highScoreTextView.setText(levelOneActivity.score);
    }
}