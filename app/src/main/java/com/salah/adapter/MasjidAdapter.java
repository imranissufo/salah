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

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MostViewedViewHolder> {

    ArrayList<Location> locations;

    public MasjidAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public MasjidAdapter.MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.masjid_card, parent,false);
        MasjidAdapter.MostViewedViewHolder viewHolder = new MasjidAdapter.MostViewedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MasjidAdapter.MostViewedViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.image.setImageResource(location.getImage());
        holder.title.setText(location.getTitle());
        holder.desc.setText(location.getDesc());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class MostViewedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView desc;

        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.mv_image);
            title = itemView.findViewById(R.id.mv_title);
            desc = itemView.findViewById(R.id.mv_desc);

        }
    }

}
