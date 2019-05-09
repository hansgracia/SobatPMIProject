package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Detail.DonorDarahDetail;
import com.example.hans_pc.sobatpmi.Menu.KegiatanActivity;
import com.example.hans_pc.sobatpmi.Model.DataKegiatan;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataKegiatan> data;
    private LayoutInflater inflater;
    private KegiatanActivity kegiatanActivity;

    public KegiatanAdapter(Context context, ArrayList<DataKegiatan> data) {
        this.context = context;
        this.data = data;

        inflater = LayoutInflater.from(context);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rowNamaKegiatan, rowTempatKegiatan, rowWaktuKegiatan, rowKeteranganKegiatan
                , rowOptionRVKegiatan;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowNamaKegiatan = itemView.findViewById(R.id.rowNamaKegiatan);
            rowTempatKegiatan = itemView.findViewById(R.id.rowTempatKegiatan);
            rowWaktuKegiatan = itemView.findViewById(R.id.rowWaktuKegiatan);
            rowKeteranganKegiatan = itemView.findViewById(R.id.rowKeteranganKegiatan);
            rowOptionRVKegiatan = itemView.findViewById(R.id.rowOptionRecyclerViewKegiatan);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.row_kegiatan, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.rowNamaKegiatan.setText(data.get(position).getNamaKegiatan());
        myViewHolder.rowTempatKegiatan.setText(data.get(position).getTempatKegiatan());
        myViewHolder.rowWaktuKegiatan.setText(data.get(position).getWaktuKegiatan());
        myViewHolder.rowKeteranganKegiatan.setText(data.get(position).getKeteranganKegiatan());


        myViewHolder.rowOptionRVKegiatan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, myViewHolder.rowOptionRVKegiatan);
                popupMenu.inflate(R.menu.option_recycler_view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.option_detail:
//                                Intent intent = new Intent(context, DonorDarahDetail.class);
//
//                                intent.putExtra("Nama Kegiatan", data.get(position).getNamaKegiatan());
//                                intent.putExtra("Tempat Kegiatan", data.get(position).getTempatKegiatan());
//                                intent.putExtra("Waktu Kegiatan", data.get(position).getWaktuKegiatan());
//                                intent.putExtra("Keterangan Kegiatan", data.get(position).getKeteranganKegiatan());
//                                intent.putExtra("ID Kegiatan", data.get(position).getIdKegiatan());
//
//                                context.startActivity(intent);
                                break;
                            case R.id.option_update:
                                break;
                            case R.id.option_delete:
                                kegiatanActivity.deleteData(data.get(position).getIdKegiatan());
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}