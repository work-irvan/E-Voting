package com.muhammad_irvan.eev.Data;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Irvan on 31/07/2017.
 */

public class DataRespone extends Application {
    private static DataRespone singleton;

    private static String respone;

    public static String getRespone() {
        return respone;
    }

    public static void setRespone(String respone) {
        DataRespone.respone = respone;
    }

    public static DataRespone getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

}
