package com.example.smartshop.model;

public class DonHang {
    private int id;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;

    public DonHang(int id, String tenKhachHang, String soDienThoai, String email) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }
}