package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        String txtUsername  = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        String txtPassword  = ((EditText)findViewById(R.id.txtName)).getText().toString();
        String txtName  = ((EditText)findViewById(R.id.txtName)).getText().toString();
        String txtSurname = ((EditText)findViewById(R.id.txtSobrenome)).getText().toString();;
        String txtEmail = ((EditText)findViewById(R.id.txtEmail)).getText().toString();;

        User user = new User(txtUsername, txtPassword, txtName, txtSurname, txtEmail);
        UserServices.addUser(user, txtPassword, this);
        // TODO: descomentar após teste!
//        SharedPreferencesUtils.setUsername(this, txtName);
    }

}
