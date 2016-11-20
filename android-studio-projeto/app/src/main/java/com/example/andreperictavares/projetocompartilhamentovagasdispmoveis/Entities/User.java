package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

import android.content.Context;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

/**
 * Created by andre.peric.tavares on 11/10/2016.
 */

public class User {

    private String username;
    private String password;
    private String first_name;
    private String surname;
    private String email;

    public User(String username, String password, String name, String surname, String email) {
        this.username = username;
        this.password = password;
        this.first_name = name;
        this.surname = surname;
        this.email = email;
    }

    public static User getCurrentUser(Context ctx){
        return new User(SharedPreferencesUtils.getUsername(ctx),
                SharedPreferencesUtils.getPassword(ctx),
                SharedPreferencesUtils.getFirstName(ctx),
                SharedPreferencesUtils.getSurname(ctx),
                SharedPreferencesUtils.getEmail(ctx));
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
