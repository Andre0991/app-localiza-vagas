package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

import android.graphics.Color;

/**
 * Created by andreperictavares on 14/12/2016.
 */

public class Car {
    Placa placa;
    Color c;
    String modelo;

    public Car(Placa placa, Color color, String modelo) {
        this.placa = placa;
        this.c = color;
        this.modelo = modelo;
    }
}
