package com.salah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.activity.MasjidAnnc1Activity;
import com.salah.activity.MasjidForm1Activity;
import com.salah.model.Masjid;

import java.util.ArrayList;

public class MasgidAdapter extends RecyclerView.Adapter<MasgidAdapter.MasgidViewHolder> {

    Context context;
    ArrayList<Masjid> masjids;

    public MasgidAdapter(Context context, ArrayList<Masjid> locations) {
        this.context = context;
        this.masjids = locations;
    }

    public void setMasjids(ArrayList<Masjid> masjids) {
        this.masjids = masjids;
    }

    @NonNull
    @Override
    public MasgidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.masgid_card, parent,false);
        return new MasgidViewHolder(view);
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
        holder.anncFajrDate.setText(model.getAnncFajrDate());
        holder.anncFajrTime.setText(model.getAnncFajrTime());
        holder.anncAssrDate.setText(model.getAnncAssrDate());
        holder.anncAssrTime.setText(model.getAnncAssrTime());
        holder.anncIshaDate.setText(model.getAnncIshaDate());
        holder.anncIshaTime.setText(model.getAnncIshaTime());
        holder.editMasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasjidForm1Activity.class);
                intent.putExtra("masjid", model);
                intent.putExtra("action", "EDIT");
                context.startActivity(intent);
            }
        });

        holder.editAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasjidAnnc1Activity.class);
                intent.putExtra("masjid", model);
                intent.putExtra("action", "EDIT");
                context.startActivity(intent);
            }
        });
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
        TextView anncFajrDate, anncFajrTime, anncAssrDate, anncAssrTime, anncIshaDate, anncIshaTime;
        ImageView editMasjid;
        ImageView editAnnouncement;

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
            anncTime = itemView.findViewById(R.id.mj_annc_vl);

            anncFajrDate= itemView.findViewById(R.id.mj_annc_fajr_date);
            anncFajrTime= itemView.findViewById(R.id.mj_annc_fajr_vl);
            anncAssrDate= itemView.findViewById(R.id.mj_annc_assr_date);
            anncAssrTime= itemView.findViewById(R.id.mj_annc_assr_vl);
            anncIshaDate= itemView.findViewById(R.id.mj_annc_isha_date);
            anncIshaTime= itemView.findViewById(R.id.mj_annc_isha_vl);

            editMasjid = itemView.findViewById(R.id.mj_edit);
            editAnnouncement = itemView.findViewById(R.id.mj_annc_edit);
        }
    }
}
