package com.example.car_e_cm;

public class Bidang extends BidangId {
    String nama;

    public Bidang() {
    }

    public Bidang(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
