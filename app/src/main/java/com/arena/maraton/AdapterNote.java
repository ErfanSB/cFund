package com.arena.maraton;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arena.maraton.app.G;

import java.util.ArrayList;

public class AdapterNote extends ArrayAdapter<StructComment> {

    public AdapterNote(ArrayList<StructComment> array) {
        super(G.context, R.layout.adapter_notes, array);
    }

    private static class ViewHolder {
        ViewGroup layoutRoot;
        TextView name;
        TextView text;
        TextView date;

        ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            text = view.findViewById(R.id.text);
            date = view.findViewById(R.id.date);
            layoutRoot = view.findViewById(R.id.root);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                layoutRoot.setBackgroundColor(G.context.getResources().getColor(R.color.White));
            }
        }

        @SuppressLint("SetTextI18n")
        void fill(final StructComment item) {
name.setText(""+item.name);
date.setText(""+item.date);
text.setText(""+item.text);

        }

    }


    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        StructComment item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.adapter_notes, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(item);

        return convertView;
    }


}
