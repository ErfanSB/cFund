package com.arena.maraton;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arena.maraton.activity.ShowActivity;
import com.arena.maraton.app.G;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TabAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;

    public TabAdapter(Context context, List<ItemObject> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.pop_music_list, parent, false);
            listViewHolder.screenShot = convertView.findViewById(R.id.screen_shot);
            listViewHolder.musicName = convertView.findViewById(R.id.music_name);
            listViewHolder.musicAuthor = convertView.findViewById(R.id.music_author);
            listViewHolder.card_view = convertView.findViewById(R.id.card_view);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        Picasso.with(G.context).load(listStorage.get(position).getimg1()).placeholder(R.drawable.nopic)
                .into(listViewHolder.screenShot);
        listViewHolder.musicName.setText(listStorage.get(position).gettitle());
        listViewHolder.musicAuthor.setText(listStorage.get(position).getdesc());
listViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(G.context, ShowActivity.class);
        G.Activity.startActivity(intent);
    }
});
        return convertView;
    }

    static class ViewHolder{
        ImageView screenShot;
        TextView musicName;
        TextView musicAuthor;
        CardView card_view;
    }

}
