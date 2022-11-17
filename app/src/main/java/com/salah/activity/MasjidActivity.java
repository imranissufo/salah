package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.salah.R;
import com.salah.adapter.MasjidAdapter;
import com.salah.adapter.TimingsAdapter;
import com.salah.model.Masjid;
import com.salah.model.Timings;

public class MasjidActivity extends AppCompatActivity {

    RecyclerView masjidRecycler;
    MasjidAdapter masjidAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masjid);

        masjidRecycler = findViewById(R.id.mj_rv);
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter = new MasjidAdapter(options);
        masjidRecycler.setAdapter(masjidAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        masjidAdapter.startListening();

        //Remove crash on press back
        masjidRecycler.getRecycledViewPool().clear();

        masjidAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        masjidAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override

            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void txtSearch(String str) {
        //fetch data from current day until the last day of the month
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid").orderByChild("name")
                .startAt(str).endAt(str + "~");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter = new MasjidAdapter(options);
        masjidAdapter.startListening();
        masjidRecycler.setAdapter(masjidAdapter);
    }
}