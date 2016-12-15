package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.DisponibilidadeCalcada;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Horario;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Fragments.DatePickerFragment;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.DisponibilidadeServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddScheduleParkingLocation extends FragmentActivity implements DatePickerFragment.OnHeadlineSelectedListener {

    // OBJETIVO: Criar lista de DisponibilidadeCalcada que representa os horários
    // em que o usuário tem a calçada disponível para outros estacionarem
    List<DisponibilidadeCalcada> disponibilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_parking_location);

//        Fragment fragment = new DatePickerFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.fragmentTest, fragment, DatePickerFragment.TIMERANGEPICKER_TAG);
//        transaction.addToBackStack(null);
//        transaction.commit();

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
    }

    private void sendRequestCalcada(DisponibilidadeCalcada disp) {
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

    @Override
    public void onArticleSelected(Horario horarioInicio, Horario horarioFim) {
        // TODO: Tirar segunda hardcoded
        DisponibilidadeCalcada disponibilidade = new DisponibilidadeCalcada(DisponibilidadeCalcada.Day.MONDAY, horarioInicio, horarioFim);
        sendRequestCalcada(disponibilidade);
    }
}
