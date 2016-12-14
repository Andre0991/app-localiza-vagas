package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Fragments.DatePickerFragment;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

public class MainMenuActivity extends AppCompatActivity {

    Button registerParkingLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.registerParkingLocationButton = (Button) findViewById(R.id.registerParkingLocation);

//        TextView wellcomeText = (TextView)findViewById(R.id.wellcome);
//        wellcomeText.setText("Seja bem vindo, " + SharedPreferencesUtils.getFirstName(this));
    }

    public void registerParkingLocation(View view){
        Intent intent = new Intent(this, GuessParkingLocationActivity.class);
        startActivity(intent);
    }

    public void goToCalcadaHorarioActivity(View view){
        Intent intent = new Intent(this, AddScheduleParkingLocation.class);
        startActivity(intent);

    }

    public void goToSearchFreeParkingLocationActivity(View view) {
        Intent intent = new Intent(this, SearchFreeParkingLocationActivity.class);
        startActivity(intent);
    }
}
