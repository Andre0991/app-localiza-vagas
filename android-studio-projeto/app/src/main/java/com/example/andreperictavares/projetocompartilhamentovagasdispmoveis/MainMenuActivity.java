package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView wellcomeText = (TextView)findViewById(R.id.wellcome);
        wellcomeText.setText("Seja bem vindo, ");
    }
}
