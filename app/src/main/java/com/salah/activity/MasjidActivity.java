package com.salah.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.salah.R;
import com.salah.adapter.MasjidAdapter;
import com.salah.model.Masjid;

import java.util.Locale;

public class MasjidActivity extends AppCompatActivity {

    RecyclerView masjidRecycler;
    MasjidAdapter masjidAdapter;
    int height, width;
    TextInputEditText searchInput;
    String searchTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid);

        searchInput = findViewById(R.id.mj_search_editText);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    searchTxt = charSequence.toString();
                    txtSearch(searchTxt.toUpperCase());
                }else{
                    showAllMasjids();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        masjidRecycler = findViewById(R.id.mj_rv);
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height;
        masjidRecycler.setLayoutParams(params);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid").orderByChild("name");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter = new MasjidAdapter(options);
        masjidRecycler.setAdapter(masjidAdapter);

    }

    private void showAllMasjids() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid").orderByChild("name");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter.updateOptions(options);
        masjidAdapter.startListening();
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
        //masjidAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        masjidAdapter.stopListening();
    }

    private void txtSearch(String str) {
        //fetch data from current day until the last day of the month
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid").orderByChild("search")
                .startAt(str).endAt(str + "\\uf8ff");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter.updateOptions(options);
        masjidAdapter.startListening();
        masjidRecycler.setAdapter(masjidAdapter);
    }

    public void onBackBtnClick(View view){
        MasjidActivity.super.onBackPressed();
    }
}