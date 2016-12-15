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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.services.GithubService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlayerDetailActivity extends AppCompatActivity {
    @Bind(R.id.titleTextView) TextView mTitle;
    @Bind(R.id.playerImageView) ImageView mPlayerImage;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private DatabaseReference mDatabaseReference;
    private Player newPlayer;
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
        getPlayer(intent.getStringExtra("playername"));
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
        mDatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference()
            .child(Constants.FIREBASE_CHILD_PLAYER);
        mDatabaseReference.push().setValue(newPlayer);
        Toast.makeText(this, "Player Saved", Toast.LENGTH_SHORT).show();

    }

    private void getPlayer(String username) {
        final GithubService github = new GithubService();
        github.getPlayer(username,  new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                newPlayer = github.makePlayer(response);
                mPlayerName = newPlayer.getPlayerName();
                mPlayerId = String.valueOf(newPlayer.getPlayerId());

                PlayerDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTitle.setText(mPlayerName);
                        Picasso.with(PlayerDetailActivity.this)
                            .load("https://avatars.githubusercontent.com/u/"+ mPlayerId +"?v=3")
                            .resize(100, 100)
                            .centerCrop()
                            .into(mPlayerImage);

                    }
                });
            }
        });
    }
}
