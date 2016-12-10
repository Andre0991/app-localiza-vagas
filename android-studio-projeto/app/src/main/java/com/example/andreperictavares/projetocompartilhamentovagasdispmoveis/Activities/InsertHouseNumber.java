package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Calcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.CalcadaServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONObject;

public class InsertHouseNumber extends AppCompatActivity {

    protected EditText editTxtAddrNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_house_number);
        editTxtAddrNo    = (EditText) findViewById(R.id.editTxtAddrNo);
    }

    public void sendParkingLocation(View view) {
        Calcada calcada = createCalcadaFromBundleAndGivenNumber();
        CalcadaServices.addCalcada(calcada, this, new VolleyJsonOBJCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                // TODO: retirar (debug)
                String sucesso = "Calçada adicionada com sucesso!";
                Toast.makeText(InsertHouseNumber.this, sucesso, Toast.LENGTH_LONG).show();
                goToMainMenu();
            }
            @Override
            public void onErrorResponse(String result) {
                // TODO: retirar (debug)
                Toast.makeText(InsertHouseNumber.this, result, Toast.LENGTH_LONG).show();
            }
        });
    }

    private Calcada createCalcadaFromBundleAndGivenNumber(){
        Bundle extras = getIntent().getExtras();
        String postcode = extras.getString("postcode");
        String road = extras.getString("road");
        Double latitude = extras.getDouble("latitude");
        Double longitude = extras.getDouble("longitude");
        int number = Integer.parseInt(editTxtAddrNo.getText().toString());
        return new Calcada(number, postcode, road, latitude, longitude, User.getCurrentUser(this));
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }
}
