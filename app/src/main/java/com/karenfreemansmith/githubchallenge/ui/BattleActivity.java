package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonStartBattle)
    public void showResults(View v) {
        Intent intent = new Intent(BattleActivity.this, BattleResultsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonChoosePlayer1)
    public void searchPlayer1(View v) {
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonChoosePlayer2)
    public void searchPlayer2(View v) {
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}
