package com.arena.maraton.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arena.maraton.R;
import com.arena.maraton.Webservice;
import com.arena.maraton.app.G;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import com.raycoarana.codeinputview.CodeInputView;
import com.raycoarana.codeinputview.OnCodeCompleteListener;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MaterialDesignLogInForm extends AppCompatActivity  {
    private int codesend;
    private SmsVerifyCatcher smsVerifyCatcher;
    private CodeInputView codeinput;
    private EditText Name, Phone;
    private SweetAlertDialog DialogLoad;
    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 90000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private ViewGroup txtsms;
    private String _Phone;
    private TextView number,downcount;
    private TextView sendagain;
    private ViewGroup aName,aPhone;
    private Button Enter;
    private boolean doubleBackToExitPressedOnce;
    private boolean SENDED=false;
    private Time now;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_login_form);
        G.context = this;
        G.Activity = this;
         now = new Time();
        now.setToNow();
         Enter = findViewById(R.id.enter);
        Phone = findViewById(R.id.phone);
        Name = findViewById(R.id.name);
        txtsms = findViewById(R.id.showcount);
        downcount = findViewById(R.id.downcount);
        number = findViewById(R.id.number);
        sendagain = findViewById(R.id.sendagain);
        codeinput = findViewById(R.id.codeinput);
         aName = findViewById(R.id.aname);
         aPhone = findViewById(R.id.aphone);
//        TelephonyManager tMgr = (TelephonyManager) G.context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
//            Phone.setText(tMgr.getLine1Number());
//        }
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internet()) {
                    String _Name = Name.getText().toString();
                     _Phone = Phone.getText().toString();
                    if (_Name.length() < 3) {
                        Name.requestFocus();
                        Name.setError("نام و نام خانوادگی را به درستی وارد کنید");
                    } else if (_Phone.length() != 11) {
                        Phone.requestFocus();
                        Phone.setError("شماره خود را به درستی وارد کنید");
                    } else {
                        if(!G.preferences.getBoolean("REPORT",false)) {
                            Enter.setVisibility(View.GONE);
                            aName.setVisibility(View.GONE);
                            aPhone.setVisibility(View.GONE);
                            codeinput.setVisibility(View.VISIBLE);
                            codeinput.requestFocus();
                            txtsms.setVisibility(View.VISIBLE);
                            number.setText(_Phone);
                            codesend = new Random().nextInt(9999 - 1000 + 1) + 1000;
                            final ArrayList<NameValuePair> params1 = new ArrayList<>();
                            params1.add(new BasicNameValuePair("to", Phone.getText().toString() + ""));
                            params1.add(new BasicNameValuePair("code", codesend + ""));
                            sendCode(params1);

                            Toast.makeText(MaterialDesignLogInForm.this, "لطفا شکیبا باشید", Toast.LENGTH_SHORT).show();
                            resetTimer();
                            startTimer();
                        }else {
                            Toast.makeText(MaterialDesignLogInForm.this, "شما ریپورت شده و اجازه ورود به اپلیکیشن را ندارید", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    internet();failed();
                }
            }
        });
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {

                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(message);
                while (m.find()) {
                    codeinput.setCode(m.group());

                    Verification(m.group());
                }
            }
        });
        sendagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internet()) {
                    final ArrayList<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("to", Phone.getText().toString() + ""));
                    params.add(new BasicNameValuePair("code", codesend + ""));
                    sendCode(params);
               resetTimer();
               startTimer();
                    Toast.makeText(MaterialDesignLogInForm.this, " کد تایید دوباره ارسال شد ", Toast.LENGTH_SHORT).show();
                    sendagain.setVisibility(View.GONE);
                } else {
                    failed();
                }
            }
        });
        codeinput.addOnCompleteListener(new OnCodeCompleteListener() {


            @Override
            public void onCompleted(final String code) {

                Verification(code);

            }
        });
        DialogLoad = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        DialogLoad.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        DialogLoad.setTitleText("لطفا صبر کنید");

        DialogLoad.setCancelable(false);
    }

    public void Verification(final String code) {
        if (internet()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!code.equals(codesend + ""))
                        codeinput.setEditable(true);
                    codeinput.setError("کد اشتباه است");
                    if (code.equals(codesend + "")) {
                        codeinput.setVisibility(View.INVISIBLE);
                        final ArrayList<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("phone", Phone.getText().toString() + ""));
                        params.add(new BasicNameValuePair("name", Name.getText().toString() + ""));
                        params.add(new BasicNameValuePair("sign_date", now.year+"-"+now.month+"-"+now.monthDay + ""));
                        newUser(params);
                        pauseTimer();
                        //Toast.makeText(MaterialDesignLogInForm.this, "ثبت نام انجام شد", Toast.LENGTH_SHORT).show();

                    }
                }
            }, 1000);
        } else {
           failed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("StaticFieldLeak")
    public void newUser(final ArrayList<NameValuePair> params) {

        new AsyncTask<Object, Object, String>() {

            @Override
            protected String doInBackground(Object... parms) {
                String url = "user";
                return Webservice.readUrl(url, params);
            }

            @Override
            protected void onPostExecute(String result) {
                G.toast("لطفا صبر کنید");
                SharedPreferences.Editor editor = G.preferences.edit();
                editor.putString("PHONE", getValueByKey(params, "phone"));
                editor.putString("NAME", getValueByKey(params, "name"));
                editor.apply();
                Intent i = new Intent(MaterialDesignLogInForm.this, MainActivity.class);
                DialogLoad.dismiss();
                startActivity(i);
                finish();
            }
        }.execute();

    }


    @SuppressLint("StaticFieldLeak")
    public void sendCode(final ArrayList<NameValuePair> params) {

        new AsyncTask<Object, Object, Boolean>() {

            @Override
            protected Boolean doInBackground(Object... parms) {

                String url = "sms";
                Webservice.readUrl(url, params);

                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                // DialogLoading(false);
            }
        }.execute();

    }

    private String getValueByKey(ArrayList<NameValuePair> _list, String key) {
        for (NameValuePair nvPair : _list) {
            if (nvPair.getName().equals(key)) {
                return nvPair.getValue() + "";
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if(txtsms.getVisibility()==View.VISIBLE){
                codeinput.clearFocus();
                Back();
            }else {
                super.onBackPressed();
                finish();
            }
        }else {
            if(txtsms.getVisibility()==View.VISIBLE) {
                Toast.makeText(this, "برای برگشت دوباره کلیک کنید ", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "برای خروج از برنامه دوباره کلیک کنید ", Toast.LENGTH_SHORT).show();
            }
        }

        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

    @Override
    public void finish() {
        super.finish();


    }

    public void failed() {
        SweetAlertDialog dilalog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dilalog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        dilalog.setTitleText("اینترنت متصل نیست");
        dilalog.showCancelButton(false);
        dilalog.setCancelable(true);
        dilalog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!G.preferences.getString("NAME", "").equals("")) {
            Intent intent = new Intent(MaterialDesignLogInForm.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onPause() {
        super.onPause();


    }




    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if(!SENDED){
                    SENDED=true;
                    sendagain.setVisibility(View.VISIBLE);

                }else {
                    Back();
                    Toast.makeText(MaterialDesignLogInForm.this, "کد فعال سازی دریافت نشد", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();

    }

public void Back(){
    Enter.setVisibility(View.VISIBLE);
    aName.setVisibility(View.VISIBLE);
    aPhone.setVisibility(View.VISIBLE);
    codeinput.setVisibility(View.GONE);
    txtsms.setVisibility(View.GONE);
    txtsms.setVisibility(View.GONE);
}
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

downcount.setText(timeLeftFormatted);

    }
    private boolean internet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}