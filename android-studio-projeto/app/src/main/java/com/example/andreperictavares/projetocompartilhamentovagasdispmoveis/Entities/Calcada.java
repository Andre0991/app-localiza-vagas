package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

/**
 * Created by andreperictavares on 27/11/2016.
 */

public class Calcada {
    int numero;
    String cep;
    String rua;
    Double latitude;
    Double longitude;
    User user;

    public int getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public User getUser() {
        return user;
    }

    public Calcada(int numero, String cep, String rua, Double latitude, Double longitude, User user) {
        this.numero = numero;
        this.cep = cep;
        this.rua = rua;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Calcada{" +
                "numero=" + numero +
                ", cep='" + cep + '\'' +
                ", rua='" + rua + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", user=" + user +
                '}';
    }
}
