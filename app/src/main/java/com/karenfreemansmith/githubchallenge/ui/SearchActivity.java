package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.karenfreemansmith.githubchallenge.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonGithubSearch)
    public void showPlayer(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowers)
    public void listFollowers(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonFollowing)
    public void listFollowing(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonFavoritePlayers)
    public void listFavorites(View v) {
        Intent intent = new Intent(SearchActivity.this, PlayerListActivity.class);
        startActivity(intent);
    }
}
