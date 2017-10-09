package com.muhammad_irvan.evv.admin.data;

/**
 * Created by Irvan on 10/08/2017.
 */

public class DataPanitia {
    private String id_panitia;
    private String nama;
    private String level;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_panitia() {
        return id_panitia;
    }

    public void setId_panitia(String id_panitia) {
        this.id_panitia = id_panitia;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
