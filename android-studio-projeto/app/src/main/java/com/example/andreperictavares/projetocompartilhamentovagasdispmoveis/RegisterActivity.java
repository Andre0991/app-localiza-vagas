package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void register(View view){
        String txtName  = ((EditText)findViewById(R.id.txtName)).getText().toString();
        String txtPhone = ((EditText)findViewById(R.id.txtPhone)).getText().toString();;
        String txtEmail = ((EditText)findViewById(R.id.txtEmail)).getText().toString();;

        SharedPreferencesUtils.setUsername(this, txtName);
//        SharedPreferences shPref = getSharedPreferences(SharedPreferencesUtils.SHPR_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = shPref.edit();
//        editor.putString("userName", txtName.toString());
//        editor.putString("userPhome", txtPhone.toString());
//        editor.putString("userEmail", txtEmail.toString());
//        editor.commit();
    }

}
