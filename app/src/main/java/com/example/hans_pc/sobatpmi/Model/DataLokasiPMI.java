package com.example.hans_pc.sobatpmi.Model;

public class DataLokasiPMI {
    String namaLokasiPMI, posisiLokasiPMI;
    double longitude, latitude;

    public DataLokasiPMI(String namaLokasiPMI, String posisiLokasiPMI) {
        this.namaLokasiPMI = namaLokasiPMI;
        this.posisiLokasiPMI = posisiLokasiPMI;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getNamaLokasiPMI() {
        return namaLokasiPMI;
    }

    public void setNamaLokasiPMI(String namaLokasiPMI) {
        this.namaLokasiPMI = namaLokasiPMI;
    }

    public String getPosisiLokasiPMI() {
        return posisiLokasiPMI;
    }

    public void setPosisiLokasiPMI(String posisiLokasiPMI) {
        this.posisiLokasiPMI = posisiLokasiPMI;
    }
}
