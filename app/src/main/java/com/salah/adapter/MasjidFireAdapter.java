package com.salah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.salah.R;
import com.salah.model.Masjid;

public class MasjidFireAdapter extends FirebaseRecyclerAdapter<Masjid, MasjidFireAdapter.MasjidViewHolder> {

    public MasjidFireAdapter(@NonNull FirebaseRecyclerOptions<Masjid> options) {
        super(options);
    }

    @NonNull
    @Override
    public MasjidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_card, parent, false);
        MasjidViewHolder viewHolder = new MasjidViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull MasjidViewHolder holder, int position, @NonNull Masjid model) {
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

    public static class MasjidViewHolder extends RecyclerView.ViewHolder {

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
            anncTime = itemView.findViewById(R.id.mj_anncTime_vl);
        }
    }

}
