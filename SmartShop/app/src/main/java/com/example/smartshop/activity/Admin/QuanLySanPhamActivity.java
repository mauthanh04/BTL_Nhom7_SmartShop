package com.example.smartshop.activity.Admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartshop.R;
import com.example.smartshop.adapter.QuanLySanPhamAdapter;
import com.example.smartshop.model.SanPham;
import com.example.smartshop.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLySanPhamActivity extends AppCompatActivity {

    EditText edtTenSanPham, edtGiaSanPham, edtHinhAnhSanPham, edtMoTaSanPham;
    Spinner spinnerLoaiSanPham;
    Button btnThem, btnSua, btnXoa;
    ListView listViewSanPham;
    ArrayList<SanPham> mangSanPham;
    QuanLySanPhamAdapter sanPhamAdapter;
    ArrayAdapter<String> spinnerAdapter;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        // Ánh xạ các thành phần
        Toolbar toolbar = findViewById(R.id.toolbalquanlysp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.getNavigationIcon().setTint(ContextCompat.getColor(this, android.R.color.white));
        toolbar.setNavigationOnClickListener(v -> finish());

        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGiaSanPham = findViewById(R.id.edtGiaSanPham);
        edtHinhAnhSanPham = findViewById(R.id.edtHinhAnhSanPham);
        edtMoTaSanPham = findViewById(R.id.edtMoTaSanPham);
        spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        listViewSanPham = findViewById(R.id.listViewSanPham);

        // Khởi tạo Spinner
        String[] loaiSanPham = {"Điện Thoại", "Máy Tính"};
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiSanPham);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiSanPham.setAdapter(spinnerAdapter);

        // Khởi tạo ListView
        mangSanPham = new ArrayList<>();
        sanPhamAdapter = new QuanLySanPhamAdapter(this, mangSanPham);
        listViewSanPham.setAdapter(sanPhamAdapter);

        // Lấy dữ liệu sản phẩm từ server
        getSanPhamFromServer();

        // Xử lý sự kiện nút Thêm
        btnThem.setOnClickListener(v -> {
            String tenSanPham = edtTenSanPham.getText().toString().trim();
            String giaSanPhamStr = edtGiaSanPham.getText().toString().trim();
            String hinhAnhSanPham = edtHinhAnhSanPham.getText().toString().trim();
            String moTaSanPham = edtMoTaSanPham.getText().toString().trim();
            int idLoaiSanPham = spinnerLoaiSanPham.getSelectedItemPosition() + 1;

            // Kiểm tra các trường nhập liệu
            if (tenSanPham.isEmpty() || giaSanPhamStr.isEmpty() || hinhAnhSanPham.isEmpty() || moTaSanPham.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra tên sản phẩm đã tồn tại chưa
            if (isProductNameExists(tenSanPham)) {
                Toast.makeText(this, "Tên sản phẩm đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra giá sản phẩm
            int giaSanPham;
            try {
                giaSanPham = Integer.parseInt(giaSanPhamStr);
                if (giaSanPham <= 0) {
                    Toast.makeText(this, "Giá sản phẩm phải là số dương", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá sản phẩm không hợp lệ, vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra URL hình ảnh
            if (!isValidUrl(hinhAnhSanPham)) {
                Toast.makeText(this, "URL hình ảnh không hợp lệ, phải bắt đầu bằng http:// hoặc https://", Toast.LENGTH_SHORT).show();
                return;
            }

            themSanPhamToServer(tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, idLoaiSanPham);
        });

        // Xử lý sự kiện khi chọn một sản phẩm trong ListView
        listViewSanPham.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            SanPham sanPham = mangSanPham.get(position);
            edtTenSanPham.setText(sanPham.getTenSanPham());
            edtGiaSanPham.setText(String.valueOf(sanPham.getGiaSanPham()));
            edtHinhAnhSanPham.setText(sanPham.getHinhAnhSanPham());
            edtMoTaSanPham.setText(sanPham.getMoTaSanPham());
            spinnerLoaiSanPham.setSelection(sanPham.getIdSanPham() - 1);
        });

        // Xử lý sự kiện nút Sửa
        btnSua.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một sản phẩm để sửa", Toast.LENGTH_SHORT).show();
                return;
            }

            String tenSanPham = edtTenSanPham.getText().toString().trim();
            String giaSanPhamStr = edtGiaSanPham.getText().toString().trim();
            String hinhAnhSanPham = edtHinhAnhSanPham.getText().toString().trim();
            String moTaSanPham = edtMoTaSanPham.getText().toString().trim();
            int idLoaiSanPham = spinnerLoaiSanPham.getSelectedItemPosition() + 1;

            // Kiểm tra các trường nhập liệu
            if (tenSanPham.isEmpty() || giaSanPhamStr.isEmpty() || hinhAnhSanPham.isEmpty() || moTaSanPham.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra tên sản phẩm đã tồn tại chưa (trừ sản phẩm đang sửa)
            if (isProductNameExistsForEdit(tenSanPham, selectedPosition)) {
                Toast.makeText(this, "Tên sản phẩm đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra giá sản phẩm
            int giaSanPham;
            try {
                giaSanPham = Integer.parseInt(giaSanPhamStr);
                if (giaSanPham <= 0) {
                    Toast.makeText(this, "Giá sản phẩm phải là số dương", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá sản phẩm không hợp lệ, vui lòng nhập số", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra URL hình ảnh
            if (!isValidUrl(hinhAnhSanPham)) {
                Toast.makeText(this, "URL hình ảnh không hợp lệ, phải bắt đầu bằng http:// hoặc https://", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy thông tin sản phẩm hiện tại để so sánh
            SanPham sanPhamHienTai = mangSanPham.get(selectedPosition);

            // Kiểm tra xem có thay đổi gì không
            if (tenSanPham.equals(sanPhamHienTai.getTenSanPham()) &&
                    giaSanPham == sanPhamHienTai.getGiaSanPham() &&
                    hinhAnhSanPham.equals(sanPhamHienTai.getHinhAnhSanPham()) &&
                    moTaSanPham.equals(sanPhamHienTai.getMoTaSanPham()) &&
                    idLoaiSanPham == sanPhamHienTai.getIdSanPham()) {
                Toast.makeText(this, "Không có gì để sửa", Toast.LENGTH_SHORT).show();
                return;
            }

            int idSanPham = sanPhamHienTai.getId();
            suaSanPhamOnServer(idSanPham, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, idLoaiSanPham);
        });

        // Xử lý sự kiện nút Xóa
        btnXoa.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một sản phẩm để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị dialog xác nhận trước khi xóa
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa sản phẩm " + mangSanPham.get(selectedPosition).getTenSanPham() + "?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int idSanPham = mangSanPham.get(selectedPosition).getId();
                        xoaSanPhamOnServer(idSanPham);
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    // Kiểm tra tên sản phẩm đã tồn tại chưa
    private boolean isProductNameExists(String tenSanPham) {
        for (SanPham sanPham : mangSanPham) {
            if (sanPham.getTenSanPham().equalsIgnoreCase(tenSanPham)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra tên sản phẩm đã tồn tại chưa khi sửa (bỏ qua sản phẩm đang sửa)
    private boolean isProductNameExistsForEdit(String tenSanPham, int selectedPosition) {
        for (int i = 0; i < mangSanPham.size(); i++) {
            if (i != selectedPosition && mangSanPham.get(i).getTenSanPham().equalsIgnoreCase(tenSanPham)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra URL có hợp lệ không
    private boolean isValidUrl(String url) {
        if (url == null) {
            return false;
        }
        return Patterns.WEB_URL.matcher(url).matches() && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private void getSanPhamFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanGetSanPham, response -> {
            mangSanPham.clear();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String tenSanPham = jsonObject.getString("tensanpham");
                    int giaSanPham = jsonObject.getInt("giasanpham");
                    String hinhAnhSanPham = jsonObject.getString("hinhanhsanpham");
                    String moTaSanPham = jsonObject.getString("motasanpham");
                    int idSanPham = jsonObject.getInt("idsanpham");
                    mangSanPham.add(new SanPham(id, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, idSanPham));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Lỗi khi phân tích dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }
            sanPhamAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonArrayRequest);
    }

    private void themSanPhamToServer(String tenSanPham, int giaSanPham, String hinhAnhSanPham, String moTaSanPham, int idLoaiSanPham) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanThemSanPham,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getSanPhamFromServer();
                    } else {
                        Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tensanpham", tenSanPham);
                params.put("giasanpham", String.valueOf(giaSanPham));
                params.put("hinhanhsanpham", hinhAnhSanPham);
                params.put("motasanpham", moTaSanPham);
                params.put("idsanpham", String.valueOf(idLoaiSanPham));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void suaSanPhamOnServer(int id, String tenSanPham, int giaSanPham, String hinhAnhSanPham, String moTaSanPham, int idLoaiSanPham) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanSuaSanPham,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getSanPhamFromServer();
                        selectedPosition = -1;
                    } else {
                        Toast.makeText(this, "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("tensanpham", tenSanPham);
                params.put("giasanpham", String.valueOf(giaSanPham));
                params.put("hinhanhsanpham", hinhAnhSanPham);
                params.put("motasanpham", moTaSanPham);
                params.put("idsanpham", String.valueOf(idLoaiSanPham));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void xoaSanPhamOnServer(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongDanXoaSanPham,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        getSanPhamFromServer();
                        selectedPosition = -1;
                    } else {
                        Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
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
        edtTenSanPham.setText("");
        edtGiaSanPham.setText("");
        edtHinhAnhSanPham.setText("");
        edtMoTaSanPham.setText("");
        spinnerLoaiSanPham.setSelection(0);
    }
}