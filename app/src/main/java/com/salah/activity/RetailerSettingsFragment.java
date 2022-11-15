package com.salah.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.model.User;
import com.salah.util.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetailerSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetailerSettingsFragment extends Fragment {

    TextInputLayout fullname, username, email, phone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RetailerSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetailerSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetailerSettingsFragment newInstance(String param1, String param2) {
        RetailerSettingsFragment fragment = new RetailerSettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_retailer_settings, container, false);

        fullname = view.findViewById(R.id.rd_fullname);
        username = view.findViewById(R.id.rd_username);
        email = view.findViewById(R.id.rd_email);
        phone = view.findViewById(R.id.rd_phone);

        SharedPreferencesManager manager = new SharedPreferencesManager(getContext());
        User user = manager.getUser();

        fullname.getEditText().setText(user.getFullName());
        username.getEditText().setText(user.getUsername());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhoneNo());

        return view;
    }

    public void onBackBtnClick(View view) {

    }
}