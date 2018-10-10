package com.arena.maraton.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arena.maraton.ActivityInsert;
import com.arena.maraton.CampaignFragment;
import com.arena.maraton.ProjectFragment;
import com.arena.maraton.R;
import com.arena.maraton.UserFragment;
import com.arena.maraton.Webservice;
import com.arena.maraton.app.G;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.util.QiscusErrorLogger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {


    private ProgressDialog mProgressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_three);
        initToolbar();
        initViewPagerAndTabs();
        Give_Image();
        FloatingActionButton fab = findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityInsert.class));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void Give_Image() {
        final ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("phone", G.preferences.getString("PHONE", "") + ""));
        new AsyncTask<Object, Object, String>() {

            @Override
            protected String doInBackground(Object... parms) {
                String url = "Give_Image";
                return Webservice.readUrl(url, params);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null && result.length() > 5)
                    G.preferences.edit().putString("IMG", result).apply();
            }
        }.execute();

    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ProjectFragment(), getString(R.string.tab_1));
        pagerAdapter.addFragment(new CampaignFragment(), getString(R.string.tab_2));
        pagerAdapter.addFragment(new UserFragment(), getString(R.string.tab_3));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
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

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            QiscusErrorLogger.print(throwable);
                            showError("مشکل در اتصال");
                            dismissLoading();
                        }
                    });
        }


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
