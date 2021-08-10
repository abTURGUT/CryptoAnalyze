package com.tt.cryptoanalyze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    TextView errorTXT;
    EditText usernameTXT, passwordTXT;
    Button loginBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorTXT = findViewById(R.id.errorTXT);
        usernameTXT = findViewById(R.id.usernameTXT);
        passwordTXT = findViewById(R.id.passwordTXT);
        loginBTN = findViewById(R.id.loginBTN);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTXT.getText().toString() != null ? usernameTXT.getText().toString() : "";
                String password = passwordTXT.getText().toString() != null ? passwordTXT.getText().toString() : "";;

                errorTXT.setText("");
                Login(username, password);
            }
        });
    }

    public void Login(String username, String password){
        OkHttpClient client = new OkHttpClient();
        String url = "https://kontoryazilim.com/stajLogin.php?&username=" + username + "&password=" + password;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                WriteErrorMessage("Giriş yapılırken hata meydana geldi !");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String myResponse = response.body().string();

                    LoginActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {


                            if(myResponse.contains("ACTIVE")){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(myResponse.contains("WRONG USER INFO")){
                                WriteErrorMessage("Kullanıcı adı veya şifre yanlış !");
                            }

                        }
                    });


                }
            }
        });
    }

    public void WriteErrorMessage(String message){
        errorTXT.setVisibility(View.VISIBLE);
        errorTXT.setText(message);
    }
}