package com.arena.maraton.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arena.maraton.R;
import com.arena.maraton.Webservice;
import com.arena.maraton.app.G;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.util.QiscusErrorLogger;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class showActivity2 extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle, Title;
    private AppBarLayout mAppBarLayout;
    private ImageView profile_background;
    ArrayList<NameValuePair> params;
    JSONArray array = null;
    JSONObject object = null;
    private String ID;
    private CircleImageView image_profile;
    private ProgressDialog mProgressDialog;
    private View chat;
    private String UserName, name;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showw);

        bindActivity();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);


        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        image_profile = findViewById(R.id.image_profile);
        profile_background = findViewById(R.id.profile_background);


        params = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("ID");
        }
        params.add(new BasicNameValuePair("id", ID + ""));
        new MyAsyncTask().execute();
        chat = findViewById(R.id.chat);
        startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
    }

    @SuppressLint("NewApi")
    private void bindActivity() {

        Title = findViewById(R.id.title);
        mTitle = findViewById(R.id.main_textview_title);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = findViewById(R.id.main_appbar);

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
//        private ProgressDialog pd = new ProgressDialog(ShowActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
//            pd.setMessage("درحال دریافت اطلاعات");
//            pd.show();
        }

        @Override
        protected String doInBackground(String... pa) {

            return Webservice.readUrl("showUser", params);


        }

        protected void onPostExecute(String result) {
//            pd.hide();
//            pd.dismiss();

            try {
                array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {

                    object = new JSONObject(String.valueOf(array.getJSONObject(i)));
                    name = object.getString("name");
                    mTitle.setText(name);
                    Title.setText(name);
                    String img = (object.getString("img"));
                    UserName = (object.getString("number"));
                    String[] na = name.split(" ");
                    ((TextView) findViewById(R.id.Name)).setText(na[0]);
                    ((TextView) findViewById(R.id.LastName)).setText(na[1]);
                    ((TextView) findViewById(R.id.reshte)).setText(object.getString("comments"));
                    ((TextView) findViewById(R.id.ostan)).setText("همدان");
                    ((TextView) findViewById(R.id.desc)).setText(object.getString("description"));
                   Picasso.with(G.context).load(object.getString("img")).placeholder(R.drawable.nopic)
                            .into(image_profile);
                    Picasso.with(G.context).load("https://wallpaper.wiki/wp-content/uploads/2017/04/wallpaper.wiki-Download-railway-photos-1-PIC-WPD009986.jpg").placeholder(R.drawable.nopic)
                            .into(profile_background);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void Chat(View v) {
        if (!Qiscus.hasSetupUser()) {
            showLoading();

            Qiscus.setUser(G.preferences.getString("PHONE", ""), "123456789")
                    .withUsername(G.preferences.getString("NAME", ""))
                    .withAvatarUrl(G.preferences.getString("IMG", ""))
                    .save(new Qiscus.SetUserListener() {
                        @Override
                        public void onSuccess(QiscusAccount qiscusAccount) {
                            openChat();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            QiscusErrorLogger.print(throwable);
                            showError("مشکل در اتصال");
                            dismissLoading();
                        }
                    });
        } else {
            openChat();
        }


    }

    public void openChat() {

        showLoading();
        Qiscus.buildChatWith(UserName)
                .build(this, new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        revertCustomChatConfig();
                        startActivity(intent);
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        QiscusErrorLogger.print(throwable);
                        if (QiscusErrorLogger.getMessage(throwable).contains("400") || QiscusErrorLogger.getMessage(throwable).contains("not found")) {
                            showError("کاربر اپلیکیشن را نصب نکرده است");
                        } else {
                            showError("مشکل در اتصال");
                        }
                        dismissLoading();
                    }
                });
    }

    private void revertCustomChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_attach);
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(" لطفا صبر کنید ... ");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissLoading() {

        if (mProgressDialog != null) mProgressDialog.dismiss();
    }
}

