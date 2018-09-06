package com.arena.maraton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment {

    public ProjectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pop_music, container, false);
        GridView gridview = (GridView)view.findViewById(R.id.gridview);

        List<ItemObject> allItems = getAllItemObject();
        TabAdapter tabAdapter = new TabAdapter(getActivity(), allItems);
        gridview.setAdapter(tabAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private List<ItemObject> getAllItemObject(){
        List<ItemObject> items = new ArrayList<>();
        String url = "projects";
        String result=Webservice.readUrl(url,null);
        if(!result.equals("[]")) {

            try {

                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    items.add(new ItemObject( object.getString("img1"), object.getString("title"), object.getString("description")));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
//        new AsyncTask<Object, Object, String>(){
//
//            @Override
//            protected String doInBackground(Object... parms) {
//                String url = "projects";
//
//                return Webservice.readUrl(url,null);
//            }
//            @Override
//            protected void onPostExecute(String result){
//                if(result!= null) {
//                    if(!result.equals("[]")) {
//
//                        try {
//                            List<ItemObject> items = new ArrayList<>();
//                            JSONArray array = new JSONArray(result);
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject object = array.getJSONObject(i);
//                                items.add(new ItemObject(R.drawable.sss1,object.getString("title"), object.getString("description")));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//                }
//
//            }
//        }.execute();
//

        return items;
    }

}
