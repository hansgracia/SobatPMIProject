package com.example.hans_pc.sobatpmi.Model;

public class DataInformasiUmum {

    String namaInformasi, isiInformasi, tanggalInformasi;

    public String getNamaInformasi() {
        return namaInformasi;
    }

    public void setNamaInformasi(String namaInformasi) {
        this.namaInformasi = namaInformasi;
    }

    public String getIsiInformasi() {
        return isiInformasi;
    }

    public void setIsiInformasi(String isiInformasi) {
        this.isiInformasi = isiInformasi;
    }

    public String getTanggalInformasi() {
        return tanggalInformasi;
    }

    public void setTanggalInformasi(String tanggalInformasi) {
        this.tanggalInformasi = tanggalInformasi;
    }

    public DataInformasiUmum(String namaInformasi, String isiInformasi, String tanggalInformasi) {
        this.namaInformasi = namaInformasi;
        this.isiInformasi = isiInformasi;
        this.tanggalInformasi = tanggalInformasi;
    }
}
