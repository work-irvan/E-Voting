package com.muhammad_irvan.evv.admin.data;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Irvan on 05/08/2017.
 */

public class DataPanitiaTerpilih extends Application {
    private static DataPanitiaTerpilih singleton;

    private static String id_panitia;
    private static String nama;
    private static String level;
    private static String email;
    private static String password;



    public static DataPanitiaTerpilih getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }


    public static DataPanitiaTerpilih getSingleton() {
        return singleton;
    }



    public static String getId_panitia() {
        return id_panitia;
    }

    public static void setId_panitia(String id_panitia) {
        DataPanitiaTerpilih.id_panitia = id_panitia;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        DataPanitiaTerpilih.nama = nama;
    }

    public static String getLevel() {
        return level;
    }

    public static void setLevel(String level) {
        DataPanitiaTerpilih.level = level;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DataPanitiaTerpilih.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataPanitiaTerpilih.password = password;
    }
}
