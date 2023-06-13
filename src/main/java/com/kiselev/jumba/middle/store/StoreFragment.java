package com.kiselev.jumba.middle.store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.kiselev.jumba.R;
import com.kiselev.jumba.senior.product.ProductDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class StoreFragment extends Fragment implements StoreAdapter.RecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Store> store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);
        recyclerView = rootView.findViewById(R.id.recycle);
        store = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        StoreAdapter adapter = new StoreAdapter(rootView.getContext(), store, this);
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "http://192.168.1.17/jumba/shop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                int cost = Integer.parseInt(jsonObject.getString("cost"));
                                store.add(new Store(name, description, cost, R.drawable.profile));
                            }
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Обработка ошибки запроса
                    }
                });

        queue.add(stringRequest);
        return rootView;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(requireContext(), ProductDescription.class);
        Store selectedStore = store.get(position);
        intent.putExtra("name", selectedStore.getName());
        intent.putExtra("description", selectedStore.getDescription());
        intent.putExtra("cost", selectedStore.getCost());
        intent.putExtra("imageResourceId", selectedStore.getImage());
        startActivity(intent);
    }

}
