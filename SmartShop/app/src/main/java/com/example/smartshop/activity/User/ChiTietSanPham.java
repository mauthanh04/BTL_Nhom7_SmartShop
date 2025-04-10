package com.example.smartshop.activity.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartshop.R;
import com.example.smartshop.model.GioHang;
import com.example.smartshop.model.SanPham;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;

    //biến toàn cục
    int id = 0;
    String TenChitiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        Anhxa();
        ActionToolbar();
        GetInfomation();
        CatchEventSpinner();
        EventButton();
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
    private void EventButton(){
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainUserActivity.manggiohang != null && MainUserActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i=0;i<MainUserActivity.manggiohang.size();i++){
                        if(MainUserActivity.manggiohang.get(i).getIdsp() == id){
                            MainUserActivity.manggiohang.get(i).setSoluongsp(MainUserActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if(MainUserActivity.manggiohang.get(i).getSoluongsp() >= 10){
                                MainUserActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainUserActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainUserActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChitiet;
                        MainUserActivity.manggiohang.add(new GioHang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaChitiet;
                    MainUserActivity.manggiohang.add(new GioHang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));

                }
                Intent intent = new Intent(getApplicationContext(), Giohang.class);
                startActivity(intent);
            }
        });
    }
    private void CatchEventSpinner(){
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }
    private void GetInfomation(){
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");

        id = sanPham.getId();
        TenChitiet = sanPham.getTenSanPham();
        GiaChitiet = sanPham.getGiaSanPham();
        HinhanhChitiet = sanPham.getHinhAnhSanPham();
        MotaChitiet = sanPham.getMoTaSanPham();
        Idsanpham = sanPham.getIdSanPham();

        txtten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : " + decimalFormat.format(GiaChitiet) + " Đ");
        txtmota.setText(MotaChitiet);
        Picasso.get()
                .load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);
    }
    private void Anhxa(){
        toolbarChitiet = (Toolbar) findViewById(R.id.toolbarchitietsanphham);
        imgChitiet = (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndatmua = (Button) findViewById(R.id.buttondatmua);
    }
    private void ActionToolbar(){
        if (toolbarChitiet != null) {
            setSupportActionBar(toolbarChitiet);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Drawable navIcon = toolbarChitiet.getNavigationIcon();
                if (navIcon != null) {
                    navIcon = DrawableCompat.wrap(navIcon);
                    DrawableCompat.setTint(navIcon, Color.WHITE);
                    toolbarChitiet.setNavigationIcon(navIcon);
                }
            }
            toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Log.e("ToolbarError", "Toolbar is null");
        }
    }

}