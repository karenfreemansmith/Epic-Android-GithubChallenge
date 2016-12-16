package com.karenfreemansmith.githubchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.ui.PlayerDetailActivity;
import com.karenfreemansmith.githubchallenge.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Karen Freeman-Smith on 12/14/2016.
 */

public class FirebasePlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
  private static final int MAX_WIDTH = 200;
  private static final int MAX_HEIGHT = 200;
  private String mPlayerName;

  public ImageView mPlayerImageView;
  View mView;
  Context mContext;

  public FirebasePlayerViewHolder(View itemView) {
    super(itemView);
    mView = itemView;
    mContext = itemView.getContext();
    itemView.setOnClickListener(this);
  }

  public void bindPlayer(Player player) {
    mPlayerImageView = (ImageView) mView.findViewById(R.id.playerAvatarImageView);
    TextView nameTextView = (TextView) mView.findViewById(R.id.playerNameText);
    Button buttonView = (Button) mView.findViewById(R.id.viewPlayerButton);

    Picasso.with(mContext)
        .load("https://avatars.githubusercontent.com/u/"+ player.getPlayerId() +"?v=3")
        .resize(MAX_WIDTH, MAX_HEIGHT)
        .centerCrop()
        .into(mPlayerImageView);

    mPlayerName = player.getPlayerName();
    nameTextView.setText(mPlayerName);
    buttonView.setVisibility(View.GONE);
  }

  @Override
  public void onClick(View view) {
    final ArrayList<Player> players = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PLAYER);
    ref.addListenerForSingleValueEvent(new ValueEventListener() {

      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
          players.add(snapshot.getValue(Player.class));
        }

        int position = getLayoutPosition();

        Intent intent = new Intent(mContext, PlayerDetailActivity.class);
        intent.putExtra("playername", mPlayerName);

        mContext.startActivity(intent);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
      }
    });

  }

  @Override
  public void onItemSelected() {
    itemView.animate()
            .alpha(0.7f)
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(500);
  }

  @Override
  public void onItemClear() {
    itemView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f);
  }
}
