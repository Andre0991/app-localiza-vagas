package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

import android.content.SharedPreferences;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.MainActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre.peric.tavares on 11/10/2016.
 */

public class User {

    String username;
    String password;
    String name;
    String surname;
    String email;

    public User(String username, String password, String name, String surname, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
