package com.example.smartshop.model;

public class LoaiSp {
    public int Id;
    public String TenLoaiSp;
    public String HinhAnhLoaiSp;

    public LoaiSp(int id, String tenLoaiSp, String hinhAnhLoaiSp) {
        Id = id;
        TenLoaiSp = tenLoaiSp;
        HinhAnhLoaiSp = hinhAnhLoaiSp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaiSp() {
        return TenLoaiSp;
    }

    public void setTenLoaiSp(String tenLoaiSp) {
        TenLoaiSp = tenLoaiSp;
    }

    public String getHinhAnhLoaiSp() {
        return HinhAnhLoaiSp;
    }

    public void setHinhAnhLoaiSp(String hinhAnhLoaiSp) {
        HinhAnhLoaiSp = hinhAnhLoaiSp;
    }
}
