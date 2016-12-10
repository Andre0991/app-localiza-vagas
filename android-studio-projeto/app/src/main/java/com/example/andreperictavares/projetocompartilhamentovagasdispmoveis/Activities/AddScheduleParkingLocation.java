package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.DisponibilidadeCalcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Horario;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.DisponibilidadeServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleParkingLocation extends AppCompatActivity {

    // OBJETIVO: Criar lista de DisponibilidadeCalcada que representa os horários
    // em que o usuário tem a calçada disponível para outros estacionarem
    List<DisponibilidadeCalcada> disponibilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_parking_location);

        try {
            // criar a partir de interface de calendário ou algo do tipo
            disponibilidades = new ArrayList<>();
            DisponibilidadeCalcada disponibilidade2 =  new DisponibilidadeCalcada(DisponibilidadeCalcada.Day.MONDAY, new Horario(16, 30), new Horario(17, 0));
//            DisponibilidadeCalcada disponibilidade1 =  new DisponibilidadeCalcada(DisponibilidadeCalcada.Day.MONDAY, new Horario(14, 0), new Horario(15, 0));
//            disponibilidades.add(disponibilidade1);
            disponibilidades.add(disponibilidade2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarRequisicaoTest(View view) {
        for (DisponibilidadeCalcada disp : disponibilidades) {
            DisponibilidadeServices.addDisponibilidade(disp, this, new VolleyJsonOBJCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    String sucesso = "Calçada adicionada com sucesso!";
                    Toast.makeText(AddScheduleParkingLocation.this, sucesso, Toast.LENGTH_LONG).show();
                }
                @Override
                public void onErrorResponse(String result) {
                    Toast.makeText(AddScheduleParkingLocation.this, result, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
