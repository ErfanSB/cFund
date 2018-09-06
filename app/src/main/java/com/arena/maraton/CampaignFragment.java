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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CampaignFragment extends Fragment {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";

    public static CampaignFragment createInstance(int itemsCount) {
        CampaignFragment partThreeFragment = new CampaignFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
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
                String url =  "campaign";
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
                            items.add(new ItemObject(object.getString("id"), object.getString("img1"), object.getString("title"), object.getString("description")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                itemsCAdapter recyclerAdapter = new itemsCAdapter(items);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }.execute();

    }


}
