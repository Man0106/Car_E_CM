package com.example.car_e_cm;

public class Proyek extends ProyekId {
    String nama, bidang, status;

    public Proyek() {
    }

    public Proyek(String nama, String bidang, String status) {
        this.nama = nama;
        this.bidang = bidang;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBidang() {
        return bidang;
    }

    public void setBidang(String bidang) {
        this.bidang = bidang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
