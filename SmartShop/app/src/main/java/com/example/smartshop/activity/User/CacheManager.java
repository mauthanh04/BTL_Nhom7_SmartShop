package com.example.smartshop.activity.User;

import android.util.Log;

import com.example.smartshop.model.SanPham;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private static HashMap<Integer, SanPham> cache = new HashMap<>();

    public static void putSanPham(SanPham sanPham) {
        cache.put(sanPham.getId(), sanPham);
    }

    public static SanPham getSanPham(int id) {
        return cache.get(id);
    }

    public static boolean contains(int id) {
        return cache.containsKey(id);
    }

//    public static void clear() {
//        cache.clear();
//    }

    public static void printCache() {
        Log.d("CACHE_MANAGER", "======= DANH SÁCH SẢN PHẨM TRONG CACHE =======");
        for (Map.Entry<Integer, SanPham> entry : cache.entrySet()) {
            SanPham sp = entry.getValue();
            Log.d("CACHE_MANAGER", "ID: " + sp.getId() + " | Tên: " + sp.getTenSanPham());
        }
    }

}
