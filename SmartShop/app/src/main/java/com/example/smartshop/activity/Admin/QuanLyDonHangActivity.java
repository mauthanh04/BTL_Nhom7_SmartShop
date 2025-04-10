package com.example.smartshop.activity.Admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.smartshop.adapter.QuanLyDonHangAdapter;
import com.example.smartshop.model.DonHang;
import com.example.smartshop.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyDonHangActivity extends AppCompatActivity {

    EditText edtTenKhachHang, edtSoDienThoai, edtEmail;
    Button btnThem, btnSua, btnXoa;
    ListView listViewDonHang;
    ArrayList<DonHang> mangDonHang;
    QuanLyDonHangAdapter donHangAdapter;
    int selectedPosition = -1;
    String tenKhachHangGoc = "";
    String soDienThoaiGoc = "";
    String emailGoc = "";
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);

        // Ánh xạ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarQuanLyDonHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.getNavigationIcon().setTint(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(v -> finish());

        // Ánh xạ các thành phần
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        listViewDonHang = findViewById(R.id.listViewDonHang);

        // Khởi tạo ListView
        mangDonHang = new ArrayList<>();
        donHangAdapter = new QuanLyDonHangAdapter(this, mangDonHang);
        listViewDonHang.setAdapter(donHangAdapter);

        // Ban đầu vô hiệu hóa nút Sửa
        btnSua.setEnabled(false);

        // Lấy dữ liệu đơn hàng từ server
        getDonHangFromServer();

        // Xử lý sự kiện nút Thêm
        btnThem.setOnClickListener(v -> {
            String tenKhachHang = edtTenKhachHang.getText().toString().trim();
            String soDienThoai = edtSoDienThoai.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (tenKhachHang.isEmpty() || soDienThoai.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            themDonHangToServer(tenKhachHang, soDienThoai, email);
        });

        // Xử lý sự kiện khi chọn một đơn hàng trong ListView
        listViewDonHang.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            DonHang donHang = mangDonHang.get(position);
            tenKhachHangGoc = donHang.getTenKhachHang();
            soDienThoaiGoc = donHang.getSoDienThoai();
            emailGoc = donHang.getEmail();

            edtTenKhachHang.setText(tenKhachHangGoc);
            edtSoDienThoai.setText(soDienThoaiGoc);
            edtEmail.setText(emailGoc);

            // Kích hoạt kiểm tra thay đổi
            checkForChanges();
        });

        // Xử lý sự kiện nhấn giữ để chuyển sang màn hình quản lý chi tiết đơn hàng
        listViewDonHang.setOnItemLongClickListener((parent, view, position, id) -> {
            if (isLoading) {
                Toast.makeText(this, "Đang làm mới danh sách, vui lòng chờ!", Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent intent = new Intent(QuanLyDonHangActivity.this, QuanLyChiTietDonHangActivity.class);
            int maDonHang = mangDonHang.get(position).getId();
            Toast.makeText(this, "Mã đơn hàng: " + maDonHang, Toast.LENGTH_SHORT).show();
            intent.putExtra("madonhang", maDonHang);
            startActivity(intent);
            return true;
        });

        // Xử lý sự kiện nút Sửa
        btnSua.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một đơn hàng để sửa", Toast.LENGTH_SHORT).show();
                return;
            }

            String tenKhachHang = edtTenKhachHang.getText().toString().trim();
            String soDienThoai = edtSoDienThoai.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (tenKhachHang.isEmpty() || soDienThoai.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int idDonHang = mangDonHang.get(selectedPosition).getId();
            suaDonHangOnServer(idDonHang, tenKhachHang, soDienThoai, email);
        });

        // Xử lý sự kiện nút Xóa
        btnXoa.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một đơn hàng để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa đơn hàng này? Tất cả chi tiết đơn hàng cũng sẽ bị xóa.")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int idDonHang = mangDonHang.get(selectedPosition).getId();
                        xoaDonHangOnServer(idDonHang);
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

        edtTenKhachHang.addTextChangedListener(textWatcher);
        edtSoDienThoai.addTextChangedListener(textWatcher);
        edtEmail.addTextChangedListener(textWatcher);

        updateSuaButtonState();
    }

    // Cập nhật trạng thái nút Sửa
    private void updateSuaButtonState() {
        String tenKhachHang = edtTenKhachHang.getText().toString().trim();
        String soDienThoai = edtSoDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        boolean hasChanges = !tenKhachHang.equals(tenKhachHangGoc) ||
                !soDienThoai.equals(soDienThoaiGoc) ||
                !email.equals(emailGoc);

        btnSua.setEnabled(hasChanges);
    }

    private void getDonHangFromServer() {
        getDonHangFromServerWithCallback(null);
    }

    private void getDonHangFromServerWithCallback(Runnable callback) {
        isLoading = true;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetDonHang, response -> {
            mangDonHang.clear();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String tenKhachHang = jsonObject.getString("tenkhachhang");
                    String soDienThoai = jsonObject.getString("sodienthoai");
                    String email = jsonObject.getString("email");
                    mangDonHang.add(new DonHang(id, tenKhachHang, soDienThoai, email));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            donHangAdapter.notifyDataSetChanged();
            isLoading = false;
            if (callback != null) {
                callback.run();
            }
        }, error -> {
            Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show();
            isLoading = false;
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void themDonHangToServer(String tenKhachHang, String soDienThoai, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getDonHangFromServerWithCallback(() -> {
                            Toast.makeText(this, "Danh sách đã được làm mới", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tenkhachhang", tenKhachHang);
                params.put("sodienthoai", soDienThoai);
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void suaDonHangOnServer(int id, String tenKhachHang, String soDienThoai, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanSuaDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Sửa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getDonHangFromServer();
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
                params.put("tenkhachhang", tenKhachHang);
                params.put("sodienthoai", soDienThoai);
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void xoaDonHangOnServer(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanXoaDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getDonHangFromServer();
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
        edtTenKhachHang.setText("");
        edtSoDienThoai.setText("");
        edtEmail.setText("");
        tenKhachHangGoc = "";
        soDienThoaiGoc = "";
        emailGoc = "";
        btnSua.setEnabled(false);
    }
}