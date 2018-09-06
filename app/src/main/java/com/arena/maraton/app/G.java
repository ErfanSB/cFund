package com.arena.maraton.app;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arena.maraton.R;
import com.qiscus.sdk.Qiscus;

import java.text.SimpleDateFormat;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class G extends Application {

    public static LayoutInflater LayoutInflater;
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static LayoutInflater inflater;
    public static int i = 0;
    public static SharedPreferences preferences;
    public static String host = "http://arenateam.ir/cfund/service.php?action=";
    public static String font = "fonts/shabnam.ttf";
    public static AppCompatActivity Activity;

    @SuppressLint({"ResourceType", "SimpleDateFormat"})
    public void onCreate() {
        super.onCreate();
        setLocale(new Locale("en"));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Qiscus.init(this, "khabgahne-gy7hy1elmis");
        //Qiscus.setEnableLog(BuildConfig.DEBUG);
        Qiscus.getChatConfig()
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAppBarColor(R.color.colorPrimary)
                .setAccentColor(R.color.colorAccent)
                .setSwipeRefreshColorScheme(R.color.colorPrimary)
                .setTitleColor(R.color.White)
                .setReadIconColor(android.R.color.holo_blue_bright)
                .setFailedToSendMessageColor(R.color.colorAccent)
                .setRightBubbleColor(R.color.colorAccent)
                .setRightBubbleTextColor(R.color.White)
                .setRightBubbleTimeColor(android.R.color.darker_gray)
                .setRecordText("صدا")
                .setAddFileText("فایل")
                .setAddLocationText("موقعیت")
                .setAddPictureText("عکس")
                .setTakePictureText("دوربین")
                .setAddContactText("مخاطب")
//                .setSelectedBubbleBackgroundColor(R.color.error_stroke_color)
                .setEnableAvatarAsNotificationIcon(true)
                .setNotificationBigIcon(R.mipmap.ic_launcher)
                .setNotificationSmallIcon(R.mipmap.ic_launcher)
                .setOnlyEnablePushNotificationOutsideChatRoom(true)
                .setEnableAvatarAsNotificationIcon(true)
                .setTimeFormat(date -> new SimpleDateFormat("HH:mm").format(date));
        if (Build.VERSION.SDK_INT >= 24) {
            Qiscus.getChatConfig()
                    .setEnableReplyNotification(true);
        }
        FontOverride.setDefaultFont(this, "DEFAULT");
        FontOverride.setDefaultFont(this, "MONOSPACE");
        FontOverride.setDefaultFont(this, "SERIF");
        FontOverride.setDefaultFont(this, "SANS_SERIF");
        TypefaceUtil.overrideFont(getApplicationContext());
        context = getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(font)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater = (android.view.LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    private void setLocale(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplicationContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }
    public static void  toast(String text){
        LayoutInflater inf = inflater;
        View layout = inf.inflate(R.layout.toast,
                (ViewGroup) G.Activity.findViewById(R.id.toast_layout_root));
        TextView matn =  layout.findViewById(R.id.text);
        matn.setText(text);
        Toast toast = new Toast(G.context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
