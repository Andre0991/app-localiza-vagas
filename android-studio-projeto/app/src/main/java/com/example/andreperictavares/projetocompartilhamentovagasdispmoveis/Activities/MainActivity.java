package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.CalcadaServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!SharedPreferencesUtils.hasUserRegistered(this)){
            goToRegisterScreen();
            MainActivity.this.finish();
        }
        else {
            User currentUser = User.getCurrentUser(this);
            UserServices.authUser(currentUser, this, new VolleyJsonOBJCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        String token = result.getString("token");
                        SharedPreferencesUtils.setToken(MainActivity.this, token);
                        // TODO: deletar (teste)
                        Calcada calcada = new Calcada(12, "3887777", "rua blau3", 123.4, 345.5, User.getCurrentUser(MainActivity.this));
                        CalcadaServices.addCalcada(calcada, MainActivity.this, new VolleyJsonOBJCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject result) {
                                Toast.makeText(MainActivity.this, "Adicionou calcada com sucesso!", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onErrorResponse(String errorMsg) {
                                if (errorMsg != null){
                                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        // fim teste
                        Intent intent = new Intent(MainActivity.this, GuessParkingLocationActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onErrorResponse(String errorMsg) {
                    if (errorMsg != null){
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
            });
//            goToMainScreen();
//            Intent intent = new Intent(this, LocationActivity.class);
        }
    }

    public void goToRegisterScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
