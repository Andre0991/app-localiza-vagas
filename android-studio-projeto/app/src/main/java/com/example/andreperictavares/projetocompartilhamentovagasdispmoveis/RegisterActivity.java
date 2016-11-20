/**/package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Services.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.ValidationUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyStringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText editTxtUsername;
    EditText editTxtPassword;
    EditText editTxtFirstName;
    EditText editTxtSurname;
    EditText editTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxtUsername = (EditText)findViewById(R.id.txtUsername);
        editTxtPassword = (EditText)findViewById(R.id.txtPassword);
        editTxtFirstName = (EditText)findViewById(R.id.txtName);
        editTxtSurname = (EditText)findViewById(R.id.txtSobrenome);
        editTxtEmail = (EditText)findViewById(R.id.txtEmail);

        addListeners();
    }


//    @Override
//    protected void onResume(Bundle savedInstanceState) {
//        super.onResume();
//        System.out.println("test");
//    }

    private void addListeners() {
        final ValidationUtils validationUtils = new ValidationUtils();
        editTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validationUtils.isValidEmail(s.toString())) {
                    editTxtEmail.setError("Insira um endereço de e-mail válido.");
                }
            }
        });
    }

    public void register(View view){

        final String txtUsername  = editTxtUsername.getText().toString();
        final String txtPassword  = editTxtPassword.getText().toString();
        final String txtFirstName  = editTxtFirstName.getText().toString();
        final String txtSurname = editTxtSurname.getText().toString();
        final String txtEmail = editTxtEmail.getText().toString();

        List<EditText> allEditTxtsFromView = new ArrayList<>(Arrays.asList(
                editTxtUsername,
                editTxtPassword,
                editTxtFirstName,
                editTxtSurname,
                editTxtEmail));

        if (!validate(allEditTxtsFromView)) {
            return;
        }

        User user = new User(txtUsername, txtPassword, txtFirstName, txtSurname, txtEmail);
        UserServices.addUser(user, txtPassword, this, new VolleyStringCallback() {
            @Override
            public void onSuccessResponse(String result) {
                SharedPreferencesUtils.setUsername(RegisterActivity.this, txtFirstName);
                SharedPreferencesUtils.setPassword(RegisterActivity.this, txtPassword);
                Intent intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }

            @Override
            public void onErrorResponse(String result) {

            }
        });
    }

    private boolean validate(List<EditText> allTxtEdits){
        boolean valid = true;
        for (EditText edtTxt: allTxtEdits) {
            if (edtTxt.getError() != null){
                edtTxt.setError(edtTxt.getError());
                valid = false;
            }
            if (TextUtils.isEmpty(edtTxt.getText().toString())){
                edtTxt.setError("Campo não pode ser vazio.");
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        // Não queremos perder o estado da atividade. Não faça nada.
    }

}
