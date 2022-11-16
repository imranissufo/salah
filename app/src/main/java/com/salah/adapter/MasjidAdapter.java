package com.salah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;

import java.util.ArrayList;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MasjidViewHolder> {

    ArrayList<Location> locations;

    public MasjidAdapter(ArrayList<Location> locations) {
        this.locations = locations;
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
        Location location = locations.get(position);
        holder.name.setText(location.getTitle());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class MasjidViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public MasjidViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.mj_name);

        }
    }

}
