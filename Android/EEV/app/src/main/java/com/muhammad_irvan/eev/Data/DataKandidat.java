package com.muhammad_irvan.eev.Data;

import android.graphics.Bitmap;

/**
 * Created by Irvan on 26/07/2017.
 */

public class DataKandidat {
    Bitmap KandidatImage;
    String KandidatId;
    String KandidatIdPemilihan;
    String KandidatNama;
    String KandidatKeterangan;
    String KandidatSlogan;
    String KandidatTanggal;
    String KandidatAlamat;

    public String getKandidatIdPemilihan() {
        return KandidatIdPemilihan;
    }

    public void setKandidatIdPemilihan(String kandidatIdPemilihan) {
        KandidatIdPemilihan = kandidatIdPemilihan;
    }

    public String getKandidatAlamat() {
        return KandidatAlamat;
    }

    public void setKandidatAlamat(String kandidatAlamat) {
        KandidatAlamat = kandidatAlamat;
    }

    public String getKandidatSlogan() {
        return KandidatSlogan;
    }

    public void setKandidatSlogan(String kandidatSlogan) {
        KandidatSlogan = kandidatSlogan;
    }

    public String getKandidatTanggal() {
        return KandidatTanggal;
    }

    public void setKandidatTanggal(String kandidatTanggal) {
        KandidatTanggal = kandidatTanggal;
    }

    public Bitmap getKandidatImage() {
        return KandidatImage;
    }

    public void setKandidatImage(Bitmap kandidatImage) {
        KandidatImage = kandidatImage;
    }

    public String getKandidatId() {
        return KandidatId;
    }

    public void setKandidatId(String kandidatId) {
        KandidatId = kandidatId;
    }

    public String getKandidatNama() {
        return KandidatNama;
    }

    public void setKandidatNama(String kandidatNama) {
        KandidatNama = kandidatNama;
    }

    public String getKandidatKeterangan() {
        return KandidatKeterangan;
    }

    public void setKandidatKeterangan(String kandidatKeterangan) {
        KandidatKeterangan = kandidatKeterangan;
    }
}
