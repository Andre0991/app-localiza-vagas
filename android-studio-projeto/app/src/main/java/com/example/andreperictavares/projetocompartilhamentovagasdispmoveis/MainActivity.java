package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String SHPR_NAME="SH_PREFS_PRKG_APP";

//    private static boolean IS_REGISTERED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!this.userIsRegistered()){
            goToRegisterScreen();
            MainActivity.this.finish();
        }
        else {
            // go to main menu
            this.goToMainScreen();
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

    public boolean userIsRegistered(){
        SharedPreferences shPref = getSharedPreferences(MainActivity.SHPR_NAME, MODE_PRIVATE);
        return shPref.contains("userName");
    }

}
