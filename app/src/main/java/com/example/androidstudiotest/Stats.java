package com.example.androidstudiotest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Stats extends AppCompatActivity {

    private TextView slotsWins, slotsLosses, totalRounds, jackpots, chipsWon, chipsLost;
    public static int chips = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PageMovement.pageMoving(this, R.id.moveToStart, MainActivity.class);

        slotsWins = findViewById(R.id.slotsWins);
        slotsLosses = findViewById(R.id.slotsLosses);
        totalRounds = findViewById(R.id.roundsPlayed);
        jackpots = findViewById(R.id.jackpots);
        chipsWon = findViewById(R.id.chipsWon);
        chipsLost = findViewById(R.id.chipsLost);


        slotsWins.setText("Wins: " + Slots.getWins());
        slotsLosses.setText("Losses: " + Slots.getLosses());
        totalRounds.setText("Rounds played: " + Slots.getTotalRounds());
        jackpots.setText("Jackpots hit: " + Slots.getJackpots());
        chipsWon.setText("Chips won: " + Slots.getWonChips());
        chipsLost.setText("Chips lost: " + Slots.getLostChips());
    }
}