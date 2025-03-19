package com.example.smartshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
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

public class SignUp extends AppCompatActivity {

    EditText editTextFullName, editTextUserName, editTextPassword, editTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextFullName = findViewById(R.id.fullname);
        editTextUserName = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        // Điều hướng tới trang đăng nhập
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện khi nhấn nút đăng ký
        buttonSignUp.setOnClickListener(v -> {
            String fullname = editTextFullName.getText().toString().trim();
            String username = editTextUserName.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();

            // Kiểm tra các trường không được bỏ trống
            if (fullname.isEmpty()) {
                editTextFullName.setError("Họ tên không được bỏ trống!");
                editTextFullName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                editTextEmail.setError("Email không được bỏ trống!");
                editTextEmail.requestFocus();
                return;
            }

            // Kiểm tra định dạng email hợp lệ
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Email không hợp lệ!");
                editTextEmail.requestFocus();
                return;
            }
            if (username.isEmpty()) {
                editTextUserName.setError("Tên đăng nhập không được bỏ trống!");
                editTextUserName.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Mật khẩu không được bỏ trống!");
                editTextPassword.requestFocus();
                return;
            }

            // Kiểm tra độ dài mật khẩu (ít nhất 6 ký tự)
            if (password.length() < 6) {
                editTextPassword.setError("Mật khẩu phải có ít nhất 6 ký tự!");
                editTextPassword.requestFocus();
                return;
            }

            // Tiến hành đăng ký nếu dữ liệu hợp lệ
            progressBar.setVisibility(View.VISIBLE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = {"fullname", "username", "password", "email"};
                String[] data = {fullname, username, password, email};

                PutData putData = new PutData(Server.DuongDanSignUp, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        switch (result) {
                            case "Sign Up Success":
                                Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "Email already in use":
                                Toast.makeText(getApplicationContext(), "Email đã được sử dụng!", Toast.LENGTH_SHORT).show();
                                break;
                            case "Username already exists":
                                Toast.makeText(getApplicationContext(), "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            });
        });
    }
}
