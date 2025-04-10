package com.example.smartshop.activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartshop.R;
import com.example.smartshop.activity.Login;
import com.example.smartshop.adapter.LoaiSpAdapter;
import com.example.smartshop.adapter.SanPhamAdapter;
import com.example.smartshop.model.LoaiSp;
import com.example.smartshop.model.SanPham;
import com.example.smartshop.ultil.CheckConnection;
import com.example.smartshop.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainAdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    ArrayList<LoaiSp> mangLoaiSp;
    LoaiSpAdapter loaiSpAdapter;
    ArrayList<SanPham> mangSanPham;
    SanPhamAdapter sanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            GetDuLieuLoaiSP();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void CatchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Trang Chính
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Toast.makeText(MainAdminActivity.this, "Chuyển đến trang chính", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainAdminActivity.this, MainAdminActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1: // qlusser
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Toast.makeText(MainAdminActivity.this, "Chuyển đến trang người dùng", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainAdminActivity.this, QuanLyTaiKhoanActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2: // Quản Lý Sản Phẩm
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Toast.makeText(MainAdminActivity.this, "Chuyển đến Quản Lý Sản Phẩm", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainAdminActivity.this, QuanLySanPhamActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3: // Quản Lý Đơn Hàng
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Toast.makeText(MainAdminActivity.this, "Chuyển đến Quản Lý Đơn Hàng", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainAdminActivity.this, QuanLyDonHangActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4: // Đăng Xuất
                        Toast.makeText(MainAdminActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent = new Intent(MainAdminActivity.this, Login.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        finish();
                        break;
                }
            }
        });
    }

    private void AnhXa() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbarManHinhChinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigationView);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);

        mangLoaiSp = new ArrayList<>();
        mangLoaiSp.add(0, new LoaiSp(0, "Trang Chính", "https://icons.iconarchive.com/icons/fps.hu/free-christmas-flat-circle/512/home-icon.png"));
        loaiSpAdapter = new LoaiSpAdapter(mangLoaiSp, getApplicationContext());
        listViewManHinhChinh.setAdapter(loaiSpAdapter);

        mangSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangSanPham);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewManHinhChinh.setAdapter(sanPhamAdapter);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void actionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.tgdd.vn/Files/2020/01/20/1232366/0_800x450.jpg");
        mangQuangCao.add("https://cdn.nguyenkimmall.com/images/companies/_1/Content/video-PDP/iphone-16-pro-iphone-16-pro-max-sa-mac.jpg");
        mangQuangCao.add("https://png.pngtree.com/background/20230519/original/pngtree-dozens-of-electronic-devices-on-a-table-picture-image_2661915.jpg");
        mangQuangCao.add("https://cellphones.com.vn/sforum/wp-content/uploads/2019/05/Honor-20-Pro-lo-anh-quang-cao-1.jpg");

        for(int i = 0; i < mangQuangCao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in_slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_out_slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in_slide);
        viewFlipper.setOutAnimation(animation_out_slide);
    }

    private void GetDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    mangLoaiSp.add(1, new LoaiSp(0, "Quản Lý Người Dùng", "https://icons.iconarchive.com/icons/colebemis/feather/256/user-icon.png"));
                    mangLoaiSp.add(2, new LoaiSp(0, "Quản Lý Sản Phẩm", "https://icons.iconarchive.com/icons/ionic/ionicons/256/build-icon.png"));
                    mangLoaiSp.add(3, new LoaiSp(0, "Quản Lý Đơn Hàng", "https://icons.iconarchive.com/icons/aniket-suvarna/box/256/bxs-cart-icon.png"));
                    mangLoaiSp.add(4, new LoaiSp(0, "Đăng Xuất", "https://icons.iconarchive.com/icons/pictogrammers/material/256/logout-icon.png"));
                    loaiSpAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int id = 0;
                    String tenSanPham = "";
                    Integer giaSanPham = 0;
                    String hinhAnhSanPham = "";
                    String moTaSanPham = "";
                    int idSanPham = 0;
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenSanPham = jsonObject.getString("tensanpham");
                            giaSanPham = jsonObject.getInt("giasanpham");
                            hinhAnhSanPham = jsonObject.getString("hinhanhsanpham");
                            moTaSanPham = jsonObject.getString("motasanpham");
                            idSanPham = jsonObject.getInt("idsanpham");
                            mangSanPham.add(new SanPham(id, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, idSanPham));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}