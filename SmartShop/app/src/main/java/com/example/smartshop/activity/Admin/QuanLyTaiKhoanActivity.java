package com.example.smartshop.activity.Admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartshop.R;
import com.example.smartshop.adapter.QuanLyTaiKhoanAdapter;
import com.example.smartshop.model.User;
import com.example.smartshop.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyTaiKhoanActivity extends AppCompatActivity {

    EditText edtEmail, edtMatKhau, edtHoTen;
    Spinner spinnerVaiTro;
    Button btnThem, btnSua, btnXoa;
    ListView listViewTaiKhoan;
    ArrayList<User> mangUser;
    QuanLyTaiKhoanAdapter userAdapter;
    ArrayAdapter<String> vaiTroAdapter;
    int selectedPosition = -1;
    String matKhauGoc = ""; // Biến để lưu mật khẩu gốc
    String emailGoc = ""; // Biến để lưu email gốc
    String hoTenGoc = ""; // Biến để lưu họ tên gốc
    String vaiTroGoc = ""; // Biến để lưu vai trò gốc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);

        // Ánh xạ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarQuanLyTaiKhoan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.getNavigationIcon().setTint(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(v -> finish());

        // Ánh xạ các thành phần
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtHoTen = findViewById(R.id.edtHoTen);
        spinnerVaiTro = findViewById(R.id.spinnerVaiTro);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        listViewTaiKhoan = findViewById(R.id.listViewTaiKhoan);

        // Khởi tạo Spinner Vai trò
        String[] vaiTroArray = {"user", "admin"};
        vaiTroAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vaiTroArray);
        vaiTroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaiTro.setAdapter(vaiTroAdapter);

        // Khởi tạo ListView
        mangUser = new ArrayList<>();
        userAdapter = new QuanLyTaiKhoanAdapter(this, mangUser);
        listViewTaiKhoan.setAdapter(userAdapter);

        // Ban đầu vô hiệu hóa nút Sửa
        btnSua.setEnabled(false);

        // Lấy dữ liệu tài khoản từ server
        getUsersFromServer();

        // Xử lý sự kiện nút Thêm
        btnThem.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();
            String hoTen = edtHoTen.getText().toString().trim();
            String vaiTro = spinnerVaiTro.getSelectedItem().toString();

            if (email.isEmpty() || matKhau.isEmpty() || hoTen.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            themUserToServer(email, matKhau, hoTen, vaiTro);
        });

        // Xử lý sự kiện khi chọn một tài khoản trong ListView
        listViewTaiKhoan.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            User user = mangUser.get(position);
            emailGoc = user.getEmail();
            hoTenGoc = user.getHoTen();
            vaiTroGoc = user.getVaiTro();
            matKhauGoc = user.getMatKhau();

            edtEmail.setText(emailGoc);
            if (matKhauGoc.startsWith("$2y$10$")) {
                edtMatKhau.setText(""); // Để trống nếu mật khẩu đã mã hóa
                Toast.makeText(this, "Mật khẩu đã được mã hóa, vui lòng nhập mật khẩu mới nếu muốn thay đổi", Toast.LENGTH_SHORT).show();
            } else {
                edtMatKhau.setText(matKhauGoc); // Hiển thị mật khẩu nếu là plaintext
            }
            edtHoTen.setText(hoTenGoc);
            spinnerVaiTro.setSelection(vaiTroAdapter.getPosition(vaiTroGoc));

            // Kích hoạt kiểm tra thay đổi
            checkForChanges();
        });

        // Xử lý sự kiện nút Sửa
        btnSua.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một tài khoản để sửa", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = edtEmail.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();
            String hoTen = edtHoTen.getText().toString().trim();
            String vaiTro = spinnerVaiTro.getSelectedItem().toString();

            if (email.isEmpty() || hoTen.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin (trừ mật khẩu)", Toast.LENGTH_SHORT).show();
                return;
            }

            int idUser = mangUser.get(selectedPosition).getId();
            if (matKhau.isEmpty()) {
                suaUserOnServer(idUser, email, null, hoTen, vaiTro);
            } else {
                suaUserOnServer(idUser, email, matKhau, hoTen, vaiTro);
            }
        });

        // Xử lý sự kiện nút Xóa
        btnXoa.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một tài khoản để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa tài khoản này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int idUser = mangUser.get(selectedPosition).getId();
                        xoaUserOnServer(idUser);
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    // Hàm kiểm tra thay đổi để kích hoạt/vô hiệu hóa nút Sửa
    private void checkForChanges() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateSuaButtonState();
            }
        };

        edtEmail.addTextChangedListener(textWatcher);
        edtMatKhau.addTextChangedListener(textWatcher);
        edtHoTen.addTextChangedListener(textWatcher);

        spinnerVaiTro.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateSuaButtonState();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        updateSuaButtonState();
    }

    // Cập nhật trạng thái nút Sửa
    private void updateSuaButtonState() {
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String hoTen = edtHoTen.getText().toString().trim();
        String vaiTro = spinnerVaiTro.getSelectedItem().toString();

        boolean hasChanges = !email.equals(emailGoc) ||
                !hoTen.equals(hoTenGoc) ||
                !vaiTro.equals(vaiTroGoc) ||
                (!matKhau.isEmpty() && !matKhau.equals(matKhauGoc));

        btnSua.setEnabled(hasChanges);
    }

    private void getUsersFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetUsers, response -> {
            mangUser.clear();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String email = jsonObject.getString("email");
                    String matKhau = jsonObject.getString("matkhau");
                    String hoTen = jsonObject.getString("hoten");
                    String vaiTro = jsonObject.getString("vaitro");
                    mangUser.add(new User(id, email, matKhau, hoTen, vaiTro));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            userAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void themUserToServer(String email, String matKhau, String hoTen, String vaiTro) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemUser,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getUsersFromServer();
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("matkhau", matKhau);
                params.put("hoten", hoTen);
                params.put("vaitro", vaiTro);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void suaUserOnServer(int id, String email, String matKhau, String hoTen, String vaiTro) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanSuaUser,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Sửa tài khoản thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getUsersFromServer();
                        selectedPosition = -1;
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("email", email);
                if (matKhau != null) {
                    params.put("matkhau", matKhau); // Gửi mật khẩu mới (sẽ được mã hóa ở server)
                }
                params.put("hoten", hoTen);
                params.put("vaitro", vaiTro);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void xoaUserOnServer(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanXoaUser,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getUsersFromServer();
                        selectedPosition = -1;
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void clearInputFields() {
        edtEmail.setText("");
        edtMatKhau.setText("");
        edtHoTen.setText("");
        spinnerVaiTro.setSelection(0);
        matKhauGoc = "";
        emailGoc = "";
        hoTenGoc = "";
        vaiTroGoc = "";
        btnSua.setEnabled(false); // Vô hiệu hóa nút Sửa sau khi xóa hoặc thêm
    }
}