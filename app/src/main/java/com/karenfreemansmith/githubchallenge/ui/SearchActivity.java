package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.editSearchGithubPlayer) EditText mGithubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonGithubSearch)
    public void showPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerDetailActivity.class);
        if(mGithubName != null) {
            intent.putExtra("playername", mGithubName.getText().toString());
        } else {
            intent.putExtra("playername", "karenfreemansmith");
        }
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowers)
    public void listFollowers(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        if(mGithubName != null) {
            intent.putExtra("playername", mGithubName.getText().toString());
        } else {
            intent.putExtra("playername", "karenfreemansmith");
        }
        intent.putExtra("type", "followers");
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowing)
    public void listFollowing(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        if(mGithubName != null) {
            intent.putExtra("playername", mGithubName.getText().toString());
        } else {
            intent.putExtra("playername", "karenfreemansmith");
        }
        intent.putExtra("type", "following");
        startActivity(intent);
    }

    @OnClick(R.id.buttonFavoritePlayers)
    public void listFavorites(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        intent.putExtra("playername", "karenfreemansmith");
        intent.putExtra("type", "favorites");
        startActivity(intent);
    }
}
