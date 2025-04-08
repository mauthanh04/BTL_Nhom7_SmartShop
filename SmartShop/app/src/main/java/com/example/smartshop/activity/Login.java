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
import com.example.smartshop.activity.Admin.MainAdminActivity;
import com.example.smartshop.activity.User.MainUserActivity;
import com.example.smartshop.activity.User.MayTinhActivity;
import com.example.smartshop.ultil.Server;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    // Khai báo biến giao diện
    EditText editTextEmail, editTextMatKhau;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ View từ XML
        editTextEmail = findViewById(R.id.email);
        editTextMatKhau = findViewById(R.id.matkhau);
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
            String email = editTextEmail.getText().toString().trim();
            String matkhau = editTextMatKhau.getText().toString().trim();

            // Kiểm tra điều kiện ngoại lệ
            if (email.isEmpty()) {
                editTextEmail.setError("Email không được bỏ trống!");
                editTextEmail.requestFocus();
                return;
            }

            if (matkhau.isEmpty()) {
                editTextMatKhau.setError("Mật khẩu không được bỏ trống!");
                editTextMatKhau.requestFocus();
                return;
            }

            // Hiển thị ProgressBar khi đăng nhập
            progressBar.setVisibility(View.VISIBLE);

            // 🟢 Xử lý đăng nhập với API
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = {"email", "matkhau"};
                String[] data = {email, matkhau};

                PutData putData = new PutData(Server.DuongDanLogin, "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();

                        if (result.startsWith("Login Success")) {
                            // Nhận kết quả trả về bao gồm vai trò
                            String vaitro = result.replace("Login Success", "");

                            Intent intent;
                            switch (vaitro) {
                                case "user":
                                    intent = new Intent(getApplicationContext(), MainUserActivity.class);
                                    break;
                                case "admin":
                                    intent = new Intent(getApplicationContext(), MainAdminActivity.class);
                                    break;
                                default:
                                    // Trường hợp vai trò không xác định
                                    Toast.makeText(getApplicationContext(), "Vai trò không xác định!", Toast.LENGTH_SHORT).show();
                                    return;
                            }

                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else if (result.equals("Invalid Email or Password")) {
                            Toast.makeText(getApplicationContext(), "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        } else if (result.equals("Error: Database connection")) {
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        });
    }
}
