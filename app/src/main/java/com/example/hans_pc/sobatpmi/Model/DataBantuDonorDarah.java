package com.example.hans_pc.sobatpmi.Model;

public class DataBantuDonorDarah {

    String date, email;

    public DataBantuDonorDarah(String date, String email) {
        this.email = email;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
