package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editTxtUsername;
    EditText editTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTxtUsername = (EditText)findViewById(R.id.edtTxtLoginUsername);
        editTxtPassword = (EditText)findViewById(R.id.edtTxtLoginPassword);
    }

    // TODO: terminar
    public void login(View view) {
        final String txtUsername  = editTxtUsername.getText().toString();
        final String txtPassword  = editTxtPassword.getText().toString();

        List<EditText> allEditTxtsFromView = new ArrayList<>(Arrays.asList(
                editTxtUsername,
                editTxtPassword));

        if (!validate(allEditTxtsFromView)) {
            return;
        }

        User user = new User(txtUsername, txtPassword);

        UserServices.authUser(user, this, new VolleyJsonOBJCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                try {
                    SharedPreferencesUtils.setToken(LoginActivity.this, result.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UserServices.getOne(LoginActivity.this, txtUsername, new VolleyJsonOBJCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            JSONObject userData = result.getJSONObject("data");
                            String jsonStr = userData.toString();
                            Gson gson = new Gson();
                            User userObj = gson.fromJson(jsonStr, User.class);
                            userObj.setPassword(txtPassword);
                            SharedPreferencesUtils.saveUser(userObj, LoginActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String success = getString(R.string.success_login);
                        Toast.makeText(LoginActivity.this, success, Toast.LENGTH_LONG).show();
                        goToMainMenuActivity();
                    }

                    @Override
                    public void onErrorResponse(String result) {
                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });

            }
            @Override
            public void onErrorResponse(String result) {
                Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goToRegisterActivity(View view) {
        goToRegisterActivity();
    }

    private void goToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goToMainMenuActivity(View view) {
        goToMainMenuActivity();
    }

    private void goToMainMenuActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }

    private boolean validate(List<EditText> allTxtEdits){
        boolean valid = true;
        for (EditText edtTxt: allTxtEdits) {
            if (edtTxt.getError() != null){
                edtTxt.setError(edtTxt.getError());
                valid = false;
            }
            if (TextUtils.isEmpty(edtTxt.getText().toString())){
                edtTxt.setError(getString(R.string.message_field_not_empty));
                valid = false;
            }
        }
        return valid;
    }
}
