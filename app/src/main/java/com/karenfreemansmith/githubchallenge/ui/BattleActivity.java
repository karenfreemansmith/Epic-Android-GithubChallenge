package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mCurrentPlayer;
    private String mPlayer1Choice;
    private String mPlayer2Choice;

    @Bind(R.id.player1Textview) TextView mPlayerName1;
    @Bind(R.id.player2TextView) TextView mPlayerName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentPlayer = mSharedPreferences.getString(Constants.LOGGED_IN_PLAYER_KEY, "wycats");
        mPlayer1Choice = mSharedPreferences.getString(Constants.PLAYER1_KEY, null);
        mPlayer2Choice = mSharedPreferences.getString(Constants.PLAYER2_KEY, null);

        if(mPlayer1Choice != null) {
            mPlayerName1.setText(mPlayer1Choice);
        } else {
            mPlayerName1.setText(mCurrentPlayer);
        }
        if(mPlayer2Choice != null) {
            mPlayerName2.setText(mPlayer2Choice);
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
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        intent.putExtra("playerPosition", "1");
        startActivity(intent);
    }

    @OnClick(R.id.buttonChoosePlayer2)
    public void searchPlayer2(View v) {
        Intent intent = new Intent(BattleActivity.this, SearchActivity.class);
        intent.putExtra("playerPosition", "2");
        startActivity(intent);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(BattleActivity.this, NewPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
