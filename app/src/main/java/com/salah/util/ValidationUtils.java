package com.salah.util;

import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.model.Masjid;

import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static Pattern numPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static Pattern emailPattern = Pattern.compile("^(.+)@(\\S+)$");
    public static Pattern passwordPattern = Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");

    public static boolean validateField(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            textInputLayout.setError("Campo obrigatório!");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validateField(TextInputLayout textInputLayout, int min, int max) {
        String val = textInputLayout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            textInputLayout.setError("Campo obrigatório!");
            return false;
        } else if (val.length() < min) {
            textInputLayout.setError("Tamanho mínimo é " + min + " caracteres!");
            return false;
        } else if (val.length() > max) {
            textInputLayout.setError("Tamanho máximo é " + max + " caracteres!");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validateEmail(TextInputLayout email) {
        String val = email.getEditText().getText().toString().trim();
        //String regexPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        //String regexPattern = "^(.+)@(\\S+)$";
        if (val.isEmpty()) {
            email.setError("O campo não pode estar vazio");
            return false;
        } else if (!isValidEmail(val)) {
            email.setError("Email invalido!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean validateHour(Context context, int min, int max, TimePicker timePicker) {
        int hour = timePicker.getHour();

        if (hour >= min && hour <= max) {
            return true;
        }
        Toast.makeText(context, "A hora deve estar entre " + min + " e " + max + "!", Toast.LENGTH_LONG).show();
        return false;
    }

    public static boolean validatePassword(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            textInputLayout.setError("Campo obrigatório!");
            return false;
        } else if (!isValidPassword(val)) {
            textInputLayout.setError("Senha invalida!");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean isValidEmail(String emailAddress) {
        if (emailAddress == null) {
            return false;
        }
        return emailPattern
                .matcher(emailAddress)
                .matches();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return numPattern.matcher(strNum).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return passwordPattern
                .matcher(password)
                .matches();
    }

    public static boolean contains(String name, List<Masjid> masjids) {
        if (name != null && !name.isEmpty() && masjids != null && !masjids.isEmpty()) {
            for (Masjid m : masjids) {
                if (m.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static boolean validateFieldContains(TextInputLayout textInputLayout, List<Masjid> masjids) {
        String val = textInputLayout.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            textInputLayout.setError("Campo obrigatório!");
            return false;
        } else if(contains(val, masjids)){
            textInputLayout.setError("Nome existente!");
            return false;
        }else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }
}

