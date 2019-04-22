package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Detail.LokasiPMIDetail;
import com.example.hans_pc.sobatpmi.Model.DataLokasiPMI;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class LokasiPMIAdapter extends RecyclerView.Adapter<LokasiPMIAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DataLokasiPMI> data;
    private DataLokasiPMI dataLokasiPMI;

    public LokasiPMIAdapter(Context context, ArrayList<DataLokasiPMI> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView row_namaLokasiPMI, row_posisiLokasiPMI;

        public MyViewHolder(View itemView){
            super(itemView);

            row_namaLokasiPMI = itemView.findViewById(R.id.rowNamaLokasiPMI);
            row_posisiLokasiPMI = itemView.findViewById(R.id.rowPosisiLokasiPMI);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.row_lokasi_pmi, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.row_namaLokasiPMI.setText(data.get(position).getNamaLokasiPMI());
        myViewHolder.row_posisiLokasiPMI.setText(data.get(position).getPosisiLokasiPMI());

        myViewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), LokasiPMIDetail.class);
                        intent.putExtra("Nama Lokasi PMI", data.get(position).getNamaLokasiPMI());
                        intent.putExtra("Latitude Lokasi PMI", String.valueOf(data.get(position).getLatitude()));
                        intent.putExtra("Longitude Lokasi PMI", String.valueOf(data.get(position).getLongitude()));
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
