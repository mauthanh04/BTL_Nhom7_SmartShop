package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.model.User;

import java.util.ArrayList;

public class QuanLyTaiKhoanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> mangUser;

    public QuanLyTaiKhoanAdapter(Context context, ArrayList<User> mangUser) {
        this.context = context;
        this.mangUser = mangUser;
    }

    @Override
    public int getCount() {
        return mangUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mangUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtEmail, txtHoTen, txtVaiTro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_quanly_taikhoan, null);
            viewHolder.txtEmail = convertView.findViewById(R.id.txtEmail);
            viewHolder.txtHoTen = convertView.findViewById(R.id.txtHoTen);
            viewHolder.txtVaiTro = convertView.findViewById(R.id.txtVaiTro);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = mangUser.get(position);
        viewHolder.txtEmail.setText(user.getEmail());
        viewHolder.txtHoTen.setText("Họ tên: " + user.getHoTen());
        viewHolder.txtVaiTro.setText("Vai trò: " + user.getVaiTro());

        return convertView;
    }
}