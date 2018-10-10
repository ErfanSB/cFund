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
import com.arena.maraton.activity.showActivity2;
import com.arena.maraton.app.G;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemUAdapter extends RecyclerView.Adapter<itemUAdapter.AbsentViewHolder> {

    private static List<ModelSarmaye> itemObjects;

    public itemUAdapter(List<ModelSarmaye> itemObjects) {
        itemUAdapter.itemObjects = itemObjects;
    }


    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_sarmaye, parent, false);
        return new itemUAdapter.AbsentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {

        Picasso.with(G.context).load(itemObjects.get(position).getImage()).placeholder(R.drawable.nopic)
                .into(holder.image);
        holder.name.setText(itemObjects.get(position).getName());
        holder.fav.setText(itemObjects.get(position).getFav());
        holder.like.setText(itemObjects.get(position).getLike());
        holder.comment.setText(itemObjects.get(position).getComment());
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.context, showActivity2.class);
                intent.putExtra("ID", itemObjects.get(position).getId()+"");
                G.Activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class AbsentViewHolder extends RecyclerView.ViewHolder {

        ViewGroup layoutRoot;
        TextView name, fav, like, comment;
        ImageView image;

        AbsentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            fav = itemView.findViewById(R.id.fav);
            image = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.likee);
            comment = itemView.findViewById(R.id.commentt);
            layoutRoot = itemView.findViewById(R.id.layoutRoot1);
        }
    }
}
