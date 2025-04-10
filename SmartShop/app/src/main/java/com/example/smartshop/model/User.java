package com.example.smartshop.model;

public class User {
    private int id;
    private String email;
    private String matKhau;
    private String hoTen;
    private String vaiTro;

    public User(int id, String email, String matKhau, String hoTen, String vaiTro) {
        this.id = id;
        this.email = email;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getVaiTro() {
        return vaiTro;
    }
}