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

public class DienthoaiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> arrayDienThoai;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); // Khai báo 1 lần

    public DienthoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayDienThoai.get(position).getId(); // Trả về ID sản phẩm thay vì position
    }

    static class ViewHolder {
        TextView txtTenDienThoai, txtGiaDienThoai, txtMoTaDienThoai;
        ImageView imgDienThoai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dong_dienthoai, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtTenDienThoai = convertView.findViewById(R.id.textviewdienthoai);
            viewHolder.txtGiaDienThoai = convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtMoTaDienThoai = convertView.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgDienThoai = convertView.findViewById(R.id.imageviewdienthoai);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = arrayDienThoai.get(position);

        viewHolder.txtTenDienThoai.setText(sanPham.getTenSanPham());
        viewHolder.txtGiaDienThoai.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        viewHolder.txtMoTaDienThoai.setMaxLines(2);
        viewHolder.txtMoTaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDienThoai.setText(sanPham.getMoTaSanPham());

        // Kiểm tra hình ảnh
        String imageUrl = sanPham.getHinhAnhSanPham();
        if (TextUtils.isEmpty(imageUrl)) {
            viewHolder.imgDienThoai.setImageResource(R.drawable.noimage);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(viewHolder.imgDienThoai);
        }

        return convertView;
    }
}
