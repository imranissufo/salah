package com.salah.service;

import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.model.Masjid;

import java.util.ArrayList;
import java.util.List;

public class MasjidService {

    private static MasjidService instance;

    FirebaseDatabase database;
    DatabaseReference ref;
    List<Masjid> masjids;

    private MasjidService(){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("masjid");
    }

    public static MasjidService getInstance(){
        if(instance == null){
            instance = new MasjidService();
        }
        return instance;
    }

    public List<Masjid> findAll(String search){
        masjids = new ArrayList<Masjid>();
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
                    masjid.setId(ds.getKey());
                    if (search.isEmpty() || masjid.getName().toUpperCase().contains(search.toUpperCase())) {
                        masjids.add(masjid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return masjids;
    }
}
