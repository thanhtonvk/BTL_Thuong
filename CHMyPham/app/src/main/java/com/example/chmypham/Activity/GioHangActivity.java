package com.example.chmypham.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chmypham.Adapters.GioHangAdapter;
import com.example.chmypham.R;
import com.example.chmypham.Utils.Common;


public class GioHangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        lv = findViewById(R.id.lv_sanpham);
        adapter = new GioHangAdapter(getApplicationContext());
        lv.setAdapter(adapter);
        findViewById(R.id.btnDatHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.gioHangList != null) {
                    startActivity(new Intent(getApplicationContext(), ThanhToanActivity.class));
                }
            }
        });
    }

    GioHangAdapter adapter;
    ListView lv;


}