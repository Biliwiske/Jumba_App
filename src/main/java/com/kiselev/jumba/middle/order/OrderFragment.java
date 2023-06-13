package com.kiselev.jumba.middle.order;

import android.content.Context;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class OrderFragment extends Fragment{
    private RecyclerView recyclerView;
    private ArrayList<Order> order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_order);
        order = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        OrderAdapter adapter = new OrderAdapter(rootView.getContext(), order);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "http://192.168.1.17/jumba/order.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d("Response", response); // Добавьте эту строку для вывода response в лог
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String date = jsonObject.getString("date");
                            String product = jsonObject.getString("product");
                            String status = jsonObject.getString("status");
                            String id = jsonObject.getString("user_id");

                            if(id.equals(userId)){
                                order.add(new Order(date, product, status, R.drawable.profile));
                            }
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
}
