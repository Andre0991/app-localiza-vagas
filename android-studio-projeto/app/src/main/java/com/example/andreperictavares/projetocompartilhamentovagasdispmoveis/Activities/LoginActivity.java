package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // TODO: terminar
    public void login(View view) {
        String str = "Ainda não implementei. Hue.";
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }
}
