package com.muhammad_irvan.eev.Data;

import android.graphics.Bitmap;

/**
 * Created by Irvan on 24/07/2017.
 */

public class DataPemilihan {
    String id_pemilihan;
    Bitmap image_pemilihan;
    String judul_pemilihan;
    String tanggal_pemilhan;
    String keterangan_pemilihan;
    String penyelenggara_pemilihan;
    String penyelenggara_tanggalMaxGabung;
    String penyelenggara_tanggalMaxVote;
    String penyelenggara_tanggalPerhitungan;
    private static String penyelenggara_selesai;

    public static String getPenyelenggara_selesai() {
        return penyelenggara_selesai;
    }

    public static void setPenyelenggara_selesai(String penyelenggara_selesai) {
        DataPemilihan.penyelenggara_selesai = penyelenggara_selesai;
    }

    public String getPenyelenggara_tanggalMaxGabung() {
        return penyelenggara_tanggalMaxGabung;
    }

    public void setPenyelenggara_tanggalMaxGabung(String penyelenggara_tanggalMaxGabung) {
        this.penyelenggara_tanggalMaxGabung = penyelenggara_tanggalMaxGabung;
    }

    public String getPenyelenggara_tanggalMaxVote() {
        return penyelenggara_tanggalMaxVote;
    }

    public void setPenyelenggara_tanggalMaxVote(String penyelenggara_tanggalMaxVote) {
        this.penyelenggara_tanggalMaxVote = penyelenggara_tanggalMaxVote;
    }

    public String getPenyelenggara_tanggalPerhitungan() {
        return penyelenggara_tanggalPerhitungan;
    }

    public void setPenyelenggara_tanggalPerhitungan(String penyelenggara_tanggalPerhitungan) {
        this.penyelenggara_tanggalPerhitungan = penyelenggara_tanggalPerhitungan;
    }

    public String getId_pemilihan() {
        return id_pemilihan;
    }

    public void setId_pemilihan(String id_pemilihan) {
        this.id_pemilihan = id_pemilihan;
    }

    public Bitmap getImage_pemilihan() {
        return image_pemilihan;
    }

    public void setImage_pemilihan(Bitmap image_pemilihan) {
        this.image_pemilihan = image_pemilihan;
    }

    public String getJudul_pemilihan() {
        return judul_pemilihan;
    }

    public void setJudul_pemilihan(String judul_pemilihan) {
        this.judul_pemilihan = judul_pemilihan;
    }

    public String getTanggal_pemilhan() {
        return tanggal_pemilhan;
    }

    public void setTanggal_pemilhan(String tanggal_pemilhan) {
        this.tanggal_pemilhan = tanggal_pemilhan;
    }

    public String getKeterangan_pemilihan() {
        return keterangan_pemilihan;
    }

    public void setKeterangan_pemilihan(String keterangan_pemilihan) {
        this.keterangan_pemilihan = keterangan_pemilihan;
    }

    public String getPenyelenggara_pemilihan() {
        return penyelenggara_pemilihan;
    }

    public void setPenyelenggara_pemilihan(String penyelenggara_pemilihan) {
        this.penyelenggara_pemilihan = penyelenggara_pemilihan;
    }
}
