package com.example.demofirebase;

import com.google.android.gms.maps.model.LatLng;

public class Userlatlng {

    public Userlatlng()
    {

    }
    Double latitude,longitude;

    public Userlatlng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
