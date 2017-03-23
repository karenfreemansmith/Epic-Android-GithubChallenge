package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.karenfreemansmith.githubchallenge.adapter.PlayerListAdapter;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.services.GithubService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        StrictMode.enableDefaults();

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
                getPlayer(s);
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

    @OnClick(R.id.buttonPlayerDetails)
    public void showPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerDetailActivity.class);
        intent.putExtra("playername", mCurrentPlayer);
        intent.putExtra("playerid", mCurrentPlayerId);
        startActivity(intent);
    }

    @OnClick(R.id.buttonSelectPlayer)
    public void pickPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, BattleActivity.class);
        Player newPlayer = Player.getRandomStarter();
        addPlayer(newPlayer.getPlayerName(), String.valueOf(newPlayer.getPlayerId()));
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

    private void addPlayer(String playerName, String playerId) {
        if(mPlayerPosition.equals("1")) {
            mEditor.putString(Constants.PLAYER1_KEY, playerName).apply();
            mEditor.putString(Constants.PLAYER1_ID, playerId).apply();
        } else {
            mEditor.putString(Constants.PLAYER2_KEY, playerName).apply();
            mEditor.putString(Constants.PLAYER2_ID, playerId).apply();
        }
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
                final Player newPlayer = github.makePlayer(response);
                mCurrentPlayer = newPlayer.getPlayerName();
                mCurrentPlayerId = String.valueOf(newPlayer.getPlayerId());
                if(mPlayerPosition.equals("1")) {
                    mEditor.putString(Constants.PLAYER1_KEY, mCurrentPlayer).apply();
                    mEditor.putString(Constants.PLAYER1_ID, mCurrentPlayerId).apply();
                } else {
                    mEditor.putString(Constants.PLAYER2_KEY, mCurrentPlayer).apply();
                    mEditor.putString(Constants.PLAYER2_ID, mCurrentPlayerId).apply();
                }
                SearchActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTitle.setText(mCurrentPlayer);
                        Picasso.with(SearchActivity.this)
                            .load("https://avatars.githubusercontent.com/u/"+ mCurrentPlayerId +"?v=3")
                            .resize(100, 100)
                            .centerCrop()
                            .into(mPlayerImageView);

                    }
                });
            }
        });
    }
}
