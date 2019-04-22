package com.example.hans_pc.sobatpmi.Detail;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.hans_pc.sobatpmi.Model.DataLokasiPMI;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LokasiPMIDetail extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    DataLokasiPMI dataLokasiPMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pmidetail);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        Intent in = getIntent();

        double dLat = Double.parseDouble(in.getStringExtra("Latitude Lokasi PMI"));
        double dLong = Double.parseDouble(in.getStringExtra("Longitude Lokasi PMI"));

        LatLng latLng = new LatLng(dLat, dLong);

        map.addMarker(new MarkerOptions().position(latLng).title(in.getStringExtra("Nama Lokasi PMI")));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
