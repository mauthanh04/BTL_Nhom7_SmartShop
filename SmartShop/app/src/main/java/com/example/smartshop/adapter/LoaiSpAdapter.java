package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.LoaiSp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSpAdapter extends BaseAdapter {
    private final ArrayList<LoaiSp> arrayListLoaiSp;
    private final Context context;

    public LoaiSpAdapter(ArrayList<LoaiSp> arrayListLoaiSp, Context context) {
        this.arrayListLoaiSp = arrayListLoaiSp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtTenLoaiSp;
        ImageView imgLoaiSp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.txtTenLoaiSp = view.findViewById(R.id.textViewLoaiSp);
            viewHolder.imgLoaiSp = view.findViewById(R.id.imageViewLoaiSp);

            view.setTag(viewHolder); // Gán ViewHolder cho view
        } else {
            viewHolder = (ViewHolder) view.getTag(); // Lấy lại ViewHolder từ tag
        }

        // Đảm bảo object không null trước khi truy cập thuộc tính
        LoaiSp loaiSp = (LoaiSp) getItem(i);
        if (loaiSp != null) {
            viewHolder.txtTenLoaiSp.setText(loaiSp.getTenLoaiSp());
            viewHolder.txtTenLoaiSp.setTextColor(context.getResources().getColor(R.color.black)); //chỉnh màu

            // Kiểm tra URL hình ảnh trước khi tải
            if (loaiSp.getHinhAnhLoaiSp() != null && !loaiSp.getHinhAnhLoaiSp().isEmpty()) {
                Picasso.get().load(loaiSp.getHinhAnhLoaiSp())
                        .placeholder(R.drawable.errorimages)
                        .error(R.drawable.error)
                        .into(viewHolder.imgLoaiSp);
            } else {
                viewHolder.imgLoaiSp.setImageResource(R.drawable.error);
            }
        }

        return view;
    }
}
