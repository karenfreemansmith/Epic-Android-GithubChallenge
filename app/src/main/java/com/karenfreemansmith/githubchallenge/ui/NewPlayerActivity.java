package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonNewPlayer)
    public void showResults(View v) {
        Intent intent = new Intent(NewPlayerActivity.this, BattleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonLogin)
    public void showLogin(View v) {
        Intent intent = new Intent(NewPlayerActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
