package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

/**
 * Created by andreperictavares on 20/11/2016.
 */

public class ParkingLocation {

    private int number;
    private String road;
    private String suburb;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private User user;

    public ParkingLocation(int number, String road, String suburb, String city, String state, Double latitude, Double longitude, User user) {
        this.number = number;
        this.road = road;
        this.suburb = suburb;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ParkingLocation{" +
                "number='" + number + '\'' +
                ", road='" + road + '\'' +
                ", suburb='" + suburb + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", user=" + user +
                '}';
    }
}
