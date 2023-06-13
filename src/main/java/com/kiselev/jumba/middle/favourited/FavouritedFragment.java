package com.kiselev.jumba.middle.favourited;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kiselev.jumba.R;
import com.kiselev.jumba.middle.store.Store;
import com.kiselev.jumba.middle.store.StoreAdapter;
import com.kiselev.jumba.senior.product.ProductDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouritedFragment extends Fragment implements FavouritedAdapter.RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Favourited> favourited;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourited, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_fav);
        favourited = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        FavouritedAdapter adapter = new FavouritedAdapter(rootView.getContext(), favourited, this);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "http://192.168.1.17/jumba/favourited.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d("Response", response);
                        if (!response.isEmpty()) { // Проверка на пустую строку
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) { // Проверка на пустой массив
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String description = jsonObject.getString("description");
                                    int cost = Integer.parseInt(jsonObject.getString("cost"));
                                    String id = jsonObject.getString("user_id");
                                    if (id.equals(userId)) {
                                        favourited.add(new Favourited(name, description, cost, R.drawable.profile));
                                    }
                                }
                            } else {
                                // Добавьте код по умолчанию, который будет выполнен при пустом массиве JSON
                                //favourited.add(new Favourited("Default Name", "Default Description", 0, R.drawable.default_image));
                            }
                        } else {
                            // Добавьте код по умолчанию, который будет выполнен при пустом JSON
                            //favourited.add(new Favourited("Default Name", "Default Description", 0, R.drawable.default_image));
                        }

                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                });

        queue.add(stringRequest);
        return rootView;
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(requireContext(), ProductDescription.class);
        Favourited selectedStore = favourited.get(position);
        intent.putExtra("name", selectedStore.getName());
        intent.putExtra("description", selectedStore.getDescription());
        intent.putExtra("cost", selectedStore.getCost());
        intent.putExtra("imageResourceId", selectedStore.getImage());
        startActivity(intent);
    }
}
