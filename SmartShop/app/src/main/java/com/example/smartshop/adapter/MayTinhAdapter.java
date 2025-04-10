package com.example.smartshop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MayTinhAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arrayMayTinh;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); // Khai báo 1 lần

    public MayTinhAdapter(Context context, ArrayList<SanPham> arrayMayTinh) {
        this.context = context;
        this.arrayMayTinh = arrayMayTinh;
    }

    @Override
    public int getCount() {
        return arrayMayTinh.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayMayTinh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayMayTinh.get(position).getId(); // Trả về ID sản phẩm thay vì position
    }

    static class ViewHolder {
        TextView txtTenMayTinh, txtGiaMayTinh, txtMoTaMayTinh;
        ImageView imgMayTinh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MayTinhAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dong_maytinh, parent, false);

            viewHolder = new MayTinhAdapter.ViewHolder();
            viewHolder.txtTenMayTinh = convertView.findViewById(R.id.textviewmaytinh);
            viewHolder.txtGiaMayTinh = convertView.findViewById(R.id.textviewgiamaytinh);
            viewHolder.txtMoTaMayTinh = convertView.findViewById(R.id.textviewmotamaytinh);
            viewHolder.imgMayTinh = convertView.findViewById(R.id.imageviewmaytinh);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MayTinhAdapter.ViewHolder) convertView.getTag();
        }

        SanPham sanPham = arrayMayTinh.get(position);

        viewHolder.txtTenMayTinh.setText(sanPham.getTenSanPham());
        viewHolder.txtGiaMayTinh.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        viewHolder.txtMoTaMayTinh.setMaxLines(2);
        viewHolder.txtMoTaMayTinh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaMayTinh.setText(sanPham.getMoTaSanPham());

        // Kiểm tra hình ảnh
        String imageUrl = sanPham.getHinhAnhSanPham();
        if (TextUtils.isEmpty(imageUrl)) {
            viewHolder.imgMayTinh.setImageResource(R.drawable.noimage);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(viewHolder.imgMayTinh);
        }

        return convertView;
    }
}
