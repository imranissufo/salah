package com.salah.user;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salah.R;
import com.salah.model.Location;
import com.salah.util.FeaturedAdapter;
import com.salah.util.MostViewedAdapter;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler, mostRecycler, categoryRecycler;
    RecyclerView.Adapter featuredAdapter, mostAdapter, categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.ud_featured_recycler);
        mostRecycler = findViewById(R.id.ud_most_recycler);
        //categoryRecycler = findViewById(R.id.ud_category_recycler);

        recyclers();
    }

    private void recyclers() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.drawable.dua, "dua", "dua desc"));
        locations.add(new Location(R.drawable.hands, "hands", "hands desc"));
        locations.add(new Location(R.drawable.sujud, "sujud", "sujud desc"));

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        featuredAdapter = new FeaturedAdapter(locations);
        featuredRecycler.setAdapter(featuredAdapter);

        mostRecycler.setHasFixedSize(true);
        mostRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mostAdapter = new MostViewedAdapter(locations);
        mostRecycler.setAdapter(mostAdapter);

        //GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFFEFF400,0xFFAFF600});
    }
}