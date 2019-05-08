package com.example.hans_pc.sobatpmi.Model;

public class DataBantuDonorDarah {

    String date;
    String namaPenerimaDonor;
    String namaPemberiDonor;
    String emailPemberiDonor;

    public String getNamaPenerimaDonor() {
        return namaPenerimaDonor;
    }

    public void setNamaPenerimaDonor(String namaPenerimaDonor) {
        this.namaPenerimaDonor = namaPenerimaDonor;
    }

    public DataBantuDonorDarah(String date, String namaPenerimaDonor, String namaPemberiDonor, String emailPemberiDonor) {
        this.date = date;
        this.namaPenerimaDonor = namaPenerimaDonor;
        this.namaPemberiDonor = namaPemberiDonor;
        this.emailPemberiDonor = emailPemberiDonor;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamaPemberiDonor() {
        return namaPemberiDonor;
    }

    public void setNamaPemberiDonor(String namaPemberiDonor) {
        this.namaPemberiDonor = namaPemberiDonor;
    }

    public String getEmailPemberiDonor() {
        return emailPemberiDonor;
    }

    public void setEmailPemberiDonor(String emailPemberiDonor) {
        this.emailPemberiDonor = emailPemberiDonor;
    }
}
