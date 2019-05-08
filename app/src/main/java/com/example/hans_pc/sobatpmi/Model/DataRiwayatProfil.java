package com.example.hans_pc.sobatpmi.Model;

public class DataRiwayatProfil {

    String dateRiwayat, namaPenerimaDonor;

    public DataRiwayatProfil(String dateRiwayat, String namaPenerimaDonor) {
        this.dateRiwayat = dateRiwayat;
        this.namaPenerimaDonor = namaPenerimaDonor;
    }

    public String getDateRiwayat() {
        return dateRiwayat;
    }

    public void setDateRiwayat(String date) {
        this.dateRiwayat = date;
    }

    public String getNamaPenerimaDonor() {
        return namaPenerimaDonor;
    }

    public void setNamaPenerimaDonor(String namaPenerimaDonor) {
        this.namaPenerimaDonor = namaPenerimaDonor;
    }
}
