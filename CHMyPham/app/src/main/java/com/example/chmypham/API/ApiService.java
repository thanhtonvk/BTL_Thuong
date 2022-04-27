package com.example.chmypham.API;

import com.example.chmypham.Models.CTHoaDon;
import com.example.chmypham.Models.Hang;
import com.example.chmypham.Models.HoaDon;
import com.example.chmypham.Models.SanPham;
import com.example.chmypham.Models.TaiKhoan;
import com.example.chmypham.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();
    ApiService api = new Retrofit.Builder()
            .baseUrl(Common.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(ApiService.class);

    //tài khoản
    @GET("api/TaiKhoans/GetTaiKhoan")
    Call<TaiKhoan> getTaiKhoan(String tenTK);

    @GET("api/TaiKhoans/DangNhap")
    Call<Boolean> dangNhap(@Query("TenTK") String tenTK, @Query("MatKhau") String matKhau);

    @POST("api/TaiKhoans/PostTaiKhoan")
    Call<Integer> dangKy(@Body TaiKhoan taiKhoan);

    @PUT("api/TaiKhoans/PutTaiKhoan")
    Call<Integer> capNhatTK(@Body TaiKhoan taiKhoan);

    //sản phẩm
    @GET("api/SanPhams/GetSanPhams")
    Call<List<SanPham>> getSanPhams();



    @GET("api/SanPhams/GetSanPhamsHang")
    Call<List<SanPham>> getSanPhamTheoHang(@Query("MaHang") int maHang);

    //hãng
    @GET("api/Hangs")
    Call<List<Hang>> getHangs();

    //hóa đơn
    @GET("api/HoaDons/GetHoaDonsTaiKhoan")
    Call<List<HoaDon>> getHoaDons(@Query("TenTK") String tenTK);

    @POST("api/HoaDons/PostHoaDon")
    Call<Integer> addHoaDon(@Body HoaDon hoaDon);

    //chi tiết hóa đơn
    @GET("api/CTHoaDons/GetCTHoaDons")
    Call<List<CTHoaDon>> getCTHoaDons(@Query("idHD") int idHD);

    @POST("api/CTHoaDons/PostCTHoaDon")
    Call<Integer> addCTHoaDons(@Body CTHoaDon ctHoaDon);

    @PUT("api/CTHoaDons/PutCTHoaDon")
    Call<Integer> updateCTHoaDons(@Body CTHoaDon ctHoaDon);

    @POST("api/CTHoaDons/DeleteCTHoaDon")
    Call<Integer> deleteHoaDons(@Query("id") int id);
}
