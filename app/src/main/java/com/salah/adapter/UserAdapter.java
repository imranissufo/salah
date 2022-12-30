package com.salah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.activity.MasjidAnnc1Activity;
import com.salah.activity.MasjidDeleteActivity;
import com.salah.activity.MasjidForm1Activity;
import com.salah.activity.UserBlockActivity;
import com.salah.activity.UserDeleteActivity;
import com.salah.activity.UserFormActivity;
import com.salah.model.Masjid;
import com.salah.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> locations) {
        this.context = context;
        this.users = locations;
    }

    public void setMasjids(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User model = users.get(position);

        holder.fullName.setText(model.getFullName());
        holder.email.setText(model.getEmail());
        holder.phoneNo.setText(model.getPhoneNo());

        holder.editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserFormActivity.class);
                intent.putExtra("user", model);
                intent.putExtra("action", "EDIT");
                context.startActivity(intent);
            }
        });

        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserBlockActivity.class);
                intent.putExtra("user", model);
                intent.putExtra("action", "BLOCK");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<User> entries) {
        this.users = entries;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        //TextInputLayout fullName, username, email, phoneNo, password, date, gender;
        //ImageView editUser;
        //ImageView deleteUser;
        TextView fullName, email, phoneNo;
        MaterialButton editUser;
        MaterialButton deleteUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.user_fullname);
            email = itemView.findViewById(R.id.user_email);
            phoneNo = itemView.findViewById(R.id.user_phone);

            editUser = itemView.findViewById(R.id.user_edit);
            deleteUser = itemView.findViewById(R.id.user_delete);
        }
    }
}
