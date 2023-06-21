package com.example.mobile_1_trial;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;


public class SplashActivity extends AppCompatActivity {

    private static final int ANIMATION_DURATION = 5000; // 2 seconds

    private TextView logoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoTextView = findViewById(R.id.textViewAppName);

        // Animate the logo
        animateLogo();
    }

    private void animateLogo() {
        // Define the translation distance for the logo (in pixels)
        float distanceInPixels = getResources().getDisplayMetrics().density * 100; // 100dp

        // Create the animator objects
        ObjectAnimator slideInAnimator = ObjectAnimator.ofFloat(logoTextView, "translationY", -distanceInPixels, 0);
        ObjectAnimator jumpAnimator = ObjectAnimator.ofFloat(logoTextView, "translationY", 0, -distanceInPixels, 0);

        // Set the durations for the animators
        slideInAnimator.setDuration(ANIMATION_DURATION);
        jumpAnimator.setDuration(ANIMATION_DURATION / 2);

        // Create an animator set to play the animators sequentially
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(slideInAnimator, jumpAnimator);

        // Set an animation listener to start the next activity when animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Animation ended
                // Start the next activity here (e.g., MainActivity)
                 Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                 startActivity(intent);
                 finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Animation canceled
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                // Animation repeated
            }
        });

        // Start the animation
        animatorSet.start();
    }
}
