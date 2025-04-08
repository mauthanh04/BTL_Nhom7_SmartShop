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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartshop.R;
import com.example.smartshop.ultil.Server;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText editTextEmail, editTextMatKhau, editTextHoTen;
    RadioGroup radioGroupVaiTro;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ
        editTextEmail = findViewById(R.id.email);
        editTextMatKhau = findViewById(R.id.matkhau);
        radioGroupVaiTro = findViewById(R.id.roleRadioGroup); // Sử dụng RadioGroup thay vì EditText
        editTextHoTen = findViewById(R.id.hoten);
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
            String email = editTextEmail.getText().toString().trim();
            String matkhau = editTextMatKhau.getText().toString().trim();
            String hoten = editTextHoTen.getText().toString().trim();

            // Lấy giá trị từ RadioGroup
            int selectedRoleId = radioGroupVaiTro.getCheckedRadioButtonId();
            String vaitro;
            if (selectedRoleId == R.id.radioUser) {
                vaitro = "user";
            } else if (selectedRoleId == R.id.radioAdmin) {
                vaitro = "admin";
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng chọn vai trò!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra các trường không được bỏ trống
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
            if (matkhau.isEmpty()) {
                editTextMatKhau.setError("Mật khẩu không được bỏ trống!");
                editTextMatKhau.requestFocus();
                return;
            }
            // Kiểm tra độ dài mật khẩu (ít nhất 6 ký tự)
            if (matkhau.length() < 6) {
                editTextMatKhau.setError("Mật khẩu phải có ít nhất 6 ký tự!");
                editTextMatKhau.requestFocus();
                return;
            }
            if (hoten.isEmpty()) {
                editTextHoTen.setError("Họ tên không được bỏ trống!");
                editTextHoTen.requestFocus();
                return;
            }

            // Tiến hành đăng ký nếu dữ liệu hợp lệ
            progressBar.setVisibility(View.VISIBLE);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = {"email", "matkhau", "vaitro", "hoten"};
                String[] data = {email, matkhau, vaitro, hoten};

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
