package com.arena.maraton;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arena.maraton.app.G;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        final ProgressBar pbimg = (ProgressBar) imageLayout
                .findViewById(R.id.pbimg);
        pbimg.setVisibility(View.VISIBLE);
        Picasso.with(G.context).load(IMAGES.get(position))
                .networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                pbimg.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Picasso.with(G.context).load(IMAGES.get(position))
                        .into(imageView ,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        pbimg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pbimg.setVisibility(View.GONE);
                    }
                });
            }
        });
        view.addView(imageLayout, 0);

        return imageLayout;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}