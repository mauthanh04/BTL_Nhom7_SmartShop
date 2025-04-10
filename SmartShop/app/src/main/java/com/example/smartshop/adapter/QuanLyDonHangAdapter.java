package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.DonHang;

import java.util.ArrayList;

public class QuanLyDonHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DonHang> mangDonHang;

    public QuanLyDonHangAdapter(Context context, ArrayList<DonHang> mangDonHang) {
        this.context = context;
        this.mangDonHang = mangDonHang;
    }

    @Override
    public int getCount() {
        return mangDonHang.size();
    }

    @Override
    public Object getItem(int position) {
        return mangDonHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_don_hang, parent, false);
            holder = new ViewHolder();
            holder.tvTenKhachHang = convertView.findViewById(R.id.tvTenKhachHang);
            holder.tvEmail = convertView.findViewById(R.id.tvEmail);
            holder.tvSoDienThoai = convertView.findViewById(R.id.tvSoDienThoai);
            holder.tvLoai = convertView.findViewById(R.id.tvLoai);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DonHang donHang = mangDonHang.get(position);
        holder.tvTenKhachHang.setText(donHang.getTenKhachHang());
        holder.tvEmail.setText("Email: " + donHang.getEmail());
        holder.tvSoDienThoai.setText("Số điện thoại: " + donHang.getSoDienThoai());
        holder.tvLoai.setText("Loại: Đơn hàng");

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenKhachHang, tvEmail, tvSoDienThoai, tvLoai;
    }
}