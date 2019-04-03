package com.example.hans_pc.sobatpmi.Model;

public class DataDonorDarah {

    String penerimaDonor, deskripsiDonor, golDarahDonor, gambarDonor;
    int jumlahDonor;

    public DataDonorDarah(String penerimaDonor, String deskripsiDonor, String golDarahDonor, int jumlahDonor) {
        this.penerimaDonor = penerimaDonor;
        this.deskripsiDonor = deskripsiDonor;
        this.golDarahDonor = golDarahDonor;
        this.jumlahDonor = jumlahDonor;
    }

    public String getPenerimaDonor() {
        return penerimaDonor;
    }

    public void setPenerimaDonor(String penerimaDonor) {
        this.penerimaDonor = penerimaDonor;
    }

    public String getDeskripsiDonor() {
        return deskripsiDonor;
    }

    public void setDeskripsiDonor(String deskripsiDonor) {
        this.deskripsiDonor = deskripsiDonor;
    }

    public String getGolDarahDonor() {
        return golDarahDonor;
    }

    public void setGolDarahDonor(String golDarahDonor) {
        this.golDarahDonor = golDarahDonor;
    }

    public int getJumlahDonor() {
        return jumlahDonor;
    }

    public void setJumlahDonor(int jumlahDonor) {
        this.jumlahDonor = jumlahDonor;
    }

    public String getGambarDonor() {
        return gambarDonor;
    }

    public void setGambarDonor(String gambarDonor) {
        this.gambarDonor = gambarDonor;
    }
}
