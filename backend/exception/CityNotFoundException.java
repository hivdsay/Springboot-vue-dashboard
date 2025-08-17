package com.example.dashboard_api.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message){
        super(message); //Hata mesajını üst sınıfa gönderir. RuntimeException'ın message ksımını ayarlıyoruz.
    }

}
