package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_results);
        ButterKnife.bind(this);
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
