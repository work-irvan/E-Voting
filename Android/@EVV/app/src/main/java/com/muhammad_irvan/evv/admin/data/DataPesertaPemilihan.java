package com.muhammad_irvan.evv.admin.data;

/**
 * Created by Irvan on 08/08/2017.
 */

public class DataPesertaPemilihan {
    private static String id_pemilihan;
    private static String id_peserta;
    private static String nama;
    private static String jenis_kelamin;
    private static String tanggal_lahir;
    private static String alamat;
    private static String email;

    public static String getId_pemilihan() {
        return id_pemilihan;
    }

    public static void setId_pemilihan(String id_pemilihan) {
        DataPesertaPemilihan.id_pemilihan = id_pemilihan;
    }

    public static String getId_peserta() {
        return id_peserta;
    }

    public static void setId_peserta(String id_peserta) {
        DataPesertaPemilihan.id_peserta = id_peserta;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        DataPesertaPemilihan.nama = nama;
    }

    public static String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public static void setJenis_kelamin(String jenis_kelamin) {
        DataPesertaPemilihan.jenis_kelamin = jenis_kelamin;
    }

    public static String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public static void setTanggal_lahir(String tanggal_lahir) {
        DataPesertaPemilihan.tanggal_lahir = tanggal_lahir;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        DataPesertaPemilihan.alamat = alamat;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DataPesertaPemilihan.email = email;
    }
}
