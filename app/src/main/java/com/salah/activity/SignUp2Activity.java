package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.R;
import com.salah.common.Login;
import com.salah.common.SignUpStep2;
import com.salah.common.SignUpStep3;
import com.salah.model.User;

import java.util.Calendar;

public class SignUp2Activity extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next;
    TextView titleText, slideText;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2);

        user = (User) getIntent().getSerializableExtra("user");

        //Hooks
        backBtn = findViewById(R.id.signup2_back_button);
        next = findViewById(R.id.signup2_next_button);
        titleText = findViewById(R.id.signup2_title_text);
        slideText = findViewById(R.id.signup2_slide_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Por favor selecione um gênero", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if (isAgeValid < 5) {
            Toast.makeText(this, "Você não está qualificado para se inscrever", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void next(View view) {

        if (!validateGender() | !validateAge()) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), SignUpStep3.class);

        String _date = datePicker.getYear()+"."+datePicker.getMonth()+"."+datePicker.getDayOfMonth();
        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();

        user.setGender(_gender);
        user.setDate(_date);
        intent.putExtra("user", user);

        //Add Transition and call next activity
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[3] = new Pair<View, String>(slideText, "transition_slide_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2Activity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        SignUp2Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}