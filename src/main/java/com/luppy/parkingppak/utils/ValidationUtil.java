package com.luppy.parkingppak.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    public Boolean validate_email(String email){
        String email_validation_regx = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern email_pattern = Pattern.compile(email_validation_regx);
        Matcher email_matcher = email_pattern.matcher(email);
        return email_matcher.matches();
    }

    public Boolean validate_password(String password){
        String password_validation_regx = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W))(?=.*[!@#$%^*+=-]).{8,16}$";
        Pattern password_pattern = Pattern.compile(password_validation_regx);
        Matcher password_matcher = password_pattern.matcher(password);
        return password_matcher.matches();
    }
}
