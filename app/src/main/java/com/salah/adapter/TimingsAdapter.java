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
import com.salah.model.Timings;

public class TimingsAdapter extends FirebaseRecyclerAdapter<Timings, TimingsAdapter.TimingsViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TimingsAdapter(@NonNull FirebaseRecyclerOptions<Timings> options) {
        super(options);
    }

    /*
    public TimingsAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }
    */

    @NonNull
    @Override
    public TimingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timings_card, parent, false);
        TimingsViewHolder viewHolder = new TimingsViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull TimingsViewHolder holder, int position, @NonNull Timings model) {
        holder.code.setText(model.getCode());
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

    public static class TimingsViewHolder extends RecyclerView.ViewHolder {

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

        public TimingsViewHolder(@NonNull View itemView) {
            super(itemView);

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
