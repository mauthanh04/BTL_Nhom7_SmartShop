package com.example.smartshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartshop.R;
import com.example.smartshop.ultil.Server;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        // Điều hướng tới trang đăng ký
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện khi nhấn nút đăng nhập
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Kiểm tra điều kiện ngoại lệ
            if (username.isEmpty()) {
                editTextUserName.setError("Username không được bỏ trống!");
                editTextUserName.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Password không được bỏ trống!");
                editTextPassword.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // 🟢 Xử lý đăng nhập với API
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = {"username", "password"};
                String[] data = {username, password};

                PutData putData = new PutData(Server.DuongDanLogin, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        switch (result) {
                            case "Login Success":
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "Invalid Username or Password":
                                Toast.makeText(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                                break;
                            case "Error: Database connection":
                                Toast.makeText(getApplicationContext(), "Lỗi kết nối cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            });
        });
    }
}
