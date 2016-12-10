package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonPlayerDetails)
    public void showDetails(View v) {
        Intent intent = new Intent(PlayerListActivity.this, PlayerDetailActivity.class);
        startActivity(intent);
    }
}
