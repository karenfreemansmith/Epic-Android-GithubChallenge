package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.adapter.PlayerListAdapter;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.services.GithubService;


import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class PlayerListActivity extends AppCompatActivity {
    private static final String TAG = PlayerListActivity.class.getSimpleName();
    private PlayerListAdapter mAdapter;

    @Bind(R.id.titleTextView) TextView mTitle;
    @Bind(R.id.recyclerView) RecyclerView mPlayerListView;

    public ArrayList<Player> mPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String playername = intent.getStringExtra("playername");
        String listType = intent.getStringExtra("type");
        mTitle.setText(listType + ": " + playername);

        getPlayers(playername, listType);
    }

    @OnClick(R.id.buttonPlayerDetails)
    public void showDetails(View v) {
        Intent intent = new Intent(PlayerListActivity.this, PlayerDetailActivity.class);
        startActivity(intent);
    }

    private void getPlayers(String username, String type) {
        final GithubService github = new GithubService();
        github.getPlayers(username, type,  new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPlayers=github.getPlayerList(response);
                PlayerListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new PlayerListAdapter(getApplicationContext(), mPlayers);
                        mPlayerListView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PlayerListActivity.this);
                        mPlayerListView.setLayoutManager(layoutManager);
                        mPlayerListView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
