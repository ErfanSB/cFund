package com.arena.maraton.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arena.maraton.AdapterNote;
import com.arena.maraton.R;
import com.arena.maraton.StructComment;
import com.arena.maraton.Webservice;
import com.arena.maraton.app.G;
import com.arena.maraton.app.RtlActionBar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CommentActivity extends AppCompatActivity {

    private AdapterNote adapter;
    private View Empty;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        G.context=this;
        RtlActionBar.Set(getWindow(), G.context);
        adapter = new AdapterNote(G.commments);
        ListView lsContent= findViewById(R.id.lstContent);
        Empty= findViewById(R.id.empty);
        EditText text= findViewById(R.id.text);
        findViewById(R.id.senddd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().length()<6){
                    G.toast("متن نظر باید بیشتر از 6 کاراکتر باشد");
                }else {
                    G.toast("نظر شما ثبت گردید پس از تأیید ناظر در نظر ها قرار میگیرد");
                    text.setText("");
                    text.clearFocus();
                    finish();
                }
            }
        });
        lsContent.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("ID");
            if(isOnline())
                give_list(id);
            else
                G.toast("اینترنت متصل نیست");
        }


    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    @SuppressLint("StaticFieldLeak")
    public void give_list(String id){

        final ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id_p", ""+id ));
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("لطفا صبر کنید");
        pDialog.setCancelable(false);
        pDialog.show();
        G.commments.clear();
        new AsyncTask<Object, Object, String>(){

            @Override
            protected String doInBackground(Object... parms) {
                String url = "comments";

                return Webservice.readUrl(url,params);
            }
            @Override
            protected void onPostExecute(String result){
                if(result!= null) {
                    if(!result.equals("[]")) {

                        try {
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                StructComment s = new StructComment();
                                JSONObject object = array.getJSONObject(i);
                                s.id = object.getString("id");
                                s.name = object.getString("name");
                                s.text = object.getString("text");
                                s.date = object.getString("date");
                                G.commments.add(s);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        adapter.notifyDataSetChanged();

                    }else {
                        Empty.setVisibility(View.VISIBLE);
                    }
                }else {
                    Empty.setVisibility(View.VISIBLE);
                }
                pDialog.dismiss();

            }
        }.execute();
    }
}
