package com.salah.adapter;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.activity.MasjidDeleteActivity;
import com.salah.activity.MasjidForm1Activity;
import com.salah.model.User;

import java.util.ArrayList;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> users;

    public UserCardAdapter(Context context, ArrayList<User> locations) {
        this.context = context;
        this.users = locations;
    }

    public void setMasjids(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cardview, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User model = users.get(position);
        holder.fullNameTextView.setText(model.getFullName());
        /*
        holder.fullName.getEditText().setText(model.getFullName());
        holder.username.getEditText().setText(model.getUsername());
        holder.email.getEditText().setText(model.getEmail());
        holder.phoneNo.getEditText().setText(model.getPhoneNo());
        holder.date.getEditText().setText(model.getDate());
        holder.gender.getEditText().setText(model.getGender());

        holder.editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasjidForm1Activity.class);
                intent.putExtra("masjid", model);
                intent.putExtra("action", "EDIT");
                context.startActivity(intent);
            }
        });

        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MasjidDeleteActivity.class);
                intent.putExtra("masjid", model);
                intent.putExtra("action", "DELETE");
                context.startActivity(intent);
            }
        });

         */

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<User> entries) {
        this.users = entries;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextInputLayout fullName, username, email, phoneNo, password, date, gender;
        ImageView editUser;
        ImageView deleteUser;

        CardView cardView;
        ImageView arrow;
        Group hiddenGroup;
        TextView fullNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.base_cardview);
            arrow = itemView.findViewById(R.id.show);
            hiddenGroup = itemView.findViewById(R.id.card_group);
            fullNameTextView = itemView.findViewById(R.id.textView14);

            /*
            fullName = itemView.findViewById(R.id.user_fullname);
            username = itemView.findViewById(R.id.user_username);
            email = itemView.findViewById(R.id.user_email);
            phoneNo = itemView.findViewById(R.id.user_phone);
            date = itemView.findViewById(R.id.user_date);
            gender = itemView.findViewById(R.id.user_gender);
             */

            //editUser = itemView.findViewById(R.id.user_edit);
            //deleteUser = itemView.findViewById(R.id.user_delete);

            arrow.setOnClickListener(view -> {
                if(hiddenGroup.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    hiddenGroup.setVisibility(View.GONE);
                    arrow.setImageResource(android.R.drawable.arrow_down_float);
                }
                else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    hiddenGroup.setVisibility(View.VISIBLE);
                    arrow.setImageResource(android.R.drawable.arrow_up_float);
                }
            });
        }
    }
}
