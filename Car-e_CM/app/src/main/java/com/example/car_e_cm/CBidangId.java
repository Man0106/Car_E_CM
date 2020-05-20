package com.example.car_e_cm;

import androidx.annotation.NonNull;

public class CBidangId {
    String bidangId;
    public <T extends CBidangId>T withId(@NonNull final String id){
        this.bidangId = id;
        return (T) this;
    }
}
