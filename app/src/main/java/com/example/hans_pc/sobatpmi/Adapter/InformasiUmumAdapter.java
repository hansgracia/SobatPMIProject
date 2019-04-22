package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Menu.InformasiUmumActivity;
import com.example.hans_pc.sobatpmi.Model.DataInformasiUmum;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class InformasiUmumAdapter extends RecyclerView.Adapter<InformasiUmumAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<DataInformasiUmum> data;
    private InformasiUmumActivity informasiUmumActivity = new InformasiUmumActivity();

    public InformasiUmumAdapter (Context context, ArrayList<DataInformasiUmum> data){
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView row_namaInformasi, row_isiInformasi, row_tanggalInformasi;

        public MyViewHolder(View itemView){
            super(itemView);

            row_namaInformasi = itemView.findViewById(R.id.rowNamaInformasi);
            row_isiInformasi = itemView.findViewById(R.id.rowIsiInformasi);
            row_tanggalInformasi = itemView.findViewById(R.id.rowTanggalBuatInformasi);

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.row_namaInformasi.setText(data.get(position).getNamaInformasi());
        myViewHolder.row_isiInformasi.setText(data.get(position).getIsiInformasi());
        myViewHolder.row_tanggalInformasi.setText(data.get(position).getTanggalInformasi());

        myViewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
