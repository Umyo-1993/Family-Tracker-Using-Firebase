package com.example.demofirebase;

import com.google.android.gms.maps.model.LatLng;

public class Userdata {
    String username,password,deviceid;



    public Userdata()
    {

    }

    public Userdata(String username, String password, String deviceid) {
        this.username = username;
        this.password = password;
        this.deviceid = deviceid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
