package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.ChiTietDonHang;

import java.util.ArrayList;

public class QuanLyChiTietDonHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ChiTietDonHang> mangChiTietDonHang;

    public QuanLyChiTietDonHangAdapter(Context context, ArrayList<ChiTietDonHang> mangChiTietDonHang) {
        this.context = context;
        this.mangChiTietDonHang = mangChiTietDonHang;
    }

    @Override
    public int getCount() {
        return mangChiTietDonHang.size();
    }

    @Override
    public Object getItem(int position) {
        return mangChiTietDonHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_don_hang, parent, false);
            holder = new ViewHolder();
            holder.tvTenSanPham = convertView.findViewById(R.id.tvTenSanPham);
            holder.tvMaSanPham = convertView.findViewById(R.id.tvMaSanPham);
            holder.tvGiaSanPham = convertView.findViewById(R.id.tvGiaSanPham);
            holder.tvSoLuong = convertView.findViewById(R.id.tvSoLuong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChiTietDonHang chiTiet = mangChiTietDonHang.get(position);
        holder.tvTenSanPham.setText(chiTiet.getTenSanPham());
        holder.tvMaSanPham.setText("Mã sản phẩm: " + chiTiet.getMaSanPham());
        holder.tvGiaSanPham.setText("Giá: " + chiTiet.getGiaSanPham() + " VND");
        holder.tvSoLuong.setText("Số lượng: " + chiTiet.getSoLuongSanPham());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenSanPham, tvMaSanPham, tvGiaSanPham, tvSoLuong;
    }
}