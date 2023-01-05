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

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MasjidViewHolder> {

    ArrayList<Masjid> masjids = new ArrayList<>();

    public MasjidAdapter(ArrayList<Masjid> locations) {
        this.masjids = locations;
    }

    public void setMasjids(ArrayList<Masjid> masjids) {
        this.masjids = masjids;
    }

    @NonNull
    @Override
    public MasjidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_card, parent,false);
        MasjidViewHolder viewHolder = new MasjidViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MasjidViewHolder holder, int position) {
        Masjid model = masjids.get(position);

        holder.name.setText(WordUtils.capitalizeFully(model.getName(),' ','.'));
        holder.fajr.setText(model.getFajr());
        holder.zuhr.setText(model.getZuhr());
        holder.jumma.setText(model.getJumma());
        holder.assr.setText(model.getAssr());
        holder.isha.setText(model.getIsha());
        holder.location.setText(model.getLocation());
        holder.annc.setText(WordUtils.capitalizeFully(model.getAnnc()));
        holder.anncTime.setText(model.getAnncTime());
        holder.anncFajrDate.setText(model.getAnncFajrDate());
        holder.anncFajrTime.setText(model.getAnncFajrTime());
        holder.anncAssrDate.setText(model.getAnncAssrDate());
        holder.anncAssrTime.setText(model.getAnncAssrTime());
        holder.anncIshaDate.setText(model.getAnncIshaDate());
        holder.anncIshaTime.setText(model.getAnncIshaTime());

    }

    @Override
    public int getItemCount() {
        return masjids.size();
    }

    public static class MasjidViewHolder extends RecyclerView.ViewHolder{

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
        TextView anncFajrDate, anncFajrTime, anncAssrDate, anncAssrTime, anncIshaDate, anncIshaTime;

        public MasjidViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.mj_name);
            fajr = itemView.findViewById(R.id.mj_fajr_vl);
            zuhr = itemView.findViewById(R.id.mj_zuhr_vl);
            jumma = itemView.findViewById(R.id.mj_jumma_vl);
            assr = itemView.findViewById(R.id.mj_assr_vl);
            isha = itemView.findViewById(R.id.mj_isha_vl);
            location = itemView.findViewById(R.id.mj_location_vl);
            annc = itemView.findViewById(R.id.mj_annc_lb);
            anncTime = itemView.findViewById(R.id.mj_annc_vl);

            anncFajrDate= itemView.findViewById(R.id.mj_fajr2_date);
            anncFajrTime= itemView.findViewById(R.id.mj_fajr2_vl);
            anncAssrDate= itemView.findViewById(R.id.mj_assr2_date);
            anncAssrTime= itemView.findViewById(R.id.mj_assr2_vl);
            anncIshaDate= itemView.findViewById(R.id.mj_isha2_date);
            anncIshaTime= itemView.findViewById(R.id.mj_isha2_vl);

        }
    }
}
