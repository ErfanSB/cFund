package com.arena.maraton.app;


import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public  class RtlActionBar {
    public static void Set(Window window, Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            int resId;
            ViewGroup actionBarView;
            View view = window.getDecorView();
            resId = context.getResources().getIdentifier(
                    "action_bar", "id", context.getPackageName());
            actionBarView = (ViewGroup) view.findViewById(resId);
            if (actionBarView == null) {
                resId = context.getResources().getIdentifier("action_bar", "id", "android");
                actionBarView = (ViewGroup) view.findViewById(resId);
            }
            actionBarView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
}