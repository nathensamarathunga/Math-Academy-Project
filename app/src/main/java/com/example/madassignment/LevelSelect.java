package com.example.madassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelSelect extends AppCompatActivity {

    private VideoView videoViewBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);


        videoViewBackground = findViewById(R.id.level_select_page_video_1);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.level_select_page_background_video_1);
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

        // Button to go back to the menu
        ImageButton toMenuButton = findViewById(R.id.back_to_menu_level_select);
        toMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to start the HomePage activity
                Intent intent = new Intent(LevelSelect.this, HomePage.class);
                startActivity(intent);
            }
        });

        // Image button 1 to select level
        ImageButton levelImageButton = findViewById(R.id.image_level_0_select_page);
        levelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LevelOne activity
                Intent intent = new Intent(LevelSelect.this, LevelOne.class);
                intent.putExtra("timePerQuestion", -1);
                intent.putExtra("level", 1);
                startActivity(intent);
            }
        });

        // Image button 2 to select level
        ImageButton level1ImageButton = findViewById(R.id.image_level_1_select_page);
        level1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LevelOne activity
                Intent intent = new Intent(LevelSelect.this, LevelOne.class);
                intent.putExtra("timePerQuestion", 20);
                startActivity(intent);
            }
        });

        // Image button 3 to select level
        ImageButton level2ImageButton = findViewById(R.id.image_level_2_select_page);
        level2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LevelOne activity
                Intent intent = new Intent(LevelSelect.this, LevelOne.class);
                intent.putExtra("timePerQuestion", 10);
                startActivity(intent);
            }
        });
    }
}
