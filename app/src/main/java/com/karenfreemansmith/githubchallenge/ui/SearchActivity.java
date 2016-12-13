package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mCurrentPlayer;
    private String mPlayerPosition;
    private String mCurrentPlayerId;

    @Bind(R.id.titleTextView) TextView mTitle;
    @Bind(R.id.playerImageView) ImageView mPlayerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPlayerPosition = mSharedPreferences.getString(Constants.PLAYER_POSITION, null);
        if(mPlayerPosition.equals("1")) {
            mCurrentPlayer = mSharedPreferences.getString(Constants.PLAYER1_KEY, null);
            mCurrentPlayerId = mSharedPreferences.getString(Constants.PLAYER1_ID, null);
        } else {
            mCurrentPlayer = mSharedPreferences.getString(Constants.PLAYER2_KEY, null);
            mCurrentPlayerId = mSharedPreferences.getString(Constants.PLAYER2_ID, null);
        }
        mTitle.setText(mCurrentPlayer);
        Picasso.with(this)
                .load("https://avatars.githubusercontent.com/u/"+mCurrentPlayerId+"?v=3")
                .resize(100, 100)
                .centerCrop()
                .into(mPlayerImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                mCurrentPlayer = s;
                if(mCurrentPlayer == null) {
                    mCurrentPlayer="karenfreemansmith";
                }
                mTitle.setText(mCurrentPlayer);
                Picasso.with(SearchActivity.this)
                        .load("https://avatars.githubusercontent.com/u/"+1232456+"?v=3")
                        .resize(100, 100)
                        .centerCrop()
                        .into(mPlayerImageView);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.buttonGithubSearch)
    public void showPlayer(View v) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + mCurrentPlayer));
        startActivity(webIntent);
    }

    @OnClick(R.id.buttonSelectPlayer)
    public void pickPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, BattleActivity.class);
        addPlayer(mCurrentPlayer, mPlayerPosition);
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowers)
    public void listFollowers(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        intent.putExtra("playername", mCurrentPlayer);
        intent.putExtra("type", "followers");
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowing)
    public void listFollowing(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        intent.putExtra("playername", mCurrentPlayer);
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
            mEditor.putString(Constants.PLAYER1_KEY, playerName).apply();
        } else {
            mEditor.putString(Constants.PLAYER2_KEY, playerName).apply();
        }
    }
}
