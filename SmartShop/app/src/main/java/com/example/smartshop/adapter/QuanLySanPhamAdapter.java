package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuanLySanPhamAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> mangSanPham;

    public QuanLySanPhamAdapter(Context context, ArrayList<SanPham> mangSanPham) {
        this.context = context;
        this.mangSanPham = mangSanPham;
    }

    @Override
    public int getCount() {
        return mangSanPham.size(); // Trả về toàn bộ số lượng sản phẩm
    }

    @Override
    public Object getItem(int position) {
        return mangSanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtTenSanPham, txtGiaSanPham, txtMoTaSanPham, txtLoaiSanPham;
        ImageView imgHinhAnhSanPham;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_quanly_sanpham, null);
            viewHolder.txtTenSanPham = convertView.findViewById(R.id.txtTenSanPham);
            viewHolder.txtGiaSanPham = convertView.findViewById(R.id.txtGiaSanPham);
            viewHolder.txtMoTaSanPham = convertView.findViewById(R.id.txtMoTaSanPham);
            viewHolder.txtLoaiSanPham = convertView.findViewById(R.id.txtLoaiSanPham);
            viewHolder.imgHinhAnhSanPham = convertView.findViewById(R.id.imgHinhAnhSanPham);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = mangSanPham.get(position);
        viewHolder.txtTenSanPham.setText(sanPham.getTenSanPham());
        viewHolder.txtGiaSanPham.setText("Giá: " + String.format("%,d", sanPham.getGiaSanPham()) + " VNĐ");
        viewHolder.txtMoTaSanPham.setText("Mô tả: " + sanPham.getMoTaSanPham());
        viewHolder.txtLoaiSanPham.setText("Loại: " + (sanPham.getIdSanPham() == 1 ? "Điện Thoại" : "Máy Tính"));
        Picasso.get().load(sanPham.getHinhAnhSanPham()).into(viewHolder.imgHinhAnhSanPham);

        return convertView;
    }
}