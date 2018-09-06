package com.arena.maraton;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arena.maraton.activity.ShowActivity;
import com.arena.maraton.app.G;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    String[] image = ShowActivity.imagee;

    public ImageAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return f;
    }

    @Override
    public int getCount() {
        int Count = 1;
        if (image[1]!=null &&image[1].length() > 5)
            Count++;
        if (image[2]!=null &&image[2].length() > 5)
            Count++;
        if (image[3]!=null &&image[3].length() > 5)
            Count++;
        return Count;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        final ImageView imgPreview = new ImageView(mContext);
        imgPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String imageUrl = image[i];

        if (imageUrl!=null&&imageUrl.length() > 5) {
            ShowActivity.progressBar.setVisibility(View.VISIBLE);
            if (!imageUrl.contains("/")) {
                String subUrl = "http://dehkade-app.ir/a/images/";
                imageUrl = subUrl + imageUrl;
            }
            final String finalImageUrl = imageUrl;

            Picasso.with(G.context).load(finalImageUrl).placeholder(R.drawable.nopic)
                    .networkPolicy(NetworkPolicy.OFFLINE).into(imgPreview, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    ShowActivity.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    Picasso.with(G.context).load(finalImageUrl).placeholder(R.drawable.nopic)
                            .into(imgPreview, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    ShowActivity.progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    ShowActivity.progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            });
        } else {
            imgPreview.setImageResource(R.drawable.nopic);
        }
        ((ViewPager) container).addView(imgPreview, 0);
        return imgPreview;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {

        ((ViewPager) container).removeView((ImageView) obj);

    }

}
