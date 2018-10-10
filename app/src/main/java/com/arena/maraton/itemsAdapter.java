package com.arena.maraton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arena.maraton.activity.ShowActivity;
import com.arena.maraton.app.G;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.AbsentViewHolder> {

    private static List<ItemObject> itemObjects;

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private int SaatStart;
    private int DaqiqeStart;

    public itemsAdapter(List<ItemObject> itemObjects) {
        itemsAdapter.itemObjects = itemObjects;


    }


    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_music_list, parent, false);
        return new itemsAdapter.AbsentViewHolder(view);
    }
    public static String convert(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = new StringBuilder(".");
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString().replace("1", "۱").replace("2", "۲").replace("3", "۳").replace("4", "۴").replace("5", "۵").replace("6", "۶").replace("7", "۷").replace("8", "۸").replace("9", "۹").replace("0", "۰");
            }
            if (i == 3) {
                str3.insert(0, ",");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {

        Picasso.with(G.context).load(itemObjects.get(position).getimg1()).placeholder(R.drawable.nopic)
                .into(holder.screenShot);
        holder.musicName.setText(itemObjects.get(position).gettitle());
        holder.musicAuthor.setText(itemObjects.get(position).getdesc());

        holder.sumFund.setText(""+convert(itemObjects.get(position).getsumFund()+""));
        holder.sumFund.append("\n");
        holder.sumFund.append("جذب شده");
        long sum=itemObjects.get(position).getsumFund();
        long need=itemObjects.get(position).getneedFund();
        int persent = (int)((float)sum/need*100);

       holder.txtPersent.setText(""+persent+"%");

        holder.pbPersent.setProgress(persent);
        holder.needTime.setText(itemObjects.get(position).getNeedTime()+"");
        holder.needTime.append("\n");
        holder.needTime.append("روز مانده");
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.context, ShowActivity.class);
                intent.putExtra("ID", itemObjects.get(position).getid());
                intent.putExtra("sumFund", itemObjects.get(position).getsumFund()+"");
                intent.putExtra("needFund", itemObjects.get(position).getneedFund()+"");
                intent.putExtra("needTime",  holder.needTime.getText().toString());
                intent.putExtra("persent",  persent);
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
        TextView sumFund;

        TextView txtPersent;
        TextView needTime;
        ProgressBar pbPersent;

        AbsentViewHolder(View itemView) {
            super(itemView);
            screenShot = itemView.findViewById(R.id.screen_shot);
            musicName = itemView.findViewById(R.id.music_name);
            musicAuthor = itemView.findViewById(R.id.music_author);
            card_view = itemView.findViewById(R.id.card_view1);
            txtPersent = itemView.findViewById(R.id.txtPersent);

            sumFund = itemView.findViewById(R.id.sumFund);
            needTime = itemView.findViewById(R.id.needTime);
            pbPersent = itemView.findViewById(R.id.progressBar);

        }
    }
}
