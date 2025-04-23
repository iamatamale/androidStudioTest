package com.example.androidstudiotest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class page2 extends AppCompatActivity {
    private Button returnToStart;
    private TextView winStatus;
    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button spinner;
    private boolean isStarted;
    private int wins = 0;
    private int losses = 0;
    public static final Random RANDOM = new Random();
    public static long randomLong(long lower, long upper){
        return lower + (long) (RANDOM.nextDouble() * (upper-lower));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PageMovement.pageMoving(this, R.id.moveToStart, MainActivity.class);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        spinner = (Button) findViewById(R.id.spinButton);
        winStatus = (TextView) findViewById(R.id.winStatus);

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    // Start the wheels
                    wheel1 = new Wheel(img -> runOnUiThread(() -> img1.setImageResource(img)), 200, randomLong(0, 200));
                    wheel1.start();

                    wheel2 = new Wheel(img -> runOnUiThread(() -> img2.setImageResource(img)), 200, randomLong(150, 400));
                    wheel2.start();

                    wheel3 = new Wheel(img -> runOnUiThread(() -> img3.setImageResource(img)), 200, randomLong(150, 400));
                    wheel3.start();

                    spinner.setText("Spinning");
                    winStatus.setText("");
                    isStarted = true;

                    new Handler().postDelayed(() -> {
                        wheel1.stopWheel();
                        wheel2.stopWheel();
                        wheel3.stopWheel();

                        if (wheel1.currentIndex == wheel2.currentIndex && wheel2.currentIndex == wheel3.currentIndex) {
                            winStatus.setText("You win big");
                            wins++;
                        } else if (wheel1.currentIndex == wheel2.currentIndex ||
                                wheel2.currentIndex == wheel3.currentIndex ||
                                wheel1.currentIndex == wheel3.currentIndex) {
                            winStatus.setText("You win small");
                            wins++;
                        } else {
                            winStatus.setText("You lose");
                            losses++;
                        }

                        spinner.setText("Start");
                        isStarted = false;
                    }, 2000);
                }
            }
        });
    }
    //@return returns number of wins
    public int getWins(){
        return wins;
    }
    //@return returns number of losses
    public int getLosses(){
        return losses;
    }
}