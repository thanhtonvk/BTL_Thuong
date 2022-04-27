package com.example.chmypham.Activity;

import static com.example.chmypham.Utils.Common.gioHangList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.chmypham.Models.GioHang;
import com.example.chmypham.R;
import com.example.chmypham.Utils.Common;

import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    TextView tvTen, tvGiaBan, tvSoLuong, tvHang, tvMota;
    Button btnThemGioHang;
    ImageView imgHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        loadDuLieu();
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GioHang gioHang = new GioHang(Common.sanPham.getMaSP(), Common.sanPham.getTenSP(), Common.sanPham.getHinhAnh(), Common.sanPham.getGiaBan(), 1);
                themGioHang(gioHang);
            }
        });
    }

    //load dữ liệu
    private void loadDuLieu() {
        if (Common.sanPham != null) {
            tvTen.setText(Common.sanPham.getTenSP());
            tvGiaBan.setText(Common.formatMoney(Common.sanPham.getGiaBan()));
            tvSoLuong.setText("Số lượng: " + Common.sanPham.getSoLuong());

            tvHang.setText("Hãng sản xuất: " + Common.sanPham.getTenHang());
            tvMota.setText(Common.sanPham.getMoTa());
            Glide.with(ChiTietSanPhamActivity.this).load(Common.sanPham.getHinhAnh()).into(imgHinhAnh);
        }
    }

    //ánh xạ
    private void anhXa() {
        tvTen = findViewById(R.id.tv_ten);
        tvGiaBan = findViewById(R.id.tv_gia);
        tvSoLuong = findViewById(R.id.tv_soluong);

        tvHang = findViewById(R.id.tv_hang);
        tvMota = findViewById(R.id.tv_mota);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);
        imgHinhAnh = findViewById(R.id.img_hinhanh);
    }

    //giỏ hàng
    private void themGioHang(GioHang gioHang) {
        //nếu giỏ hàng rỗng thì tạo mới, thêm sản phẩm vào giỏ hàng
        if (gioHangList == null) {
            gioHangList = new ArrayList<>();
            gioHangList.add(gioHang);
            dialog("Thêm thành công");
            Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
        } else {
            //kiểm tra tồn tại
            int kt = ktTonTai(gioHang.getMaSP());
            //nếu sản phẩm đã tồn tại thì tăng số lượng trogn giỏ hang lên 1
            if (kt > -1) {
                GioHang gh = gioHangList.get(kt);
                if (gh.getSoLuong() + 1 <= Common.sanPham.getSoLuong()) {
                    gh.setSoLuong(gh.getSoLuong() + 1);
                    gioHangList.set(kt, gh);
                    dialog("Thêm thành công");
                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                } else {
                    dialog("Số lượng tối đa");
                    Toast.makeText(ChiTietSanPhamActivity.this, "Số lượng sản phẩm tối đa", Toast.LENGTH_LONG).show();
                }
                //nếu không tồn tại sản phẩm trong giỏ hàng thì thêm sp vào giỏ hàng
            } else {
                gioHangList.add(gioHang);
                dialog("Thêm thành công");
                Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            }
        }
    }

    private int ktTonTai(int maSP) {
        GioHang model = null;
        for (GioHang gioHang : gioHangList
        ) {
            if (gioHang.getMaSP() == maSP) model = gioHang;
        }
        return gioHangList.indexOf(model);
    }

    private void dialog(String noidung) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietSanPhamActivity.this);
        builder.setTitle(noidung);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}