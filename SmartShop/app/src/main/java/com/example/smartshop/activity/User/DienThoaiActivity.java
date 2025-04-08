package com.example.smartshop.activity.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartshop.R;
import com.example.smartshop.adapter.DienthoaiAdapter;
import com.example.smartshop.model.SanPham;
import com.example.smartshop.ultil.CheckConnection;
import com.example.smartshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    //khai báo
    Toolbar toolbardt;
    ListView lvdt;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<SanPham> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    Boolean isLoading = false ;
    Boolean limitdata = false;
    mHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dien_thoai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolBar();
            GetData(page);

            //Loadmore data
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
            finish();
        }
    }

    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class) ;
                intent.putExtra("thongtinsanpham",mangdt.get(position)) ;
                startActivity(intent) ;
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start() ;
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Mota = "";
                int Idspdt = 0;
                if(response != null && response.length() != 2 && mangdt != null){
                    lvdt.removeFooterView(footerview);
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i < jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giadt = jsonObject.getInt("giasanpham");
                            Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            Mota = jsonObject.getString("motasanpham");
                            Idspdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new SanPham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt));
                        }
                        if (!mangdt.isEmpty()) {
                            dienthoaiAdapter.notifyDataSetChanged();
                        }
                    }catch (JSONException e){
                        Log.e("JSONError", "Lỗi phân tích JSON", e);
                    }
                }else{
                    limitdata = true;
                    lvdt.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "Lỗi khi tải dữ liệu: " + error.getMessage());
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối đến server");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        if (toolbardt != null) {
            setSupportActionBar(toolbardt);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Drawable navIcon = toolbardt.getNavigationIcon();
                if (navIcon != null) {
                    navIcon = DrawableCompat.wrap(navIcon);
                    DrawableCompat.setTint(navIcon, Color.WHITE); 
                    toolbardt.setNavigationIcon(navIcon);
                }
            }
            toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Log.e("ToolbarError", "Toolbar is null");
        }
    }


    private void GetIdloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",iddt+"");

        if (iddt == -1) {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Không tìm thấy loại sản phẩm");
            finish(); // Đóng activity nếu không có id hợp lệ
        }
    }

    private void Anhxa() {
        toolbardt = (Toolbar) findViewById(R.id.toolbaldienthoai);
        lvdt = (ListView) findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(),mangdt);
        lvdt.setAdapter(dienthoaiAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE) ;
        footerview = inflater.inflate(R.layout.progressbar,null) ;
        mHandler = new mHandler() ;
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    page++;
                    GetData(page);
                    isLoading = true;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1) ;
            mHandler.sendMessage(message);
            super.run();
        }
    }
}