package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

/**
 * Created by andreperictavares on 10/12/2016.
 */

public class DisponibilidadeCalcada {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    Day day;
    Horario inicio;
    Horario fim;

    public DisponibilidadeCalcada(Day day, Horario incio, Horario fim) {
        this.day = day;
        this.inicio = incio;
        this.fim = fim;
    }
}
