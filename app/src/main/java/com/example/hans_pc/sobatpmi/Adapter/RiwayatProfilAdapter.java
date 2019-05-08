package com.example.hans_pc.sobatpmi.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Model.DataRiwayatProfil;
import com.example.hans_pc.sobatpmi.R;

import java.util.List;

public class RiwayatProfilAdapter extends ArrayAdapter<DataRiwayatProfil> {
    public RiwayatProfilAdapter(Context context, List<DataRiwayatProfil> object){
        super(context,0, object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(
                    R.layout.row_riwayat_profil,parent,false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.rowDateRiwayatProfil);
        TextView namaPenerimaTextView = (TextView) convertView.findViewById(R.id.rowNamaPenerimaDonor);

        DataRiwayatProfil dataRiwayatProfil = getItem(position);

        dateTextView.setText(dataRiwayatProfil.getDateRiwayat());
        namaPenerimaTextView.setText(dataRiwayatProfil.getNamaPenerimaDonor());

        return convertView;
    }

}
