package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Animation.AnimationUtil;
import com.example.hans_pc.sobatpmi.Detail.DonorDarahDetail;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class DonorDarahAdapter extends RecyclerView.Adapter<DonorDarahAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataDonorDarah> data;
    private LayoutInflater inflater;
    private int previousPosition;

    public DonorDarahAdapter(Context context, ArrayList<DataDonorDarah> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rowPenerimaDonor, rowDeskripsiDonor;
        ImageView rowImagePenerimaDonor;


        public MyViewHolder(View itemView){
            super(itemView);

            rowPenerimaDonor = itemView.findViewById(R.id.rowPenerimaDonorDarah);
            rowDeskripsiDonor = itemView.findViewById(R.id.rowDescDonorDarah);
            rowImagePenerimaDonor = itemView.findViewById(R.id.rowImDonorDarah);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View view = inflater.inflate(R.layout.row_donor_darah, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position){

        myViewHolder.rowPenerimaDonor.setText(data.get(position).getPenerimaDonor());
        myViewHolder.rowDeskripsiDonor.setText(data.get(position).getDeskripsiDonor());
        myViewHolder.rowImagePenerimaDonor.setImageResource(0);

        if(position > previousPosition){
            AnimationUtil.animate(myViewHolder, true);
        }
        else{
            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;

        final int currentPosition = position;
        final DataDonorDarah infoData = data.get(position);

        myViewHolder.rowImagePenerimaDonor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DonorDarahDetail.class);

                        intent.putExtra("Penerima Donor", data.get(position).getPenerimaDonor());
                        intent.putExtra("Deskripsi Donor", data.get(position).getDeskripsiDonor());
                        intent.putExtra("Golongan Darah", data.get(position).getGolDarahDonor());
                        intent.putExtra("Jumlah Donor", String.valueOf(data.get(position).getJumlahDonor()));

                        context.startActivity(intent);
                    }
                }
        );

        myViewHolder.rowImagePenerimaDonor.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(context, "Donor Darah Berhasil dihapus", Toast.LENGTH_SHORT).show();

                        removeItem(infoData);

                        return true;
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void removeItem(DataDonorDarah dataDonorDarah){
        int currPosition = data.indexOf(dataDonorDarah);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }
}
