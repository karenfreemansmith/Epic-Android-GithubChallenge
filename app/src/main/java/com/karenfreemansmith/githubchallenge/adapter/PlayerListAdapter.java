package com.karenfreemansmith.githubchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Karen Freeman-Smith on 12/10/2016.
 */

public class PlayerListAdapter  extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private Context mContext;

    public PlayerListAdapter(Context context, ArrayList<Player> players) {
        mContext = context;
        mPlayers = players;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.playerAvatarImageView)
        ImageView mPlayerImageView;
        @Bind(R.id.playerNameText)
        TextView mPlayerNameText;
        @Bind(R.id.githubButton)
        Button mGithubButton;
        @Bind(R.id.addPlayerButton) Button mAddPlayerButton;

        private Context mContext;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext=itemView.getContext();
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
                //TODO: add player to battle
            }
            if (v == mGithubButton) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + mPlayers.get(position).getPlayerName()));
                mContext.startActivity(webIntent);
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