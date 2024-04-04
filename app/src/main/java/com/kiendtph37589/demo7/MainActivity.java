package com.kiendtph37589.demo7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText edt_name,edt_price,edt_des;
    Button btn_select;
    TextView tv_kq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edt_name=findViewById(R.id.edt_name);
        edt_price=findViewById(R.id.edt_price);
        edt_des = findViewById(R.id.edt_Des);
        btn_select = findViewById(R.id.btn_select);
        tv_kq = findViewById(R.id.tv_kq);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRetrofit();
            }
        });
    }
    String strKq="";
    List<ProdModel> ls;
    private void selectRetrofit() {
        //b1.Tao doi tuong
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104/Aluter/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2.goi ham select trong interface
        //b2.1 tao doi tuong
        InterSelectProd interSelectProd = retrofit.create(InterSelectProd.class);

        //b2.2 chuan bi ham
        Call<SrvResponseProd> call = interSelectProd.getProd();
        //b2.3 thuc thi ham
        call.enqueue(new Callback<SrvResponseProd>() {
            //thanh cong
            @Override
            public void onResponse(Call<SrvResponseProd> call, Response<SrvResponseProd> response) {
                SrvResponseProd srvResponseProd = response.body();// lay kq tra ve tu sever
                //convert sang dang list
                ls = new ArrayList<>(Arrays.asList(srvResponseProd.getProducts()));
                for (ProdModel p : ls){// doc du lieu tu list vao chuoi
                    strKq += "Id: "+p.getPid()+ "; Name: "+ p.getName()+ "; Price: "+p.getPrice()+"\n";
                }
                // dua kq len man hinh chinh
                tv_kq.setText(strKq);
            }
            // that bai
            @Override
            public void onFailure(Call<SrvResponseProd> call, Throwable t) {
                tv_kq.setText(t.getMessage());
            }
        });
    }
}