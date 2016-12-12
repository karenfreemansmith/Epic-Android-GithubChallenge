package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mCurrentPlayer;
    private String mPlayerPosition;

    @Bind(R.id.editSearchGithubPlayer) EditText mGithubName;
    @Bind(R.id.titleTextView) TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mPlayerPosition = intent.getStringExtra("playerPosition");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentPlayer = mSharedPreferences.getString(Constants.LOGGED_IN_PLAYER_KEY, null);
        if(mCurrentPlayer != null) {
            mTitle.setText(mCurrentPlayer);
        } else {
            mCurrentPlayer="karenfreemansmith";
        }
    }

    @OnClick(R.id.buttonGithubSearch)
    public void showPlayer(View v) {
        String searchPlayerName;
        if(mGithubName.getText().toString().equals("")) {
            searchPlayerName = mCurrentPlayer;
        } else {
            searchPlayerName = mGithubName.getText().toString().trim();
        }
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + searchPlayerName));
        startActivity(webIntent);
    }

    @OnClick(R.id.buttonSelectPlayer)
    public void pickPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, BattleActivity.class);
        String searchPlayerName;
        if(mGithubName.getText().toString().equals("")) {
            searchPlayerName = mCurrentPlayer;
        } else {
            searchPlayerName = mGithubName.getText().toString().trim();
        }
        Log.d("add player", searchPlayerName);
        Log.d("player position", mPlayerPosition);
        addPlayer(searchPlayerName, mPlayerPosition);
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowers)
    public void listFollowers(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        if(mGithubName.getText().toString().equals("")) {
            intent.putExtra("playername", mCurrentPlayer);
        } else {
            intent.putExtra("playername", mGithubName.getText().toString());
        }
        intent.putExtra("type", "followers");
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowing)
    public void listFollowing(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        if(mGithubName.getText().toString().equals("")) {
            intent.putExtra("playername", mCurrentPlayer);
        } else {
            intent.putExtra("playername", mGithubName.getText().toString());
        }
        intent.putExtra("type", "following");
        startActivity(intent);
    }

    @OnClick(R.id.buttonFavoritePlayers)
    public void listFavorites(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        intent.putExtra("playername", mCurrentPlayer);
        intent.putExtra("type", "favorites");
        startActivity(intent);
    }

    private void addPlayer(String playerName, String playerPosition) {
        if(playerPosition.equals("1")) {
            mEditor.putString(Constants.PLAYER1_KEY, playerName);
        } else {
            mEditor.putString(Constants.PLAYER2_KEY, playerName);
        }
    }
}
