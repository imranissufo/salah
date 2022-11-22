package com.salah.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasgidAdapter;
import com.salah.model.Masjid;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailerFeaturedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailerFeaturedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView masjidRecycler;
    MasgidAdapter masjidAdapter;
    int height, width;
    TextInputEditText searchInput;
    String searchTxt;
    private ArrayList<Masjid> entries;
    private DatabaseReference databaseReference;
    private DatabaseReference masjidReference;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RetailerFeaturedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailerFeaturedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailerFeaturedFragment newInstance(String param1, String param2) {
        RetailerFeaturedFragment fragment = new RetailerFeaturedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_featured, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadEntries("");


        searchInput = view.findViewById(R.id.mjf_search_editText);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTxt = charSequence.toString();
                loadEntries(searchTxt);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        masjidRecycler = view.findViewById(R.id.mjf_rv);
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height;
        masjidRecycler.setLayoutParams(params);

        masjidAdapter = new MasgidAdapter(entries);
        masjidRecycler.setAdapter(masjidAdapter);

        return view;
    }

    private void loadEntries(String search) {
        entries = new ArrayList<Masjid>();
        masjidReference = databaseReference.child("masjid");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
                    if (search.isEmpty() || search.toUpperCase().contains(masjid.getName())) {
                        entries.add(masjid);
                        masjidAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };
        masjidReference.addValueEventListener(valueEventListener);
    }

}