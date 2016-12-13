package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleResultsActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mPlayerId;

    @Bind(R.id.winnerImageView) ImageView mWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_results);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Random randomGenerator = new Random();
        int winner = randomGenerator.nextInt(2)+1;

        if(winner == 1) {
            mPlayerId = mSharedPreferences.getString(Constants.PLAYER1_ID, null);
        } else {
            mPlayerId = mSharedPreferences.getString(Constants.PLAYER2_ID, null);
        }
        Picasso.with(this)
                .load("https://avatars.githubusercontent.com/u/"+mPlayerId+"?v=3")
                .resize(720, 720)
                .centerCrop()
                .into(mWinner);
    }

    @OnClick(R.id.buttonPlayAgain)
    public void playAgain(View v) {
        Intent intent = new Intent(BattleResultsActivity.this, BattleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonLogout)
    public void endGame(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(BattleResultsActivity.this, NewPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
