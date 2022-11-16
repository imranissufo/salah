package com.salah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.salah.R;
import com.salah.model.Location;
import com.salah.model.Timings;

import java.util.ArrayList;

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
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.timings_card, parent,false);
        TimingsViewHolder viewHolder = new TimingsViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull TimingsViewHolder holder, int position, @NonNull Timings model) {
        holder.desc.setText(model.getCode());
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
