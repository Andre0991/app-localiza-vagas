package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences shPref = getSharedPreferences(MainActivity.SHPR_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = shPref.edit();
        editor.putInt("escalaTexto", 10);
        editor.commit();
    }

    public void register(View view){
        // referencio os componentes visuais
        EditText txtId = (EditText)findViewById(R.id.txtId);
        EditText txtNo = (EditText)findViewById(R.id.txtNome);
        EditText txtTe = (EditText)findViewById(R.id.txtTelefone);
        EditText txtEm = (EditText)findViewById(R.id.txtEmail);


    }

}
