package com.example.smartshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartshop.R;
import com.example.smartshop.activity.User.ChiTietSanPham;
import com.example.smartshop.model.SanPham;
import com.example.smartshop.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySanPham;
    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    public  class  ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhAnhSP;
        public TextView txtTenSP, txtGiaSP;

        public ItemHolder(View itemView) {
            super(itemView);
            imgHinhAnhSP = (ImageView) itemView.findViewById(R.id.imageviewSanPham);
            txtGiaSP = (TextView) itemView.findViewById(R.id.textviewGiaSP);
            txtTenSP = (TextView) itemView.findViewById(R.id.textviewTenSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", arraySanPham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, arraySanPham.get(getPosition()).getTenSanPham());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_recyclerview_spmoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = arraySanPham.get(position);
        holder.txtTenSP.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        Picasso.get().load(sanPham.getHinhAnhSanPham())
                .placeholder(R.drawable.errorimages)
                .error(R.drawable.error)
                .into(holder.imgHinhAnhSP);

    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

}
