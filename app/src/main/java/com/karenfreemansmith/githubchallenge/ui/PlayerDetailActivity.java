package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerDetailActivity extends AppCompatActivity {
    @Bind(R.id.titleTextView) TextView mTitle;
    @Bind(R.id.playerImageView) ImageView mPlayerImage;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mPlayerName;
    private String mPlayerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        Intent intent = getIntent();
        mPlayerName = intent.getStringExtra("playername");
        mPlayerId = intent.getStringExtra("playerid");

        mTitle.setText(mPlayerName);
        Picasso.with(this)
                .load("https://avatars.githubusercontent.com/u/"+mPlayerId+"?v=3")
                .resize(100, 100)
                .centerCrop()
                .into(mPlayerImage);
    }

    @OnClick(R.id.buttonGithub)
    public void viewGithub(View v) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + mPlayerName));
        startActivity(webIntent);
    }

    @OnClick(R.id.buttonSelectPlayer)
    public void startBattle(View v) {
        Intent intent = new Intent(PlayerDetailActivity.this, BattleActivity.class);
        String playerPosition = mSharedPreferences.getString(Constants.PLAYER_POSITION, null);
        if(playerPosition.equals("1")) {
            mEditor.putString(Constants.PLAYER1_KEY, mPlayerName).apply();
            mEditor.putString(Constants.PLAYER1_ID, String.valueOf(mPlayerId)).apply();
        } else {
            mEditor.putString(Constants.PLAYER2_KEY, mPlayerName).apply();
            mEditor.putString(Constants.PLAYER2_ID, String.valueOf(mPlayerId)).apply();
        }
        startActivity(intent);
    }

    @OnClick(R.id.buttonSavePlayer)
    public void savePlayer(View v) {
        Toast.makeText(this, "Player Saved", Toast.LENGTH_SHORT).show();
    }
}
