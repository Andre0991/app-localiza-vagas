package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {

//    private static boolean IS_REGISTERED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!SharedPreferencesUtils.hasUserRegistered(this)){
            goToRegisterScreen();
            MainActivity.this.finish();
        }
        else {
            goToMainScreen();
        }
    }

    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToRegisterScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
