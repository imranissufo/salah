package com.salah.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;

import java.util.ArrayList;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    ArrayList<Location> locations;

    public CategoryAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_design, parent,false);
        CategoryAdapter.CategoryViewHolder viewHolder = new CategoryAdapter.CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.image.setImageResource(location.getImage());
        holder.title.setText(location.getTitle());
        holder.layout.setBackground(location.getGradient());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        RelativeLayout layout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ct_image);
            title = itemView.findViewById(R.id.ct_title);
            layout = itemView.findViewById(R.id.ct_background);
        }
    }
}
