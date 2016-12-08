package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.ParkingLocation;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.CalcadaServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.ParkingLocationServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyStringCallback;

import org.json.JSONObject;

// TODO: pegar codigo do Steffan
public class InsertAddressManuallyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_address_manually);

    }

    public void addCalcada(View view){
        // TODO: retirar teste hardcoded quando código do Steffan estiver aqui
        // Adiciona calçada hardcoded (test)
        Calcada calcada = new Calcada(677, "09210300", "Av. Estados Unidos", -23.6358129, -46.529127, User.getCurrentUser(this));
        CalcadaServices.addCalcada(calcada, this, new VolleyJsonOBJCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                // TODO: retirar (debug)
                String sucesso = "Calçada adicionada com sucesso!";
                Toast.makeText(InsertAddressManuallyActivity.this, sucesso, Toast.LENGTH_LONG).show();
                goToMainMenu();
            }
            @Override
            public void onErrorResponse(String result) {
                // TODO: retirar (debug)
                Toast.makeText(InsertAddressManuallyActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }
}
