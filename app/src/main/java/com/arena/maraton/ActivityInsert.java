package com.arena.maraton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ActivityInsert extends AppCompatActivity {



    int SelectedID;
    private static ArrayList<String> salons = new ArrayList<>();
    Spinner spinner;
    ArrayList<NameValuePair> params;
    JSONArray array = null;
    JSONObject object = null;
    EditText title;
    EditText sarmaye,desc;
    Button selectImg,location,send;
    Dialog dialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint({"InlinedApi", "StaticFieldLeak"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_insert);


        //sarmaye = findViewById(R.id.sarmaye);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);


        title = (EditText) findViewById(R.id.title);
        sarmaye = (EditText) findViewById(R.id.sarmaye);
        selectImg = (Button) findViewById(R.id.selectImg);
        location = (Button) findViewById(R.id.location);
        send = (Button) findViewById(R.id.send);
        desc =  findViewById(R.id.desc);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog();
            }
        });
//        @Override
//        public void onClick(View view) {
//            title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (title.getText().toString().trim().equalsIgnoreCase("")) {
//                        title.setError("عنوان پروژه نمیتواند خالی باشد.");
//                    }
//                }
//            });
//
//            sarmaye.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (sarmaye.getText().toString().trim().equalsIgnoreCase("")) {
//                        sarmaye.setError("میزان سرمایه مورد نیاز نمیتواند خالی باشد.");
//                    }
//                }
//            });
//
//
//            desc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (desc.getText().toString().trim().equalsIgnoreCase("")) {
//                        desc.setError("میزان سرمایه مورد نیاز نمیتواند خالی باشد.");
//                    }
//                }
//            });
//
//
//        }
        salons.clear();
        salons.add("ورزشی");
        salons.add("فرهنگی");
        salons.add("عکس");
        salons.add("سینما");
        salons.add("موسیقی");
        salons.add("نقاشی");
        salons.add("مستند");



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sp, salons);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                        int position, long id) {

                 boolean Sel = position > -1;
                if (Sel) {
                    SelectedID = (position + 1);
                    // Toast.makeText(SettingActivity.this, ""+salons.get(position), Toast.LENGTH_SHORT).show();
                   String salone = salons.get(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void dialog() {

        dialog = new Dialog(ActivityInsert.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rols);

        dialog.show();
    }

}