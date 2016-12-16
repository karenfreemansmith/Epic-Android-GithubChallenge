package com.karenfreemansmith.githubchallenge.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.adapter.FirebasePlayerListAdapter;
import com.karenfreemansmith.githubchallenge.adapter.FirebasePlayerViewHolder;
import com.karenfreemansmith.githubchallenge.adapter.PlayerListAdapter;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.services.GithubService;
import com.karenfreemansmith.githubchallenge.util.ItemTouchHelperAdapter;
import com.karenfreemansmith.githubchallenge.util.OnStartDragListener;
import com.karenfreemansmith.githubchallenge.util.SimpleItemTouchHelperCallback;


import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class PlayerListActivity extends AppCompatActivity implements OnStartDragListener {
    private static final String TAG = PlayerListActivity.class.getSimpleName();
    private PlayerListAdapter mAdapter;
    private DatabaseReference mPlayerReference;
    private FirebasePlayerListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

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
        if(listType.equals("following")) {
            mTitle.setText(playername + " follows: ");
            getPlayers(playername, listType);
        } else if(listType.equals("followers")) {
            mTitle.setText("Who follows " + playername + "?");
            getPlayers(playername, listType);
        } else if(listType.equals("favorites")) {
            mTitle.setText("Your Saved Favorites");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            mPlayerReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_PLAYER)
                .child(uid);

            setUpFirebaseAdapter();
        }
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebasePlayerListAdapter(Player.class, R.layout.drag_player_item, FirebasePlayerViewHolder.class,
                mPlayerReference, this, this){
            @Override
            protected void populateViewHolder(FirebasePlayerViewHolder viewHolder, Player model, int position) {
                viewHolder.bindPlayer(model);
            }
        };
        mPlayerListView.setHasFixedSize(true);
        mPlayerListView.setLayoutManager(new LinearLayoutManager(this));
        mPlayerListView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mPlayerListView);

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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
