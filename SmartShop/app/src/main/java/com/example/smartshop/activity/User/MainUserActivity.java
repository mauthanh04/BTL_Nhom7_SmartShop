package com.example.smartshop.activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
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
import com.example.smartshop.model.GioHang;
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

public class MainUserActivity extends AppCompatActivity {
    //Khai báo biến
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    ArrayList<LoaiSp> mangLoaiSp;
    LoaiSpAdapter loaiSpAdapter;
    int id = 0;
    String tenLoaiSP = "";
    String hinhAnhLoaiSP = "";
    ArrayList<SanPham> mangSanPham;
    SanPhamAdapter sanPhamAdapter;

    //gio hang
    public static ArrayList<GioHang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        //khoi tạo các thành phần giao diện
        AnhXa();
        //
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
            GetDuLieuLoaiSP();
            GetDuLieuSPMoiNhat();
            //CHANGE
            CatchOnItemListView();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
        //gio hang
        if (manggiohang == null) {
            manggiohang = new ArrayList<>();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugiohang) {
            Intent intent = new Intent(getApplicationContext(), Giohang.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //CHANGE : Chuyển trang khi ấn vào các loại sản phẩm ở listview
    private void CatchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainUserActivity.this, MainUserActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainUserActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainUserActivity.this, MayTinhActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainUserActivity.this, LienHeActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainUserActivity.this, ThongTinActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaiSp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5: //Xử lý đăng xuất
                        Toast.makeText(MainUserActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent = new Intent(MainUserActivity.this, Login.class);
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
        //
        mangLoaiSp = new ArrayList<>();
        mangLoaiSp.add(0, new LoaiSp(0, "Trang Chính", "https://icons.iconarchive.com/icons/fps.hu/free-christmas-flat-circle/512/home-icon.png"));
        loaiSpAdapter = new LoaiSpAdapter(mangLoaiSp, getApplicationContext());
        listViewManHinhChinh.setAdapter(loaiSpAdapter);
        //
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
        for(int i = 0; i< mangQuangCao.size();i++){
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
                    for (int i = 0; i < response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSP = jsonObject.getString("tenloaisanpham");
                            hinhAnhLoaiSP = jsonObject.getString("hinhanhloaisanpham");
                            mangLoaiSp.add(new LoaiSp(id, tenLoaiSP, hinhAnhLoaiSP));
                            loaiSpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangLoaiSp.add(3, new LoaiSp(0, "Liên Hệ", "https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"));
                    mangLoaiSp.add(4, new LoaiSp(0, "Thông Tin", "https://icons.iconarchive.com/icons/kyo-tux/delikate/256/Info-icon.png"));
                    mangLoaiSp.add(5, new LoaiSp(0, "Đăng xuất", "https://icons.iconarchive.com/icons/pictogrammers/material/256/logout-icon.png"));
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
                            mangSanPham.add(new SanPham(id, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, idSanPham ));
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