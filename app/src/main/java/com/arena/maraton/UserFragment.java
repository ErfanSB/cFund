package com.arena.maraton;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    public final static String ITEMS_COUNT_KEY = "ProjectFragment$ItemsCount";

    public static UserFragment createInstance(int itemsCount) {
        UserFragment ProjectFragment = new UserFragment();
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
                String url = "users";
                return Webservice.readUrl(url, null);
            }

            @Override
            protected void onPostExecute(String result) {
                List<ModelSarmaye> items = new ArrayList<>();
                if (!result.equals("[]")) {
                    try {
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String image = object.getString("img");
                            String backs = object.getString("backs");
                            String projects = object.getString("projects");
                            String fav = "تکنولوژی";
                            ModelSarmaye s = new ModelSarmaye(id, image, name, fav, backs, projects);
                            items.add(s);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    itemUAdapter recyclerAdapter = new itemUAdapter(items);
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }
        }.execute();

    }


}
