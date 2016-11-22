package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView wellcomeText = (TextView)findViewById(R.id.wellcome);
        wellcomeText.setText("Seja bem vindo, " + SharedPreferencesUtils.getFirstName(this));
    }
}
