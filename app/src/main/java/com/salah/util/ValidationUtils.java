package com.salah.util;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationUtils {

    public static boolean validateEmail(TextInputLayout email) {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("O campo não pode estar vazio");
            return false;
//        } else if (!val.matches(checkEmail)) {
//            email.setError("Invalid Email!");
//            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
}
