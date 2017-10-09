package com.muhammad_irvan.evv.admin.data;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Irvan on 05/08/2017.
 */

public class DataKandidatPenyelenggaraTerpilih extends Application {
    private static DataKandidatPenyelenggaraTerpilih singleton;

    private static Bitmap KandidatImage;
    private static String KandidatId;
    private static String KandidatIdPemilihan;
    private static String KandidatNama;
    private static String KandidatKeterangan;
    private static String KandidatSlogan;
    private static String KandidatTanggal;
    private static String KandidatAlamat;
    private static String respone;

    public static DataKandidatPenyelenggaraTerpilih getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static String getRespone() {
        return respone;
    }

    public static void setRespone(String respone) {
        DataKandidatPenyelenggaraTerpilih.respone = respone;
    }

    public static Bitmap getKandidatImage() {
        return KandidatImage;
    }

    public static void setKandidatImage(Bitmap kandidatImage) {
        KandidatImage = kandidatImage;
    }

    public static String getKandidatId() {
        return KandidatId;
    }

    public static void setKandidatId(String kandidatId) {
        KandidatId = kandidatId;
    }

    public static String getKandidatIdPemilihan() {
        return KandidatIdPemilihan;
    }

    public static void setKandidatIdPemilihan(String kandidatIdPemilihan) {
        KandidatIdPemilihan = kandidatIdPemilihan;
    }

    public static String getKandidatNama() {
        return KandidatNama;
    }

    public static void setKandidatNama(String kandidatNama) {
        KandidatNama = kandidatNama;
    }

    public static String getKandidatKeterangan() {
        return KandidatKeterangan;
    }

    public static void setKandidatKeterangan(String kandidatKeterangan) {
        KandidatKeterangan = kandidatKeterangan;
    }

    public static String getKandidatSlogan() {
        return KandidatSlogan;
    }

    public static void setKandidatSlogan(String kandidatSlogan) {
        KandidatSlogan = kandidatSlogan;
    }

    public static String getKandidatTanggal() {
        return KandidatTanggal;
    }

    public static void setKandidatTanggal(String kandidatTanggal) {
        KandidatTanggal = kandidatTanggal;
    }

    public static String getKandidatAlamat() {
        return KandidatAlamat;
    }

    public static void setKandidatAlamat(String kandidatAlamat) {
        KandidatAlamat = kandidatAlamat;
    }
}
