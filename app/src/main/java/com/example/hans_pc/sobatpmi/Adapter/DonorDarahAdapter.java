package com.example.hans_pc.sobatpmi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hans_pc.sobatpmi.Detail.DonorDarahDetail;
import com.example.hans_pc.sobatpmi.Menu.DonorDarahActivity;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;

import java.util.ArrayList;

public class DonorDarahAdapter extends RecyclerView.Adapter<DonorDarahAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataDonorDarah> data;
    private LayoutInflater inflater;
    private DonorDarahActivity donorDarahActivity = new DonorDarahActivity();

    public DonorDarahAdapter(Context context, ArrayList<DataDonorDarah> data) {
        this.context = context;
        this.data = data;

        inflater = LayoutInflater.from(context);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rowPenerimaDonor, rowDeskripsiDonor, rowOptionRecylerView;
        ImageView rowImagePenerimaDonor;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowPenerimaDonor = itemView.findViewById(R.id.rowPenerimaDonorDarah);
            rowDeskripsiDonor = itemView.findViewById(R.id.rowDescDonorDarah);
            rowImagePenerimaDonor = itemView.findViewById(R.id.rowImDonorDarah);
            rowOptionRecylerView = itemView.findViewById(R.id.rowOptionRecyclerView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.row_donor_darah, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.rowPenerimaDonor.setText(data.get(position).getPenerimaDonor());
        myViewHolder.rowDeskripsiDonor.setText(data.get(position).getDeskripsiDonor());

        Glide.with(context).load(data.get(position).getGambarDonor()).fitCenter()
                .into(myViewHolder.rowImagePenerimaDonor);

        myViewHolder.rowOptionRecylerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, myViewHolder.rowOptionRecylerView);
                popupMenu.inflate(R.menu.option_recycler_view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.option_detail:
                                Intent intent = new Intent(context, DonorDarahDetail.class);

                                intent.putExtra("Penerima Donor", data.get(position).getPenerimaDonor());
                                intent.putExtra("Deskripsi Donor", data.get(position).getDeskripsiDonor());
                                intent.putExtra("Golongan Darah", data.get(position).getGolDarahDonor());
                                intent.putExtra("Jumlah Donor", String.valueOf(data.get(position).getJumlahDonor()));
                                intent.putExtra("ID Donor", data.get(position).getId());
                                intent.putExtra("Gambar Donor", data.get(position).getGambarDonor());

                                context.startActivity(intent);
                                break;
                            case R.id.option_update:
                                break;
                            case R.id.option_delete:
                                donorDarahActivity.deleteData(data.get(position).getId());
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
