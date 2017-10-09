package com.muhammad_irvan.evv.admin;

import android.app.Application;

/**
 * Created by Irvan on 31/07/2017.
 */

public class InfoAkun extends Application {

    private static InfoAkun singleton;

    private static String info_peserta_id_peserta;
    private static String info_peserta_no_identitas;
    private static String info_peserta_nama;
    private static String info_peserta_jenis_kelamin;
    private static String info_peserta_tanggal_lahir;
    private static String info_peserta_alamat;
    private static String info_peserta_email;

    private static String info_penyelenggara_id_penyelenggara;
    private static String info_penyelenggara_no_identitas;
    private static String info_penyelenggara_nama_organisasi;
    private static String info_penyelenggara_nama_penyelenggara;
    private static String info_penyelenggara_alamat;
    private static String info_penyelenggara_email;
    private static int level;

    private static String info_id_pemilihan;

    private Boolean PanitiaPeserta = false;
    private Boolean PanitiaKandidat = false;

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        InfoAkun.level = level;
    }

    public Boolean getPanitiaPeserta() {
        return PanitiaPeserta;
    }

    public void setPanitiaPeserta(Boolean panitiaPeserta) {
        PanitiaPeserta = panitiaPeserta;
    }

    public Boolean getPanitiaKandidat() {
        return PanitiaKandidat;
    }

    public void setPanitiaKandidat(Boolean panitiaKandidat) {
        PanitiaKandidat = panitiaKandidat;
    }

    public static String getInfo_id_pemilihan() {
        return info_id_pemilihan;
    }

    public static void setInfo_id_pemilihan(String info_id_pemilihan) {
        InfoAkun.info_id_pemilihan = info_id_pemilihan;
    }

    public static String getInfo_peserta_id_peserta() {
        return info_peserta_id_peserta;
    }

    public static void setInfo_peserta_id_peserta(String info_peserta_id_peserta) {
        InfoAkun.info_peserta_id_peserta = info_peserta_id_peserta;
    }

    public static String getInfo_peserta_no_identitas() {
        return info_peserta_no_identitas;
    }

    public static void setInfo_peserta_no_identitas(String info_peserta_no_identitas) {
        InfoAkun.info_peserta_no_identitas = info_peserta_no_identitas;
    }

    public static String getInfo_peserta_nama() {
        return info_peserta_nama;
    }

    public static void setInfo_peserta_nama(String info_peserta_nama) {
        InfoAkun.info_peserta_nama = info_peserta_nama;
    }

    public static String getInfo_peserta_jenis_kelamin() {
        return info_peserta_jenis_kelamin;
    }

    public static void setInfo_peserta_jenis_kelamin(String info_peserta_jenis_kelamin) {
        InfoAkun.info_peserta_jenis_kelamin = info_peserta_jenis_kelamin;
    }

    public static String getInfo_peserta_tanggal_lahir() {
        return info_peserta_tanggal_lahir;
    }

    public static void setInfo_peserta_tanggal_lahir(String info_peserta_tanggal_lahir) {
        InfoAkun.info_peserta_tanggal_lahir = info_peserta_tanggal_lahir;
    }

    public static String getInfo_peserta_alamat() {
        return info_peserta_alamat;
    }

    public static void setInfo_peserta_alamat(String info_peserta_alamat) {
        InfoAkun.info_peserta_alamat = info_peserta_alamat;
    }

    public static String getInfo_peserta_email() {
        return info_peserta_email;
    }

    public static void setInfo_peserta_email(String info_peserta_email) {
        InfoAkun.info_peserta_email = info_peserta_email;
    }

    public static String getInfo_penyelenggara_id_penyelenggara() {
        return info_penyelenggara_id_penyelenggara;
    }

    public static void setInfo_penyelenggara_id_penyelenggara(String info_penyelenggara_id_penyelenggara) {
        InfoAkun.info_penyelenggara_id_penyelenggara = info_penyelenggara_id_penyelenggara;
    }

    public static String getInfo_penyelenggara_no_identitas() {
        return info_penyelenggara_no_identitas;
    }

    public static void setInfo_penyelenggara_no_identitas(String info_penyelenggara_no_identitas) {
        InfoAkun.info_penyelenggara_no_identitas = info_penyelenggara_no_identitas;
    }

    public static String getInfo_penyelenggara_nama_organisasi() {
        return info_penyelenggara_nama_organisasi;
    }

    public static void setInfo_penyelenggara_nama_organisasi(String info_penyelenggara_nama_organisasi) {
        InfoAkun.info_penyelenggara_nama_organisasi = info_penyelenggara_nama_organisasi;
    }

    public static String getInfo_penyelenggara_nama_penyelenggara() {
        return info_penyelenggara_nama_penyelenggara;
    }

    public static void setInfo_penyelenggara_nama_penyelenggara(String info_penyelenggara_nama_penyelenggara) {
        InfoAkun.info_penyelenggara_nama_penyelenggara = info_penyelenggara_nama_penyelenggara;
    }

    public static String getInfo_penyelenggara_alamat() {
        return info_penyelenggara_alamat;
    }

    public static void setInfo_penyelenggara_alamat(String info_penyelenggara_alamat) {
        InfoAkun.info_penyelenggara_alamat = info_penyelenggara_alamat;
    }

    public static String getInfo_penyelenggara_email() {
        return info_penyelenggara_email;
    }

    public static void setInfo_penyelenggara_email(String info_penyelenggara_email) {
        InfoAkun.info_penyelenggara_email = info_penyelenggara_email;
    }

    public static InfoAkun getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
