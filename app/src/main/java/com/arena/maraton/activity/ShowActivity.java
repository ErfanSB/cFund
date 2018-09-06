package com.arena.maraton.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arena.maraton.CustomPager;
import com.arena.maraton.NotifyingScrollView;
import com.arena.maraton.R;
import com.arena.maraton.SlidingImage_Adapter;
import com.arena.maraton.StructTask;
import com.arena.maraton.Webservice;
import com.arena.maraton.app.G;
import com.arena.maraton.app.RtlActionBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressLint("StaticFieldLeak")
public class ShowActivity extends AppCompatActivity implements View.OnTouchListener {
    public static String[] imagee = {"", "", "", ""};
    public String id;
    public String subject;
    public String kind;
    public String state;
    public String address;
    public String date;
    public String parts;
    public String desc;
    public String lat, lon;
    public String img1;
    public String img2;
    public String img3;
    public String img4;
    public String date_rep;
    public String name_rep;
    public String reason_rep;
    public String desc_rep;
    private TextView txtsubj;
    private TextView txtkind;
    private TextView txtstate;

    private ListView lsparts;
    private TextView txtdesc;
    public static ProgressBar progressBar;
    private static CustomPager mViewPager;

    private Drawable mActionBarBackgroundDrawable;

    private MapView mapView;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private ViewGroup other;

    private ViewGroup aa;
    private boolean Fail;
    private TextView txtName;
    private TextView txtDate;
    private TextView txtReason;
    private TextView txtDesc_req;
    public static StructTask struct_item;
    private CircleIndicator indicato;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        G.Activity = this;
        G.context = this;
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout c = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        c.setCollapsedTitleTypeface(Typeface.SERIF);
        c.setExpandedTitleTypeface(Typeface.SERIF);
       // RtlActionBar.Set(getWindow(),G.context);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("ID");
        }

        mActionBarBackgroundDrawable = new ColorDrawable(getColor(R.color.colorPrimary));
        mActionBarBackgroundDrawable.setAlpha(0);
        getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);


        other = (ViewGroup) findViewById(R.id.other);
        other.setOnTouchListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;
                float radius = Math.max(mapView.getWidth(), mapView.getHeight()) * 2.0f;
                if (mapView.getVisibility() == View.GONE) {
                    mapView.onResume();
                    mapView.setVisibility(View.VISIBLE);
                    ViewAnimationUtils.createCircularReveal(mapView, cx, cy, 0, radius).start();


                } else {
                    mapView.onPause();
                    Animator reveal = ViewAnimationUtils.createCircularReveal(
                            mapView, cx, cy, radius, 0);
                    reveal.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mapView.setVisibility(View.GONE);
                        }
                    });
                    reveal.start();

                }

            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        }
        int width = (getWindowManager().getDefaultDisplay().getWidth());
        mapView = (MapView) findViewById(R.id.mapV);
        ViewGroup.LayoutParams params = mapView.getLayoutParams();
        params.height = width;
        mapView.setLayoutParams(params);
        mapView.onCreate(savedInstanceState);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDate = (TextView) findViewById(R.id.txtDate_req);
        txtReason = (TextView) findViewById(R.id.txtReason);
        txtDesc_req = (TextView) findViewById(R.id.txtDesc_req);
        txtsubj = (TextView) findViewById(R.id.txtnamesubj);
        txtkind = (TextView) findViewById(R.id.txtnamekind);
        txtstate = (TextView) findViewById(R.id.txtnamestate);

        lsparts = (ListView) findViewById(R.id.listParts);
        lsparts.setFocusableInTouchMode(false);

        txtdesc = (TextView) findViewById(R.id.txtnamedesc);
        progressBar = (ProgressBar) findViewById(R.id.pbimg);
        mViewPager = (CustomPager) findViewById(R.id.qwe);

        mViewPager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.image));

        indicato = (CircleIndicator) findViewById(R.id.indicator);
        Down(id + "");
    }

    private void setUpView() {


    }

    private ArrayList<String> ImagesArray = new ArrayList<String>();

    public void Down(final String _id) {

        new AsyncTask<Object, Object, Boolean>() {
            @Override
            protected Boolean doInBackground(Object... parms) {

                final ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id", "" + _id));
                String result;
                result = Webservice.readUrl("show",
                        params);
                Log.i("QWERQWER", result);
                if (result != null) try {
                    JSONArray tasks = new JSONArray(result);
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject object = tasks.getJSONObject(i);
                        subject = object.getString("title");
                        kind = object.getString("kind");
                        switch (kind){
                            case "1":
                                kind="استارتاپ";
                                break;
                            case "2":
                                kind="انیمیشن";
                                break;
                            case "3":
                                kind="تئاتر";
                                break;
                            case "4":
                                kind="سینما";
                                break;
                            case "5":
                                kind="عکاسی";
                                break;
                            case "6":
                                kind="فرهنگی";
                                break;
                            case "7":
                                kind="فیلم";
                                break;
                            case "8":
                                kind="فیلم کوتاه";
                                break;
                            case "9":
                                kind="مجسمه سازی";
                                break;
                            case "10":
                                kind="مستند";
                                break;
                            case "11":
                                kind="موسیقی";
                                break;
                            case "12":
                                kind="مینیمال";
                                break;
                            case "13":
                                kind="نقاشی";
                                break;
                            case "14":
                                kind="ورزشی";
                                break;
                        }
                        state = object.getString("kind");

                        desc = object.getString("description");
                        String s = object.getString("location");
                        if(s.length()>4 && s.contains(",")) {
                            String[] separated = s.split(",");
                            lat = separated[0];
                            lon = separated[1];
                        }
                        img1 = object.getString("img1") + "";
                        img2 = object.getString("img2") + "";
                        img3 = object.getString("img3") + "";
                        img4 = object.getString("img4") + "";
                        ImagesArray.clear();
                        if (!img1.equals("")) {
                            ImagesArray.add(img1);
                        }
                        if (!img2.equals("")) {
                            ImagesArray.add(img2);
                        }
                        if (!img3.equals("")) {
                            ImagesArray.add(img3);
                        }
                        if (!img4.equals("")) {
                            ImagesArray.add(img4);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                txtsubj.setText(subject);
                txtkind.setText(kind);
                txtstate.setText(state);

                mViewPager.setAdapter(new SlidingImage_Adapter(ShowActivity.this, ImagesArray));
                indicato.setViewPager(mViewPager);
                txtdesc.setText(desc);
                imagee[0] = img1;
                imagee[1] = img2;
                imagee[2] = img3;
                imagee[3] = img4;
                if (img1 != null && img1.length() > 5) {

                } else {

                }
                if (Fail) {
                    txtDate.setText(date_rep);
                    txtName.setText(name_rep);
                    txtReason.setText(reason_rep);
                    txtDesc_req.setText(desc_rep);

                }
                setUpView();
                if ((lat != null && !lat.equals("")) && (lon != null && !lon.equals(""))) {
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            setUpMap(googleMap);
                        }
                    });
                    fab.setVisibility(View.VISIBLE);
                }


            }
        }.execute();
    }

    private void addCameraToMap(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void getTotalHeightofListView(ListView listView) {

        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = who.getMaxScrollAmount();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            int newAlpha = (int) (ratio * 255);
            if (2 * newAlpha <= 255)
                newAlpha *= 2;
            else
                newAlpha = 255;
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };
    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(@NonNull Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        }
    };

    private void setUpMap(GoogleMap map) {
        mMap = map;
        LatLng L = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        addCameraToMap(L);
        // mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        mMap.addMarker(new MarkerOptions().position(L).title("" + subject));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}



