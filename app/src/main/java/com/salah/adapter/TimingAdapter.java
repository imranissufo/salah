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
import com.salah.model.Timings;

import java.util.ArrayList;
import java.util.Calendar;

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.TimingViewHolder> {

    Context context;
    ArrayList<Timings> timings;
    private Calendar calendar;

    public TimingAdapter(Context context, ArrayList<Timings> timings) {
        this.context = context;
        this.timings = timings;
        calendar = Calendar.getInstance();
    }

    public void setTimings(ArrayList<Timings> timings) {
        this.timings = timings;
    }

    @NonNull
    @Override
    public TimingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.timings_card, parent,false);
        return new TimingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimingViewHolder holder, int position) {
        Timings model = timings.get(position);

        holder.location.setText(model.getLocation());
        holder.code.setText(model.getDay()+"/"+model.getMonth()+"/"+calendar.get(Calendar.YEAR));
        holder.suhur.setText(model.getSuhur());
        holder.fajr.setText(model.getFajr());
        holder.sunrise.setText(model.getSunrise());
        holder.zawal.setText(model.getZawal());
        holder.zuhr.setText(model.getZuhr());
        holder.assr.setText(model.getAssr());
        holder.sunset.setText(model.getSunset());
        holder.magrib.setText(model.getMagrib());
        holder.isha.setText(model.getIsha());
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    public static class TimingViewHolder extends RecyclerView.ViewHolder{

        TextView code;
        TextView month;
        TextView day;
        TextView suhur;
        TextView fajr;
        TextView sunrise;
        TextView zawal;
        TextView zuhr;
        TextView assr;
        TextView sunset;
        TextView magrib;
        TextView isha;
        TextView location;

        public TimingViewHolder(@NonNull View itemView) {
            super(itemView);

            location =itemView.findViewById(R.id.tm_location_vl);
            code = itemView.findViewById(R.id.tm_date_vl);
            suhur = itemView.findViewById(R.id.tm_suhur_vl);
            fajr = itemView.findViewById(R.id.tm_fajr_vl);
            sunrise = itemView.findViewById(R.id.tm_sunrise_vl);
            zawal = itemView.findViewById(R.id.tm_zawal_vl);
            zuhr = itemView.findViewById(R.id.tm_zuhr_vl);
            assr = itemView.findViewById(R.id.tm_assr_vl);
            sunset = itemView.findViewById(R.id.tm_sunset_vl);
            magrib = itemView.findViewById(R.id.tm_magrib_vl);
            isha = itemView.findViewById(R.id.tm_isha_vl);
        }
    }
}
