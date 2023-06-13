package com.kiselev.jumba.senior.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kiselev.jumba.R;
import com.kiselev.jumba.middle.BottomBarStart;
import com.kiselev.jumba.middle.favourited.Favourited;
import com.kiselev.jumba.middle.store.StoreFragment;
import com.kiselev.jumba.start.Authorization;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProductDescription extends AppCompatActivity{
    public String name_text, description_text;
    public int cost_text;
    public boolean check;

    public String userId;
    public ImageView image;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Intent intent = getIntent();

        TextView name = findViewById(R.id.product_name);
        TextView description = findViewById(R.id.product_description);
        TextView cost = findViewById(R.id.product_cost);
        image = findViewById(R.id.fava);
        name_text = intent.getStringExtra("name");
        description_text = intent.getStringExtra("description");
        cost_text = intent.getIntExtra("cost", 0);
        name.setText(name_text);
        description.setText(description_text);
        cost.setText("Цена: " + cost_text);
        int imageResourceId = intent.getIntExtra("imageResourceId", 0);
        CheckFav();
    }
    public void Buy(View view){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = new String[4];
            field[0] = "date";
            field[1] = "product";
            field[2] = "status";
            field[3] = "user_id";
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            String[] data = new String[4];
            data[0] = String.valueOf(formattedDate);
            data[1] = name_text;
            data[2] = "Создан";
            SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", "");
            data[3] = userId;
            PutData putData = new PutData("http://192.168.1.17/jumba/buy.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if(result.equals("Buy Successful")){
                        ((ScrollView)findViewById(R.id.container)).removeAllViews();
                        Fragment bottomBarStart = new BottomBarStart();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, bottomBarStart);
                        fragmentTransaction.commit();
                    }
                    else{
                        Log.e("BuyError", result);
                    }
                }
            }
        });
    }
    public void Back(View view){
        ((ScrollView)findViewById(R.id.container)).removeAllViews();
        Fragment bottomBarStart = new BottomBarStart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, bottomBarStart);
        fragmentTransaction.commit();
    }
    public void AddToFav(View view){
        if(check)
        {
            Log.e("HUYMAKAKI", "VHOD");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.1.17/jumba/unfavourited.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Log.e("Success", response);
                        CheckFav();
                    }, error -> {
                Log.e("Error", String.valueOf(error));
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name_text);
                    params.put("user_id", userId);
                    return params;
                }
            };
            queue.add(stringRequest);

        }
        else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                String[] field = new String[4];
                String[] data = new String[4];
                field[0] = "name";
                field[1] = "description";
                field[2] = "cost";
                field[3] = "user_id";
                data[0] = name_text;
                data[1] = description_text;
                data[2] = String.valueOf(cost_text);
                data[3] = userId;
                PutData putData = new PutData("http://192.168.1.17/jumba/favourited_add.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("Add Successful")) {
                            image.setImageResource(R.drawable.baseline_favorite_24);
                        } else {
                            Log.e("BuyError", result);
                        }
                    }
                }
            });
        }
    }

    public void CheckFav() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.17/jumba/favourited.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d("Response", response);
                        if (!response.isEmpty()) { // Проверка на пустую строку
                            JSONArray jsonArray = new JSONArray(response);
                            boolean isFavourited = false;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String id = jsonObject.getString("user_id");
                                if (id.equals(userId) && name.equals(name_text)) {
                                    isFavourited = true;
                                    break;
                                }
                            }
                            if (isFavourited) {
                                image.setImageResource(R.drawable.baseline_favorite_24);
                            } else {
                                image.setImageResource(R.drawable.favorite_12); // Замените на ваше исходное изображение
                            }
                            image.setOnClickListener(this::AddToFav);
                            check = isFavourited;
                        } else {
                            image.setImageResource(R.drawable.favorite_12); // Замените на ваше исходное изображение
                            image.setOnClickListener(this::AddToFav);
                            check = false;
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                });

        queue.add(stringRequest);
    }


}
