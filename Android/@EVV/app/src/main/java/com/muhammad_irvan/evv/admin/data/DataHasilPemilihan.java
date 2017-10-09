package com.muhammad_irvan.evv.admin.data;

import android.graphics.Bitmap;

/**
 * Created by Irvan on 26/07/2017.
 */

public class DataHasilPemilihan {
    Bitmap ImageKandidat;
    String NamaKandidat;
    String PerolehanSuara;

    public Bitmap getImageKandidat() {
        return ImageKandidat;
    }

    public void setImageKandidat(Bitmap imageKandidat) {
        ImageKandidat = imageKandidat;
    }

    public String getNamaKandidat() {
        return NamaKandidat;
    }

    public void setNamaKandidat(String namaKandidat) {
        NamaKandidat = namaKandidat;
    }

    public String getPerolehanSuara() {
        return PerolehanSuara;
    }

    public void setPerolehanSuara(String perolehanSuara) {
        PerolehanSuara = perolehanSuara;
    }
}
