package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyCallback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        final String txtUsername  = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        final String txtPassword  = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        final String txtName  = ((EditText)findViewById(R.id.txtName)).getText().toString();
        final String txtSurname = ((EditText)findViewById(R.id.txtSobrenome)).getText().toString();;
        final String txtEmail = ((EditText)findViewById(R.id.txtEmail)).getText().toString();;

        User user = new User(txtUsername, txtPassword, txtName, txtSurname, txtEmail);
        UserServices.addUser(user, txtPassword, this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                SharedPreferencesUtils.setUsername(RegisterActivity.this, txtName);
                SharedPreferencesUtils.setPassword(RegisterActivity.this, txtPassword);
                Intent intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

}
