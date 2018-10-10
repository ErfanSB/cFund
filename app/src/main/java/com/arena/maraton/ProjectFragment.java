package com.arena.maraton;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

    public final static String ITEMS_COUNT_KEY = "ProjectFragment$ItemsCount";

    public static ProjectFragment createInstance(int itemsCount) {
        ProjectFragment ProjectFragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        ProjectFragment.setArguments(bundle);
        return ProjectFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_part_three, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    @SuppressLint("StaticFieldLeak")
    private void setupRecyclerView(RecyclerView recyclerView) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String url = "projects";
                return Webservice.readUrl(url, null);
            }

            @Override
            protected void onPostExecute(String result) {
                List<ItemObject> items = new ArrayList<>();
                if (!result.equals("[]")) {

                    try {

                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            long sumFund = 0;
                            if (object.getString("backs").contains("[")) {
                                JSONArray array1 = new JSONArray(object.getString("backs"));
                                for (int j = 0; j < array1.length(); j++) {
                                    JSONObject object1 = array1.getJSONObject(j);
                                    sumFund += Long.parseLong(object1.getString("fund"));
                                }
                            }
                            long needFund = Long.parseLong(object.getString("need_fund"));
                            int needTime = Integer.parseInt(object.getString("need_time"));

                            items.add(new ItemObject(object.getString("id"), object.getString("img1"), object.getString("title"), object.getString("description"),needFund,sumFund,needTime));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                itemsAdapter recyclerAdapter = new itemsAdapter(items);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }.execute();

    }


}
