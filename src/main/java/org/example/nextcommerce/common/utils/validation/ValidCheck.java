package org.example.nextcommerce.common.utils.validation;

import java.util.regex.Pattern;

public class ValidCheck {
    public static boolean isEmailValidation(String email){
        return Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]{2,12})@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,12}[.][a-zA-Z]{2,3}$", email);
    }

    public static boolean isPasswordValidation(String password){
        return Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", password);
    }

}
