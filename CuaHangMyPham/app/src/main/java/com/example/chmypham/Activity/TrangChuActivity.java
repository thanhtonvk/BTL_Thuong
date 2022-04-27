package com.example.chmypham.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chmypham.API.ApiService;
import com.example.chmypham.Adapters.HangLoaiAdapter;
import com.example.chmypham.Adapters.SanPhamAdapter;
import com.example.chmypham.Models.Hang;
import com.example.chmypham.Models.SanPham;
import com.example.chmypham.R;
import com.example.chmypham.Utils.Common;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangChuActivity extends AppCompatActivity {


    TwoWayView lv_hang;
    GridView lv_sanpham;
    HangLoaiAdapter hangAdapter;
    List<String> hangList = new ArrayList<>();
    List<Hang> hangs = new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> sanPhamList = new ArrayList<>();
    EditText edtTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        anhXa();
        loadDS();
        edtTimKiem = findViewById(R.id.edtTimKiem);
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String timKiem = edtTimKiem.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Common.sanPhamList = new ArrayList<>();
                    for (SanPham sanPham : sanPhamList
                    ) {
                        if (sanPham.getTenSP().toLowerCase().contains(timKiem.toLowerCase()) ||
                                sanPham.getTenHang().toLowerCase().contains(timKiem.toLowerCase())) {
                            Common.sanPhamList.add(sanPham);
                        }
                    }
                    startActivity(new Intent(getApplicationContext(), com.example.chmypham.Activity.DanhSachSanPhamActivity.class));
                }
                return false;
            }
        });
    }


    //ánh xạ
    private void anhXa() {
        lv_hang = findViewById(R.id.lv_hang);
        lv_sanpham = findViewById(R.id.lv_sanpham);
        hangAdapter = new HangLoaiAdapter(getApplicationContext(), hangList);
        lv_hang.setAdapter(hangAdapter);
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanPhamList);
        lv_sanpham.setAdapter(sanPhamAdapter);
    }

    //load DS
    private void loadDS() {
        lv_hang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hang hang = hangs.get(i);
                ApiService.api.getSanPhamTheoHang(hang.getMaHang()).enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        if (response.body() != null) {
                            Common.sanPhamList = response.body();
                            startActivity(new Intent(getApplicationContext(), com.example.chmypham.Activity.DanhSachSanPhamActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }
        });
        ApiService.api.getHangs().enqueue(new Callback<List<Hang>>() {
            @Override
            public void onResponse(Call<List<Hang>> call, Response<List<Hang>> response) {
                if (response.body() != null) {

                    for (Hang hang : response.body()
                    ) {
                        hangs.add(hang);
                        hangList.add(hang.getHinhAnh());
                        hangAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hang>> call, Throwable t) {

            }
        });

        ApiService.api.getSanPhams().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.body() != null) {
                    for (SanPham sanPham : response.body()
                    ) {
                        sanPhamList.add(sanPham);
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }
}