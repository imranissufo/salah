package com.salah.user;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;
import com.salah.util.CategoryAdapter;
import com.salah.util.FeaturedAdapter;
import com.salah.util.MostViewedAdapter;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler, mostViewedRecycler, categoryRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.ud_featured_recycler);
        mostViewedRecycler = findViewById(R.id.ud_most_recycler);
        categoryRecycler = findViewById(R.id.ud_category_recycler);

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(gradient1, R.drawable.ic_hotel, "Hotel"));
        locations.add(new Location(gradient3, R.drawable.ic_restaurant, "Restaurant"));
        locations.add(new Location(gradient4, R.drawable.ic_shop, "Shopping"));
        locations.add(new Location(gradient1, R.drawable.ic_airport, "Transport"));
        categoryRecycler.setHasFixedSize(true);

        adapter = new CategoryAdapter(locations);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecycler.setAdapter(adapter);

    }

    private void featuredRecycler() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.drawable.dua, "dua", "dua desc"));
        locations.add(new Location(R.drawable.hands, "hands", "hands desc"));
        locations.add(new Location(R.drawable.sujud, "sujud", "sujud desc"));

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new FeaturedAdapter(locations);
        featuredRecycler.setAdapter(adapter);

        /*
        mostRecycler.setHasFixedSize(true);
        mostRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mostAdapter = new MostViewedAdapter(locations);
        mostRecycler.setAdapter(mostAdapter);
        */

        //GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFFEFF400,0xFFAFF600});
    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Location> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new Location(R.drawable.sujud, "Sujud", "Sajdah"));
        mostViewedLocations.add(new Location(R.drawable.dua, "Salah", "Salah"));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }


}