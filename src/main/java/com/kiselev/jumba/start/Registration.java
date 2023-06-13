package com.kiselev.jumba.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kiselev.jumba.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Registration extends AppCompatActivity {
    EditText name,email,password, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name = findViewById(R.id.person_name);
        email = findViewById(R.id.person_email);
        password = findViewById(R.id.person_password);
        address = findViewById(R.id.person_address);
    }

    public void SignUp(View view) {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userAddress = address.getText().toString();
        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Вы не ввели имя пользователя", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Вы не ввели email пользователя", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userAddress)) {
            Toast.makeText(this, "Вы не ввели адрес", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6) {
            Toast.makeText(this, "Пароль должен быть больше 6 символов", Toast.LENGTH_SHORT).show();
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[4];
                field[0] = "username";
                field[1] = "email";
                field[2] = "password";
                field[3] = "address";

                String[] data = new String[4];
                data[0] = userName;
                data[1] = userEmail;
                data[2] = userPassword;
                data[3] = userAddress;
                PutData putData = new PutData("http://192.168.1.17/jumba/signup.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if(result.equals("Registration Successful")){
                            Intent intent = new Intent(getApplicationContext(), Authorization.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public void ToLogin(View view){
        Intent intent = new Intent(getApplicationContext(), Authorization.class);
        startActivity(intent);
        finish();
    }
}
