package com.example.smartshop.model;

public class ChiTietDonHang {
    private int id;
    private int maDonHang;
    private String maSanPham;
    private String tenSanPham;
    private int giaSanPham;
    private int soLuongSanPham;

    public ChiTietDonHang(int id, int maDonHang, String maSanPham, String tenSanPham, int giaSanPham, int soLuongSanPham) {
        this.id = id;
        this.maDonHang = maDonHang;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.soLuongSanPham = soLuongSanPham;
    }

    public int getId() {
        return id;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }
}