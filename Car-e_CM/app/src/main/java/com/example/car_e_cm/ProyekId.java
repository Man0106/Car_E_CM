package com.example.car_e_cm;

import androidx.annotation.NonNull;

public class ProyekId {
    String proyekId;
    public <T extends ProyekId>T withId(@NonNull final String id){
        this.proyekId = id;
        return (T) this;
    }
}
