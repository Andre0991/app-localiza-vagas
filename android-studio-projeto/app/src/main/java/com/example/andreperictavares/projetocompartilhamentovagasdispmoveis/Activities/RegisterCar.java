package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Car;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Placa;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.CarServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.ValidationUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterCar extends AppCompatActivity {

    EditText edtTxtPlateNumbers;
    EditText edtTxtPlateLetters;
    EditText edtTxtModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        edtTxtPlateNumbers = (EditText)findViewById(R.id.edtTxtPlateNumbers);
        edtTxtPlateLetters = (EditText)findViewById(R.id.edtTxtPlateLetters);
        edtTxtModel = (EditText)findViewById(R.id.edtTxtModel);

        addListeners();
    }

    public void register(View view) {
        final String txtPlateNumbers  = edtTxtPlateNumbers.getText().toString();
        final String txtPlateLetters  = edtTxtPlateLetters.getText().toString();
        final String txtModel  = edtTxtModel.getText().toString();

        List<EditText> allEditTxtsFromView = new ArrayList<>(Arrays.asList(
                edtTxtPlateNumbers,
                edtTxtPlateLetters,
                edtTxtModel));

        if (!validate(allEditTxtsFromView)) {
            return;
        }

        try {
            Placa placa = new Placa(txtPlateNumbers + txtPlateLetters);
            // TODO: colour picker
            Car car = new Car(placa, null, txtModel);
            CarServices.addCar(car, this, new VolleyJsonOBJCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                }

                @Override
                public void onErrorResponse(String result) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    // TODO: refactor to static method (in another class)
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

    private void addListeners() {
        edtTxtPlateNumbers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Placa.sequenceOfDigitsHasCorrectLength(s.toString())) {
                    edtTxtPlateNumbers.setError("Número de dígitos inválido. Insira " + Placa.CORECT_NUMBER_OF_DIGITS_IN_PLATE + " dígitos");
                }
            }
        });

                edtTxtPlateLetters.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Placa.sequenceOfLettersHasCorrectLength(s.toString())) {
                    edtTxtPlateLetters.setError("Número de letras inválido. Insira " + Placa.CORECT_NUMBER_OF_LETTERS_IN_PLATE + " dígitos");
                }
            }
        });
    }
}
