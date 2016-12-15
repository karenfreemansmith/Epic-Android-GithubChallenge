package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.adapter.PlayerListAdapter;
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

public class BattleActivity extends AppCompatActivity {
    @Bind(R.id.titleTextView) TextView mTitle;
    @Bind(R.id.player1Textview) TextView mPlayerName1;
    @Bind(R.id.player2TextView) TextView mPlayerName2;
    @Bind(R.id.player1ImageView) ImageView mPlayer1ImageView;
    @Bind(R.id.player2ImageView) ImageView mPlayer2ImageView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mCurrentPlayer;
    private String mCurrentPlayerId;
    private String mPlayer1Choice;
    private String mPlayer2Choice;
    private String mPlayer1Id;
    private String mPlayer2Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mTitle.setText("Pick your Champions!");
            }
        };

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        if (mSharedPreferences.getString(Constants.PLAYER_POSITION, null).equals("0")) {
            Player player1 = Player.getRandomStarter();
            mPlayer1Choice = player1.getPlayerName();
            mPlayer1Id = String.valueOf(player1.getPlayerId());
            mEditor.putString(Constants.PLAYER1_KEY, mPlayer1Choice).apply();
            mEditor.putString(Constants.PLAYER1_ID, String.valueOf(mPlayer1Id)).apply();

        } else {
            mPlayer1Choice = mSharedPreferences.getString(Constants.PLAYER1_KEY, null);
            mPlayer1Id = mSharedPreferences.getString(Constants.PLAYER1_ID, null);
        }
        mPlayerName1.setText(mPlayer1Choice);
        Picasso.with(this)
            .load("https://avatars.githubusercontent.com/u/" + mPlayer1Id + "?v=3")
            .resize(150, 150)
            .centerCrop()
            .into(mPlayer1ImageView);

        if(mSharedPreferences.getString(Constants.PLAYER_POSITION, null).equals("0")) {
            Player player2 = Player.getRandomStarter();
            mPlayer2Choice = player2.getPlayerName();
            mPlayer2Id = String.valueOf(player2.getPlayerId());
            mEditor.putString(Constants.PLAYER2_KEY, mPlayer2Choice).apply();
            mEditor.putString(Constants.PLAYER2_ID, String.valueOf(mPlayer2Id)).apply();
        } else {
            mPlayer2Choice = mSharedPreferences.getString(Constants.PLAYER2_KEY, null);
            mPlayer2Id = mSharedPreferences.getString(Constants.PLAYER2_ID, null);
        }

        mPlayerName2.setText(mPlayer2Choice);
        Picasso.with(this)
                .load("https://avatars.githubusercontent.com/u/"+mPlayer2Id+"?v=3")
                .resize(150, 150)
                .centerCrop()
                .into(mPlayer2ImageView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.buttonStartBattle)
    public void showResults(View v) {
        Intent intent = new Intent(BattleActivity.this, BattleResultsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonChoosePlayer1)
    public void searchPlayer1(View v) {
        setPosition("1");
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonChoosePlayer2)
    public void searchPlayer2(View v) {
        setPosition("2");
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void setPosition(String position) {
        mEditor.putString(Constants.PLAYER_POSITION, position).apply();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(BattleActivity.this, NewPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
