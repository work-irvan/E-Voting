package com.muhammad_irvan.eev.Data;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Irvan on 31/07/2017.
 */

public class DataPemilihanTerpilih extends Application {
    private static DataPemilihanTerpilih singleton;

    private static String penyelenggara_idPemilihan;
    private static Bitmap penyelenggara_image;
    private static String penyelenggara_namaPemilihan;
    private static String penyelenggara_simpanStatus;
    private static String penyelenggara_tanggalMaxGabung;
    private static String penyelenggara_tanggalMaxVote;
    private static String penyelenggara_tanggalPerhitungan;
    private static String penyelenggara_keterangan;
    private static String penyelenggara_peserta;
    private static String penyelenggara_selesai;


    public static DataPemilihanTerpilih getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }


    public static String getPenyelenggara_selesai() {
        return penyelenggara_selesai;
    }

    public static void setPenyelenggara_selesai(String penyelenggara_selesai) {
        DataPemilihanTerpilih.penyelenggara_selesai = penyelenggara_selesai;
    }

    public static String getPenyelenggara_idPemilihan() {
        return penyelenggara_idPemilihan;
    }

    public static void setPenyelenggara_idPemilihan(String penyelenggara_idPemilihan) {
        DataPemilihanTerpilih.penyelenggara_idPemilihan = penyelenggara_idPemilihan;
    }

    public static Bitmap getPenyelenggara_image() {
        return penyelenggara_image;
    }

    public static void setPenyelenggara_image(Bitmap penyelenggara_image) {
        DataPemilihanTerpilih.penyelenggara_image = penyelenggara_image;
    }

    public static String getPenyelenggara_namaPemilihan() {
        return penyelenggara_namaPemilihan;
    }

    public static void setPenyelenggara_namaPemilihan(String penyelenggara_namaPemilihan) {
        DataPemilihanTerpilih.penyelenggara_namaPemilihan = penyelenggara_namaPemilihan;
    }

    public static String getPenyelenggara_simpanStatus() {
        return penyelenggara_simpanStatus;
    }

    public static void setPenyelenggara_simpanStatus(String penyelenggara_simpanStatus) {
        DataPemilihanTerpilih.penyelenggara_simpanStatus = penyelenggara_simpanStatus;
    }

    public static String getPenyelenggara_tanggalMaxGabung() {
        return penyelenggara_tanggalMaxGabung;
    }

    public static void setPenyelenggara_tanggalMaxGabung(String penyelenggara_tanggalMaxGabung) {
        DataPemilihanTerpilih.penyelenggara_tanggalMaxGabung = penyelenggara_tanggalMaxGabung;
    }

    public static String getPenyelenggara_tanggalMaxVote() {
        return penyelenggara_tanggalMaxVote;
    }

    public static void setPenyelenggara_tanggalMaxVote(String penyelenggara_tanggalMaxVote) {
        DataPemilihanTerpilih.penyelenggara_tanggalMaxVote = penyelenggara_tanggalMaxVote;
    }

    public static String getPenyelenggara_tanggalPerhitungan() {
        return penyelenggara_tanggalPerhitungan;
    }

    public static void setPenyelenggara_tanggalPerhitungan(String penyelenggara_tanggalPerhitungan) {
        DataPemilihanTerpilih.penyelenggara_tanggalPerhitungan = penyelenggara_tanggalPerhitungan;
    }

    public static String getPenyelenggara_keterangan() {
        return penyelenggara_keterangan;
    }

    public static void setPenyelenggara_keterangan(String penyelenggara_keterangan) {
        DataPemilihanTerpilih.penyelenggara_keterangan = penyelenggara_keterangan;
    }

    public static String getPenyelenggara_peserta() {
        return penyelenggara_peserta;
    }

    public static void setPenyelenggara_peserta(String penyelenggara_peserta) {
        DataPemilihanTerpilih.penyelenggara_peserta = penyelenggara_peserta;
    }
}
