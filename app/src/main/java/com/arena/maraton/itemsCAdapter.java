package com.arena.maraton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arena.maraton.activity.ShowActivity;
import com.arena.maraton.app.G;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemsCAdapter extends RecyclerView.Adapter<itemsCAdapter.AbsentViewHolder> {

    private static List<ItemObject> itemObjects;

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private int SaatStart;
    private int DaqiqeStart;

    public itemsCAdapter(List<ItemObject> itemObjects) {
        itemsCAdapter.itemObjects = itemObjects;


    }


    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campaign, parent, false);
        return new itemsCAdapter.AbsentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {

        Picasso.with(G.context).load(itemObjects.get(position).getimg1()).placeholder(R.drawable.nopic)
                .into(holder.screenShot);
        holder.musicName.setText(itemObjects.get(position).gettitle());
        holder.musicAuthor.setText(itemObjects.get(position).getdesc());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.context, ShowActivity.class);
                intent.putExtra("ID",itemObjects.get(position).getid());
                G.Activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class AbsentViewHolder extends RecyclerView.ViewHolder {

        ImageView screenShot;
        TextView musicName;
        TextView musicAuthor;
        ViewGroup card_view;

        AbsentViewHolder(View itemView) {
            super(itemView);
            screenShot = itemView.findViewById(R.id.screen_shot);
            musicName = itemView.findViewById(R.id.music_name);
            musicAuthor = itemView.findViewById(R.id.music_author);
            card_view = itemView.findViewById(R.id.card_view1);
        }
    }
}
