package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Detail.KegiatanPMIDetail;
import com.example.hans_pc.sobatpmi.Menu.KegiatanPMIActivity;
import com.example.hans_pc.sobatpmi.Model.DataKegiatanPMI;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class KegiatanPMIAdapter extends RecyclerView.Adapter<KegiatanPMIAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<DataKegiatanPMI> data;
    private KegiatanPMIActivity KegiatanPMIActivity = new KegiatanPMIActivity();

    public KegiatanPMIAdapter (Context context, ArrayList<DataKegiatanPMI> data){
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView row_namaKegiatan, row_isiKegiatan, row_tanggalKegiatan;

        public MyViewHolder(View itemView){
            super(itemView);

            row_namaKegiatan = itemView.findViewById(R.id.rowNamaInformasi);
            row_isiKegiatan = itemView.findViewById(R.id.rowIsiInformasi);
            row_tanggalKegiatan = itemView.findViewById(R.id.rowTanggalBuatInformasi);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = layoutInflater.inflate(R.layout.row_informasi_umum, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.row_namaKegiatan.setText(data.get(position).getNamaKegiatan());
        myViewHolder.row_isiKegiatan.setText(data.get(position).getIsiKegiatan());
        myViewHolder.row_tanggalKegiatan.setText(data.get(position).getTanggalKegiatan());

        myViewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), KegiatanPMIDetail.class);
                        intent.putExtra("Nama Kegiatan Detail", data.get(position).getNamaKegiatan());
                        intent.putExtra("Isi Kegiatan Detail", data.get(position).getIsiKegiatan());
                        intent.putExtra("Tanggal Kegiatan Detail", data.get(position).getTanggalKegiatan());
                        context.startActivity(intent);
                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
