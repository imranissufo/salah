package com.salah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;
import com.salah.model.Masjid;

import java.util.ArrayList;

public class MasgidAdapter extends RecyclerView.Adapter<MasgidAdapter.MasgidViewHolder> {

    ArrayList<Masjid> masjids = new ArrayList<>();

    public MasgidAdapter(ArrayList<Masjid> locations) {
        this.masjids = locations;
    }

    public void setMasjids(ArrayList<Masjid> masjids) {
        this.masjids = masjids;
    }

    @NonNull
    @Override
    public MasgidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.masgid_card, parent,false);
        MasgidViewHolder viewHolder = new MasgidViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MasgidViewHolder holder, int position) {
        Masjid model = masjids.get(position);

        holder.name.setText(model.getName());
        holder.fajr.setText(model.getFajr());
        holder.zuhr.setText(model.getZuhr());
        holder.jumma.setText(model.getJumma());
        holder.assr.setText(model.getAssr());
        holder.isha.setText(model.getIsha());
        holder.location.setText(model.getLocation());
        holder.annc.setText(model.getAnnc());
        holder.anncTime.setText(model.getAnncTime());

    }

    @Override
    public int getItemCount() {
        return masjids.size();
    }

    public static class MasgidViewHolder extends RecyclerView.ViewHolder{

        TextView code;
        TextView name;
        TextView fajr;
        TextView zuhr;
        TextView jumma;
        TextView assr;
        TextView magrib;
        TextView isha;
        TextView location;
        TextView annc;
        TextView anncTime;

        public MasgidViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.mj_name);
            fajr = itemView.findViewById(R.id.mj_fajr_vl);
            zuhr = itemView.findViewById(R.id.mj_zuhr_vl);
            jumma = itemView.findViewById(R.id.mj_jumma_vl);
            assr = itemView.findViewById(R.id.mj_assr_vl);
            isha = itemView.findViewById(R.id.mj_isha_vl);
            location = itemView.findViewById(R.id.mj_location_vl);
            annc = itemView.findViewById(R.id.mj_annc_lb);
            anncTime = itemView.findViewById(R.id.mj_anncTime_vl);
        }
    }
}
