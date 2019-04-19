package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Menu.LokasiPMIActivity;
import com.example.hans_pc.sobatpmi.Model.DataLokasiPMI;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class LokasiPMIAdapter extends RecyclerView.Adapter<LokasiPMIAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DataLokasiPMI> data;
    private LokasiPMIActivity lokasiPMIActivity = new LokasiPMIActivity();

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
//        View view = inflater.inflate(R.layout.row_lokasi_pmi, parent, false);
//
//        MyViewHolder holder = new MyViewHolder(view);
//
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.row_namaLokasiPMI.setText(data.get(position).getNamaLokasiPMI());
        myViewHolder.row_posisiLokasiPMI.setText(data.get(position).getPosisiLokasiPMI());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
