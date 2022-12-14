package com.salah.adapter;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.salah.R;
import com.salah.activity.MasjidAnnc1Activity;
import com.salah.activity.MasjidDeleteActivity;
import com.salah.activity.MasjidForm1Activity;
import com.salah.model.Masjid;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;

public class MasjidAdminAdapter extends RecyclerView.Adapter<MasjidAdminAdapter.MasgidViewHolder> {

    Context context;
    ArrayList<Masjid> masjids;

    public MasjidAdminAdapter(Context context, ArrayList<Masjid> locations) {
        this.context = context;
        this.masjids = locations;
    }

    public void setMasjids(ArrayList<Masjid> masjids) {
        this.masjids = masjids;
    }

    @NonNull
    @Override
    public MasgidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_admin_card, parent,false);
        return new MasgidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasgidViewHolder holder, int position) {
        Masjid model = masjids.get(position);

        holder.name.setText(WordUtils.capitalizeFully(model.getName(),' ','.'));
        holder.location.setText(model.getLocation());

        holder.fajr.setText(model.getFajr());
        holder.zuhr.setText(model.getZuhr());
        holder.jumma.setText(model.getJumma());
        holder.assr.setText(model.getAssr());
        holder.isha.setText(model.getIsha());
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

        holder.deleteMasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasjidDeleteActivity.class);
                intent.putExtra("masjid", model);
                intent.putExtra("action", "DELETE");
                context.startActivity(intent);
            }
        });
        holder.arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.masjidDetails.getVisibility()== View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.arrowDown.setVisibility(View.GONE);
                    holder.arrowUp.setVisibility(View.VISIBLE);
                    holder.masjidDetails.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.arrowUp.setVisibility(View.GONE);
                    holder.arrowDown.setVisibility(View.VISIBLE);
                    holder.masjidDetails.setVisibility(View.GONE);
                }
            }
        });
        holder.arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.masjidDetails.getVisibility()== View.GONE){
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.arrowDown.setVisibility(View.GONE);
                    holder.arrowUp.setVisibility(View.VISIBLE);
                    holder.masjidDetails.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.arrowUp.setVisibility(View.GONE);
                    holder.arrowDown.setVisibility(View.VISIBLE);
                    holder.masjidDetails.setVisibility(View.GONE);
                }
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
        MaterialButton editMasjid;
        MaterialButton editAnnouncement;
        MaterialButton deleteMasjid;

        MaterialCardView cardView;
        LinearLayout masjidDetails;
        ImageView arrowDown,arrowUp;


        public MasgidViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.mj_name);
            location = itemView.findViewById(R.id.mj_location_vl);

            fajr = itemView.findViewById(R.id.mj_fajr_vl);
            zuhr = itemView.findViewById(R.id.mj_zuhr_vl);
            jumma = itemView.findViewById(R.id.mj_jumma_vl);
            assr = itemView.findViewById(R.id.mj_assr_vl);
            isha = itemView.findViewById(R.id.mj_isha_vl);
            annc = itemView.findViewById(R.id.mj_annc_lb);
            anncTime = itemView.findViewById(R.id.mj_annc_vl);

            anncFajrDate= itemView.findViewById(R.id.mj_annc_fajr_date);
            anncFajrTime= itemView.findViewById(R.id.mj_annc_fajr_vl);
            anncAssrDate= itemView.findViewById(R.id.mj_annc_assr_date);
            anncAssrTime= itemView.findViewById(R.id.mj_annc_assr_vl);
            anncIshaDate= itemView.findViewById(R.id.mj_annc_isha_date);
            anncIshaTime= itemView.findViewById(R.id.mj_annc_isha_vl);

            masjidDetails = itemView.findViewById(R.id.mj_details);
            cardView = itemView.findViewById(R.id.card);
            arrowDown = itemView.findViewById(R.id.mj_arrow_down);
            arrowUp = itemView.findViewById(R.id.mj_arrow_up);

            editMasjid = itemView.findViewById(R.id.mj_edit);
            editAnnouncement = itemView.findViewById(R.id.mj_annc_edit);
            deleteMasjid = itemView.findViewById(R.id.mj_delete);
        }
    }
}
