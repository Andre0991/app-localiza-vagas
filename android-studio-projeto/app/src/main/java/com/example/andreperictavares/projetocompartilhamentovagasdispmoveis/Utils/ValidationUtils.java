package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andreperictavares on 19/11/2016.
 */

// Some excerpts were taken from http://stackoverflow.com/questions/33072569/best-practice-input-validation-android
public class ValidationUtils {

    public boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
