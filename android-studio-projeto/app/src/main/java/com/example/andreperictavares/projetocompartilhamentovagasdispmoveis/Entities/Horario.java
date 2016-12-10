package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

/**
 * Created by andreperictavares on 10/12/2016.
 */

public class Horario {
    /* Deve pertencer a [0,23] */
    int hour;
    /* Deve pertencer a [0,59] */
    int minute;

    public Horario(int hour, int minute) throws Exception {
        setHour(hour);
        setMinute(minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    private void setHour(int hour) throws Exception {
        if (hour < 0 || minute > 23) {
            throw new Exception("hora não pode ser menor do que zero ou maior do que 23");
        }
        this.hour = hour;
    }

    private void setMinute(int minute) throws Exception {
        if (minute < 0 || minute > 59) {
            throw new Exception("Minuto não pode ser menor do que zero ou maior do que 59");
        }
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
