package com.example.car_e_cm;

import androidx.annotation.NonNull;

public class BidangId {
    String bidangId;
    public <T extends BidangId>T withId(@NonNull final String id){
        this.bidangId = id;
        return (T) this;
    }
}
