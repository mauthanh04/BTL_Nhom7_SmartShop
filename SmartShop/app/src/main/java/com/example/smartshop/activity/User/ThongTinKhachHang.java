package com.example.smartshop.activity.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
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
import com.example.smartshop.ultil.CheckConnection;
import com.example.smartshop.ultil.Server;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity {
    EditText edttenkhachhang, edtsdt, edtemail;
    Button btnxacnhan, btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        //ánh xạ
        Anhxa();
        //tro ve
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //xac nhan
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            Evenbutton();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void Evenbutton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edttenkhachhang.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if(ten.length() > 0 && sdt.length() > 0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("madonhang",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        };
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa(){
        edttenkhachhang = (EditText) findViewById(R.id.edittexttenkhachang);
        edtsdt = (EditText) findViewById(R.id.edittextsodienthoai);
        edtemail = (EditText) findViewById(R.id.edittextemail);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btntrove = (Button) findViewById(R.id.buttontrove);
    }


}