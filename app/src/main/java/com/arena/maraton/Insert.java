package com.arena.maraton;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Insert extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<NameValuePair> params;
    JSONArray array = null;
    JSONObject object = null;
    EditText title;
    EditText sarmaye,desc;
    Button selectImg,location,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        title = (EditText) findViewById(R.id.title);
        sarmaye = (EditText) findViewById(R.id.sarmaye);
        selectImg = (Button) findViewById(R.id.selectImg);
        location = (Button) findViewById(R.id.location);
        send = (Button) findViewById(R.id.send);
        desc =  findViewById(R.id.desc);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (title.getText().toString().trim().equalsIgnoreCase("")) {
                            title.setError("عنوان پروژه نمیتواند خالی باشد.");
                        }
                    }
                });

                sarmaye.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sarmaye.getText().toString().trim().equalsIgnoreCase("")) {
                            sarmaye.setError("میزان سرمایه مورد نیاز نمیتواند خالی باشد.");
                        }
                    }
                });


                desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (desc.getText().toString().trim().equalsIgnoreCase("")) {
                            desc.setError("توضیحات نمیتواند خالی باشد.");
                        }
                    }
                });


            }
        });

    }

    @Override
    public void onRefresh() {
        new MyAsyncTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... pa) {

            String S = Webservice.readUrl("", null);
            Log.i("result: ", S);
            return S;

        }

        protected void onPostExecute(String result) {

            try {
                array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {

                    object = new JSONObject(String.valueOf(array.getJSONObject(i)));
                    String title = object.getString("title");
                    String sarmaye = object.getString("sarmaye");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}