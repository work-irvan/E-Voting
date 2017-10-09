package com.muhammad_irvan.evv.admin.data;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Irvan on 31/07/2017.
 */

public class DataPemilihanPenyelenggaraTerpilih extends Application {
    private static DataPemilihanPenyelenggaraTerpilih singleton;

    private static String penyelenggara_idPemilihan;
    private static Bitmap penyelenggara_image;
    private static String penyelenggara_namaPemilihan;
    private static String penyelenggara_simpanStatus;
    private static String penyelenggara_tanggalMaxGabung;
    private static String penyelenggara_tanggalMaxVote;
    private static String penyelenggara_tanggalPerhitungan;
    private static String penyelenggara_keterangan;
    private static String penyelenggara_peserta;
    private static Boolean selesai;



    private static Boolean penyelenggaraEditPeserta;
    private static Boolean penyelenggaraEditKandidat;
    private static Boolean penyelenggaraEditPerhitungan;



    public static DataPemilihanPenyelenggaraTerpilih getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }


    public static Boolean getSelesai() {
        return selesai;
    }

    public static void setSelesai(Boolean selesai) {
        DataPemilihanPenyelenggaraTerpilih.selesai = selesai;
    }

    public static String getPenyelenggara_idPemilihan() {
        return penyelenggara_idPemilihan;
    }

    public static void setPenyelenggara_idPemilihan(String penyelenggara_idPemilihan) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_idPemilihan = penyelenggara_idPemilihan;
    }

    public static Bitmap getPenyelenggara_image() {
        return penyelenggara_image;
    }

    public static void setPenyelenggara_image(Bitmap penyelenggara_image) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_image = penyelenggara_image;
    }

    public static String getPenyelenggara_namaPemilihan() {
        return penyelenggara_namaPemilihan;
    }

    public static void setPenyelenggara_namaPemilihan(String penyelenggara_namaPemilihan) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_namaPemilihan = penyelenggara_namaPemilihan;
    }

    public static String getPenyelenggara_simpanStatus() {
        return penyelenggara_simpanStatus;
    }

    public static void setPenyelenggara_simpanStatus(String penyelenggara_simpanStatus) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_simpanStatus = penyelenggara_simpanStatus;
    }

    public static String getPenyelenggara_tanggalMaxGabung() {
        return penyelenggara_tanggalMaxGabung;
    }

    public static void setPenyelenggara_tanggalMaxGabung(String penyelenggara_tanggalMaxGabung) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_tanggalMaxGabung = penyelenggara_tanggalMaxGabung;
    }

    public static String getPenyelenggara_tanggalMaxVote() {
        return penyelenggara_tanggalMaxVote;
    }

    public static void setPenyelenggara_tanggalMaxVote(String penyelenggara_tanggalMaxVote) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_tanggalMaxVote = penyelenggara_tanggalMaxVote;
    }

    public static String getPenyelenggara_tanggalPerhitungan() {
        return penyelenggara_tanggalPerhitungan;
    }

    public static void setPenyelenggara_tanggalPerhitungan(String penyelenggara_tanggalPerhitungan) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_tanggalPerhitungan = penyelenggara_tanggalPerhitungan;
    }

    public static String getPenyelenggara_keterangan() {
        return penyelenggara_keterangan;
    }

    public static void setPenyelenggara_keterangan(String penyelenggara_keterangan) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_keterangan = penyelenggara_keterangan;
    }

    public static String getPenyelenggara_peserta() {
        return penyelenggara_peserta;
    }

    public static void setPenyelenggara_peserta(String penyelenggara_peserta) {
        DataPemilihanPenyelenggaraTerpilih.penyelenggara_peserta = penyelenggara_peserta;
    }
}
