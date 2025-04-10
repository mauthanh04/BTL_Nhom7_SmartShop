package com.example.smartshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartshop.R;
import com.example.smartshop.activity.User.Giohang;
import com.example.smartshop.activity.User.MainUserActivity;
import com.example.smartshop.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<GioHang> getArraygiohang() {
        return arraygiohang;
    }

    public void setArraygiohang(ArrayList<GioHang> arraygiohang) {
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnplus, btnvalues;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = (TextView) convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = (ImageView) convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button) convertView.findViewById(R.id.buttonvalues);
            viewHolder.btnplus= (Button) convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang giohang = (GioHang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + " Đ");
        Picasso.get()
                .load(giohang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp()+"");

        //minus - plus
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if(sl >= 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        //plus
        final ViewHolder finalHolder = viewHolder;

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalHolder.btnvalues.getText().toString()) + 1;
                int slhientai = MainUserActivity.manggiohang.get(position).getSoluongsp();
                long giahientai = MainUserActivity.manggiohang.get(position).getGiasp();
                MainUserActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainUserActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                Giohang.EventUltil();

                if(slmoinhat > 9){
                    finalHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalHolder.btnminus.setVisibility(View.VISIBLE);
                    finalHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalHolder.btnplus.setVisibility(View.VISIBLE);
                    finalHolder.btnminus.setVisibility(View.VISIBLE);
                    finalHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        // minus
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalHolder.btnvalues.getText().toString()) - 1;
                int slhientai = MainUserActivity.manggiohang.get(position).getSoluongsp();
                long giahientai = MainUserActivity.manggiohang.get(position).getGiasp();
                MainUserActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainUserActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                Giohang.EventUltil();

                if(slmoinhat < 2){
                    finalHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalHolder.btnplus.setVisibility(View.VISIBLE);
                    finalHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalHolder.btnminus.setVisibility(View.VISIBLE);
                    finalHolder.btnplus.setVisibility(View.VISIBLE);
                    finalHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
