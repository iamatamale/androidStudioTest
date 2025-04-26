package com.example.androidstudiotest;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class Slots extends AppCompatActivity {
    private TextView winStatus, chipsNumText, betAmountView;
    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button spinner, increaseBet, decreaseBet, maxBet;
    private boolean isStarted;
    private static int wins = 0;
    private static int losses = 0;
    public static final Random RANDOM = new Random();
    private int numChips = Stats.chips;
    private int betAmount = 10;
    private int winnings, currBet;
    public static long randomLong(long lower, long upper){
        return lower + (long) (RANDOM.nextDouble() * (upper-lower));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slots);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PageMovement.pageMoving(this, R.id.moveToStart, MainActivity.class);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        spinner = findViewById(R.id.spinButton);
        winStatus = findViewById(R.id.winStatus);
        chipsNumText = findViewById(R.id.chipsAmount);
        betAmountView = findViewById(R.id.betAmount);
        increaseBet = findViewById(R.id.increaseBet);
        decreaseBet = findViewById(R.id.decreaseBet);
        maxBet = findViewById(R.id.maxBet);

        chipsNumText.setText("Number of chips: "+ numChips);

        increaseBet.setOnClickListener(v -> {       //increases the bet by 10
            if (betAmount < numChips) {
                betAmount += 10;
                betAmountView.setText("Current Bet: " + betAmount);
            }
        });

        decreaseBet.setOnClickListener(v -> {       //decreases the bet by 10
            if (betAmount > 10) {
                betAmount -= 10;
                betAmountView.setText("Current Bet: " + betAmount);
            }
        });
        maxBet.setOnClickListener(v -> {            //sets bet equal to num chips
            betAmount = numChips;
            betAmountView.setText("Current Bet: " + betAmount);
        });
        spinner.setOnClickListener(v -> {       //spin button on click
            if (!isStarted) {
                if (numChips < betAmount) {
                    winStatus.setText("Not enough chips to bet!");  //check for sufficient chips
                    return;
                }

                winnings = 0;
                currBet = betAmount;        //save current bet amount
                numChips -= betAmount;      //update and display new chip amount
                chipsNumText.setText("Number of chips: " + numChips);

                // start the wheels
                wheel1 = new Wheel(img -> runOnUiThread(() -> img1.setImageResource(img)), 200, randomLong(0, 200));
                wheel1.start();

                wheel2 = new Wheel(img -> runOnUiThread(() -> img2.setImageResource(img)), 200, randomLong(0, 200));
                wheel2.start();

                wheel3 = new Wheel(img -> runOnUiThread(() -> img3.setImageResource(img)), 200, randomLong(0, 200));
                wheel3.start();

                spinner.setText("Spinning");
                winStatus.setText("");
                isStarted = true;

                new Handler().postDelayed(() -> {
                    // stop the wheels
                    wheel1.stopWheel();
                    new Handler().postDelayed(() -> wheel2.stopWheel(), 500);   //stagger the wheels stopping
                    new Handler().postDelayed(() -> wheel3.stopWheel(), 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() { //check win statuses
                            if (wheel1.isStopped && wheel2.isStopped && wheel3.isStopped) {
                                // All wheels fully stopped — now safe to read final images
                                if (wheel1.finalImageIndex == wheel2.finalImageIndex && wheel2.finalImageIndex == wheel3.finalImageIndex) {
                                    winStatus.setText("You win big");      //all 3 img match
                                    winnings = currBet * 5;
                                    wins++;
                                } else if (wheel1.finalImageIndex == wheel2.finalImageIndex || wheel2.finalImageIndex == wheel3.finalImageIndex || wheel1.finalImageIndex == wheel3.finalImageIndex) {
                                    winStatus.setText("You win small");    // 2 img match
                                    winnings = currBet * 2;
                                    wins++;
                                } else {
                                    winStatus.setText("You lose");      // no img match
                                    losses++;
                                }

                                numChips+=winnings;         //update num chips with winnings and display
                                chipsNumText.setText("Number of chips: " + numChips);
                                spinner.setText("Start");
                                isStarted = false;
                            } else {
                                // Keep checking every 50ms until wheels are done
                                new Handler().postDelayed(this, 50);
                            }
                        }
                    }, 50);
                }, 2000);
            }
        });
    }
    //@return returns number of wins
    public static int getWins(){
        return wins;
    }
    //@return returns number of losses
    public static int getLosses(){
        return losses;
    }
}