package com.karenfreemansmith.githubchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.ui.SearchActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Karen Freeman-Smith on 12/10/2016.
 */

public class PlayerListAdapter  extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private Context mContext;

    public PlayerListAdapter(Context context, ArrayList<Player> players) {
        mContext = context;
        mPlayers = players;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.playerAvatarImageView) ImageView mPlayerImageView;
        @Bind(R.id.playerNameText) TextView mPlayerNameText;
        @Bind(R.id.githubButton) Button mGithubButton;
        @Bind(R.id.addPlayerButton) Button mAddPlayerButton;

        private Context mContext;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext=itemView.getContext();
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mEditor = mSharedPreferences.edit();
        }

        public void bindPlayer(Player player) {
            mPlayerNameText.setText(player.getPlayerName());
            Picasso.with(mContext)
                    .load("https://avatars.githubusercontent.com/u/"+player.getPlayerId()+"?v=3")
                    .resize(150, 150)
                    .centerCrop()
                    .into(mPlayerImageView);
            mGithubButton.setOnClickListener(this);
            mAddPlayerButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int position = getLayoutPosition();
            if(v == mAddPlayerButton) {
                String playerPosition = mSharedPreferences.getString(Constants.PLAYER_POSITION, null);
                if(playerPosition.equals("1")) {
                    mEditor.putString(Constants.PLAYER1_KEY, mPlayers.get(position).getPlayerName()).apply();
                    mEditor.putString(Constants.PLAYER1_ID, String.valueOf(mPlayers.get(position).getPlayerId())).apply();
                } else {
                    mEditor.putString(Constants.PLAYER2_KEY, mPlayers.get(position).getPlayerName()).apply();
                    mEditor.putString(Constants.PLAYER2_ID, String.valueOf(mPlayers.get(position).getPlayerId())).apply();
                }
                Intent intent = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public PlayerListAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_view, parent, false);
        PlayerViewHolder viewHolder = new PlayerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayerListAdapter.PlayerViewHolder holder, int position) {
        holder.bindPlayer(mPlayers.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

}