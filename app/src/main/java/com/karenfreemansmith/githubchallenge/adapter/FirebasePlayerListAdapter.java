package com.karenfreemansmith.githubchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.karenfreemansmith.githubchallenge.models.Player;
import com.karenfreemansmith.githubchallenge.ui.PlayerDetailActivity;
import com.karenfreemansmith.githubchallenge.util.ItemTouchHelperAdapter;
import com.karenfreemansmith.githubchallenge.util.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Guest on 12/16/16.
 */
public class FirebasePlayerListAdapter extends FirebaseRecyclerAdapter<Player, FirebasePlayerViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Player> mPlayers = new ArrayList<>();

    public FirebasePlayerListAdapter(Class<Player> modelClass, int modelLayout,
                                     Class<FirebasePlayerViewHolder> viewHolderClass,
                                     Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mPlayers.add(dataSnapshot.getValue(Player.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void populateViewHolder(final FirebasePlayerViewHolder viewHolder, Player model, int position) {
        viewHolder.bindPlayer(model);
        viewHolder.mPlayerImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, PlayerDetailActivity.class);
                intent.putExtra("playername", mPlayers.get(position).getPlayerName());
                intent.putExtra("playerid", String.valueOf(mPlayers.get(position).getPlayerId()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mPlayers, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mPlayers.remove(position);
        getRef(position).removeValue();

    }
}
