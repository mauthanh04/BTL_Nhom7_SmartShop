package com.example.smartshop.activity.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.smartshop.R;
import com.example.smartshop.databinding.ActivityThongTinBinding;

public class ThongTinActivity extends FragmentActivity {

    private ActivityThongTinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xử lý nút "Mở bản đồ"
        binding.openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tọa độ của SmartShop
                double latitude = 21.007380;
                double longitude = 105.824500;
                String label = "SmartShop";

                // Tạo URI để mở ứng dụng bản đồ
                String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + label + ")";
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps"); // Mở Google Maps nếu có
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Nếu không có Google Maps, mở trình duyệt
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://maps.google.com/?q=" + latitude + "," + longitude));
                    startActivity(browserIntent);
                }
            }
        });
    }
}