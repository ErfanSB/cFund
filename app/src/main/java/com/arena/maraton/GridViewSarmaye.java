package com.arena.maraton;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arena.maraton.app.G;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewSarmaye extends ArrayAdapter<ModelSarmaye> {



    public GridViewSarmaye(ArrayList<ModelSarmaye> array) {
        super(G.context, R.layout.model_sarmaye, array);
    }

    private static class ViewHolder {
        CardView layoutRoot;
        TextView name , fav;
        ImageView image , like, comment;
        ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            fav = view.findViewById(R.id.fav);

        }
        @SuppressLint("SetTextI18n")
        void fill(final ModelSarmaye item) {
            name.setText("" + item.getName());
            fav.setText("" + item.getName());

            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(G.context, ""+item.getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        ModelSarmaye item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.model_sarmaye, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(item);
        return convertView;
    }


}

