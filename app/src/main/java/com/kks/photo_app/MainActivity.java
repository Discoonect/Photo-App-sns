package com.kks.photo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kks.photo_app.api.NetworkClient;
import com.kks.photo_app.api.UserApi;
import com.kks.photo_app.model.UserReq;
import com.kks.photo_app.model.UserRes;
import com.kks.photo_app.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText edit_email;
    EditText edit_passwd1;
    EditText edit_passwd2;
    Button btn_register;
    Button btn_login;

    RequestQueue requestQueue;

    String email;
    String passwd1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        String token = sharedPreferences.getString("token",null);

        if(token !=null){
            Intent i = new Intent(MainActivity.this,Welcome.class);
            startActivity(i);
            finish();
            return;
        }

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        edit_email = findViewById(R.id.edit_email);
        edit_passwd1 = findViewById(R.id.edit_passwd1);
        edit_passwd2 = findViewById(R.id.edit_passwd2);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edit_email.getText().toString().trim();
                passwd1 = edit_passwd1.getText().toString().trim();
                String passwd2 = edit_passwd2.getText().toString().trim();

                if(email.contains("@") == false){
                    Toast.makeText(MainActivity.this, "이메일 형식이 올바르지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwd1.length() < 4 || passwd1.length() > 12){
                    Toast.makeText(MainActivity.this, "비밀번호 길이는 4자리 이상,12자리 이하입니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwd1.equalsIgnoreCase(passwd2) == false){
                    Toast.makeText(MainActivity.this, "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // body 셋팅
                UserReq userReq = new UserReq(email,passwd1);

                // 서버로 바디 전송
                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.createUser(userReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        if(response.isSuccessful()){
                            boolean success = response.body().isSuccess();
                            String token = response.body().getToken();
                            Log.i("aaa", "success : " + success + ", token : "+token);

                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,
                                    MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Intent i = new Intent(MainActivity.this, Welcome.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });



            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });


    }
}