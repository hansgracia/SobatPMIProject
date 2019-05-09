package com.example.hans_pc.sobatpmi.Model;

public class DataKegiatan {

    public DataKegiatan(String idKegiatan, String namaKegiatan,
                        String tempatKegiatan, String waktuKegiatan, String keteranganKegiatan) {
        this.idKegiatan = idKegiatan;
        this.namaKegiatan = namaKegiatan;
        this.tempatKegiatan = tempatKegiatan;
        this.waktuKegiatan = waktuKegiatan;
        this.keteranganKegiatan = keteranganKegiatan;
    }

    String idKegiatan, namaKegiatan, tempatKegiatan, waktuKegiatan, keteranganKegiatan;

    public String getIdKegiatan() {
        return idKegiatan;
    }

    public void setIdKegiatan(String idKegiatan) {
        this.idKegiatan = idKegiatan;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getTempatKegiatan() {
        return tempatKegiatan;
    }

    public void setTempatKegiatan(String tempatKegiatan) {
        this.tempatKegiatan = tempatKegiatan;
    }

    public String getWaktuKegiatan() {
        return waktuKegiatan;
    }

    public void setWaktuKegiatan(String waktuKegiatan) {
        this.waktuKegiatan = waktuKegiatan;
    }

    public String getKeteranganKegiatan() {
        return keteranganKegiatan;
    }

    public void setKeteranganKegiatan(String keteranganKegiatan) {
        this.keteranganKegiatan = keteranganKegiatan;
    }

}
