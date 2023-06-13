package com.kiselev.jumba.middle.profile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kiselev.jumba.R;
import com.kiselev.jumba.start.Authorization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        TextView namet = rootView.findViewById(R.id.userNameTextView);
        TextView addresst = rootView.findViewById(R.id.userAddressTextView);
        TextView emailt = rootView.findViewById(R.id.userEmailTextView);
        Button button1 = rootView.findViewById(R.id.exit_but);
        String userId = sharedPreferences.getString("userId", "");
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "http://192.168.1.17/jumba/id_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("username");
                            String address = jsonObject.getString("address");
                            String email = jsonObject.getString("email");
                            if(email.equals(userId)){
                                namet.setText(name);
                                addresst.setText(address);
                                emailt.setText(email);
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    // Обработка ошибки запроса
                });

        queue.add(stringRequest);
        button1.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("userId"); // Удаление значения с ключом "userId"
            editor.apply();
            Intent intent = new Intent(requireContext(), Authorization.class);
            startActivity(intent);
        });
        return rootView;
    }
}
