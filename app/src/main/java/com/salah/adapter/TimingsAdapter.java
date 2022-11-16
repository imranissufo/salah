package com.salah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;

import java.util.ArrayList;

public class TimingsAdapter extends RecyclerView.Adapter<TimingsAdapter.TimingsViewHolder> {

    ArrayList<Location> locations;

    public TimingsAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public TimingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.timings_card, parent,false);
        TimingsViewHolder viewHolder = new TimingsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimingsViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.title.setText(location.getTitle());
        holder.desc.setText(location.getDesc());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class TimingsViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView desc;

        public TimingsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tm_date_lb);
            desc = itemView.findViewById(R.id.tm_date_vl);

        }
    }
}
