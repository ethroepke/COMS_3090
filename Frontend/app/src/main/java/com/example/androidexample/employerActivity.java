package com.example.androidexample;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

public class employerActivity extends AppCompatActivity {
    private FrameLayout borderChange;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer); // Replace with your actual layout file name

        borderChange = findViewById(R.id.frameChange);
        checkButton = findViewById(R.id.checkButton);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Access the LayerDrawable background
                LayerDrawable layerDrawable = (LayerDrawable) borderChange.getBackground();

                // Access the border layer in the LayerDrawable
                Drawable borderDrawable = layerDrawable.getDrawable(0);

                // Check if it's a GradientDrawable
                if (borderDrawable instanceof GradientDrawable) {
                    // Cast to GradientDrawable and set the stroke color
                    GradientDrawable gradientDrawable = (GradientDrawable) borderDrawable;
                    gradientDrawable.setStroke(4, Color.GREEN); // Set border color to green
                }
            }
        });
    }
}
