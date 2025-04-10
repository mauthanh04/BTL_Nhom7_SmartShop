package com.example.smartshop.activity.Admin;

import android.app.AlertDialog;
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
import com.example.smartshop.adapter.QuanLyChiTietDonHangAdapter;
import com.example.smartshop.model.ChiTietDonHang;
import com.example.smartshop.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyChiTietDonHangActivity extends AppCompatActivity {

    EditText edtMaSanPham, edtTenSanPham, edtGiaSanPham, edtSoLuongSanPham;
    Button btnThem, btnSua, btnXoa;
    ListView listViewChiTietDonHang;
    ArrayList<ChiTietDonHang> mangChiTietDonHang;
    QuanLyChiTietDonHangAdapter chiTietDonHangAdapter;
    int selectedPosition = -1;
    String maSanPhamGoc = "";
    String tenSanPhamGoc = "";
    int giaSanPhamGoc = 0;
    int soLuongSanPhamGoc = 0;
    int maDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_chi_tiet_don_hang);

        // Ánh xạ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarChiTietDonHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.getNavigationIcon().setTint(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(v -> finish());

        // Lấy mã đơn hàng từ Intent
        maDonHang = getIntent().getIntExtra("madonhang", -1);
        Toast.makeText(this, "Mã đơn hàng nhận được: " + maDonHang, Toast.LENGTH_SHORT).show();
        if (maDonHang == -1) {
            Toast.makeText(this, "Không tìm thấy mã đơn hàng. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ánh xạ các thành phần
        edtMaSanPham = findViewById(R.id.edtMaSanPham);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGiaSanPham = findViewById(R.id.edtGiaSanPham);
        edtSoLuongSanPham = findViewById(R.id.edtSoLuongSanPham);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        listViewChiTietDonHang = findViewById(R.id.listViewChiTietDonHang);

        // Khởi tạo ListView
        mangChiTietDonHang = new ArrayList<>();
        chiTietDonHangAdapter = new QuanLyChiTietDonHangAdapter(this, mangChiTietDonHang);
        listViewChiTietDonHang.setAdapter(chiTietDonHangAdapter);

        // Ban đầu vô hiệu hóa nút Sửa
        btnSua.setEnabled(false);

        // Lấy dữ liệu chi tiết đơn hàng từ server
        getChiTietDonHangFromServer();

        // Xử lý sự kiện nút Thêm
        btnThem.setOnClickListener(v -> {
            String maSanPham = edtMaSanPham.getText().toString().trim();
            String tenSanPham = edtTenSanPham.getText().toString().trim();
            String giaSanPhamStr = edtGiaSanPham.getText().toString().trim();
            String soLuongSanPhamStr = edtSoLuongSanPham.getText().toString().trim();

            if (maSanPham.isEmpty() || tenSanPham.isEmpty() || giaSanPhamStr.isEmpty() || soLuongSanPhamStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int giaSanPham;
            int soLuongSanPham;
            try {
                giaSanPham = Integer.parseInt(giaSanPhamStr);
                soLuongSanPham = Integer.parseInt(soLuongSanPhamStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá và số lượng phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            themChiTietDonHangToServer(maSanPham, tenSanPham, giaSanPham, soLuongSanPham);
        });

        // Xử lý sự kiện khi chọn một chi tiết đơn hàng trong ListView
        listViewChiTietDonHang.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            ChiTietDonHang chiTiet = mangChiTietDonHang.get(position);
            maSanPhamGoc = chiTiet.getMaSanPham();
            tenSanPhamGoc = chiTiet.getTenSanPham();
            giaSanPhamGoc = chiTiet.getGiaSanPham();
            soLuongSanPhamGoc = chiTiet.getSoLuongSanPham();

            edtMaSanPham.setText(maSanPhamGoc);
            edtTenSanPham.setText(tenSanPhamGoc);
            edtGiaSanPham.setText(String.valueOf(giaSanPhamGoc));
            edtSoLuongSanPham.setText(String.valueOf(soLuongSanPhamGoc));

            // Kích hoạt kiểm tra thay đổi
            checkForChanges();
        });

        // Xử lý sự kiện nút Sửa
        btnSua.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một chi tiết đơn hàng để sửa", Toast.LENGTH_SHORT).show();
                return;
            }

            String maSanPham = edtMaSanPham.getText().toString().trim();
            String tenSanPham = edtTenSanPham.getText().toString().trim();
            String giaSanPhamStr = edtGiaSanPham.getText().toString().trim();
            String soLuongSanPhamStr = edtSoLuongSanPham.getText().toString().trim();

            if (maSanPham.isEmpty() || tenSanPham.isEmpty() || giaSanPhamStr.isEmpty() || soLuongSanPhamStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int giaSanPham;
            int soLuongSanPham;
            try {
                giaSanPham = Integer.parseInt(giaSanPhamStr);
                soLuongSanPham = Integer.parseInt(soLuongSanPhamStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá và số lượng phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            int idChiTiet = mangChiTietDonHang.get(selectedPosition).getId();
            suaChiTietDonHangOnServer(idChiTiet, maSanPham, tenSanPham, giaSanPham, soLuongSanPham);
        });

        // Xử lý sự kiện nút Xóa
        btnXoa.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một chi tiết đơn hàng để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa chi tiết đơn hàng này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int idChiTiet = mangChiTietDonHang.get(selectedPosition).getId();
                        xoaChiTietDonHangOnServer(idChiTiet);
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

        edtMaSanPham.addTextChangedListener(textWatcher);
        edtTenSanPham.addTextChangedListener(textWatcher);
        edtGiaSanPham.addTextChangedListener(textWatcher);
        edtSoLuongSanPham.addTextChangedListener(textWatcher);

        updateSuaButtonState();
    }

    // Cập nhật trạng thái nút Sửa
    private void updateSuaButtonState() {
        String maSanPham = edtMaSanPham.getText().toString().trim();
        String tenSanPham = edtTenSanPham.getText().toString().trim();
        String giaSanPhamStr = edtGiaSanPham.getText().toString().trim();
        String soLuongSanPhamStr = edtSoLuongSanPham.getText().toString().trim();

        int giaSanPham = giaSanPhamStr.isEmpty() ? 0 : Integer.parseInt(giaSanPhamStr);
        int soLuongSanPham = soLuongSanPhamStr.isEmpty() ? 0 : Integer.parseInt(soLuongSanPhamStr);

        boolean hasChanges = !maSanPham.equals(maSanPhamGoc) ||
                !tenSanPham.equals(tenSanPhamGoc) ||
                giaSanPham != giaSanPhamGoc ||
                soLuongSanPham != soLuongSanPhamGoc;

        btnSua.setEnabled(hasChanges);
    }

    private void getChiTietDonHangFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.DuongDanGetChiTietDonHang + "?madonhang=" + maDonHang;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            mangChiTietDonHang.clear();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int maDonHang = jsonObject.getInt("madonhang");
                    String maSanPham = jsonObject.getString("masanpham");
                    String tenSanPham = jsonObject.getString("tensanpham");
                    int giaSanPham = jsonObject.getInt("giasanpham");
                    int soLuongSanPham = jsonObject.getInt("soluongsanpham");
                    mangChiTietDonHang.add(new ChiTietDonHang(id, maDonHang, maSanPham, tenSanPham, giaSanPham, soLuongSanPham));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            chiTietDonHangAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void themChiTietDonHangToServer(String maSanPham, String tenSanPham, int giaSanPham, int soLuongSanPham) {
        if (maDonHang <= 0) {
            Toast.makeText(this, "Mã đơn hàng không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đang thêm với mã đơn hàng: " + maDonHang, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemChiTietDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Thêm chi tiết đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getChiTietDonHangFromServer();
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("madonhang", String.valueOf(maDonHang));
                params.put("masanpham", maSanPham);
                params.put("tensanpham", tenSanPham);
                params.put("giasanpham", String.valueOf(giaSanPham));
                params.put("soluongsanpham", String.valueOf(soLuongSanPham));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void suaChiTietDonHangOnServer(int id, String maSanPham, String tenSanPham, int giaSanPham, int soLuongSanPham) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanSuaChiTietDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Sửa chi tiết đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getChiTietDonHangFromServer();
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
                params.put("masanpham", maSanPham);
                params.put("tensanpham", tenSanPham);
                params.put("giasanpham", String.valueOf(giaSanPham));
                params.put("soluongsanpham", String.valueOf(soLuongSanPham));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void xoaChiTietDonHangOnServer(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanXoaChiTietDonHang,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Xóa chi tiết đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getChiTietDonHangFromServer();
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
        edtMaSanPham.setText("");
        edtTenSanPham.setText("");
        edtGiaSanPham.setText("");
        edtSoLuongSanPham.setText("");
        maSanPhamGoc = "";
        tenSanPhamGoc = "";
        giaSanPhamGoc = 0;
        soLuongSanPhamGoc = 0;
        btnSua.setEnabled(false);
    }
}