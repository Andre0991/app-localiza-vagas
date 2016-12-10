package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.DisponibilidadeCalcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Horario;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleParkingLocation extends AppCompatActivity {

    // OBJETIVO: Criar lista de DisponibilidadeCalcada que representa os horários
    // em que o usuário tem a calçada disponível para outros estacionarem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_parking_location);

        try {
            // criar a partir de interface de calendário ou algo do tipo
            DisponibilidadeCalcada disponibilidade1 =  new DisponibilidadeCalcada(DisponibilidadeCalcada.Day.MONDAY, new Horario(14, 0), new Horario(15, 0));
            DisponibilidadeCalcada disponibilidade2 =  new DisponibilidadeCalcada(DisponibilidadeCalcada.Day.MONDAY, new Horario(16, 30), new Horario(17, 00));
            List<DisponibilidadeCalcada> disponibilidades = new ArrayList<>();
            disponibilidades.add(disponibilidade1);
            disponibilidades.add(disponibilidade2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
