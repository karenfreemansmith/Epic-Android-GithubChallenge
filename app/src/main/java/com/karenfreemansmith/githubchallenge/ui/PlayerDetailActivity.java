package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonSelectPlayer)
    public void startBattle(View v) {
        Intent intent = new Intent(PlayerDetailActivity.this, BattleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonSavePlayer)
    public void savePlayer(View v) {
        Toast.makeText(this, "Player Saved", Toast.LENGTH_SHORT).show();
    }
}
