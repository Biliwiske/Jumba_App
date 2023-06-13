package com.kiselev.jumba.start;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kiselev.jumba.middle.BottomBarStart;
import com.kiselev.jumba.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Authorization extends AppCompatActivity {
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
    }

    public void Login(View view) {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = {"email", "password"};
            String[] data = {userEmail, userPassword};

            PutData putData = new PutData("http://192.168.1.17/jumba/login.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Login Successful")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", userEmail);
                        editor.apply();
                        ((ConstraintLayout)findViewById(R.id.container)).removeAllViews();
                        Fragment bottomBarStart = new BottomBarStart();
                        showFragment(bottomBarStart);
                    } else if (result.equals("Wrong password or login")) {
                        Toast.makeText(getApplicationContext(), "Неправильный пароль либо логин", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println(result);
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void ToRegistration(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);
        finish();
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
