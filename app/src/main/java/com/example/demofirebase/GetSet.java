package com.example.demofirebase;

public class GetSet {
   public String username,password,datetime,location;

    public GetSet(String username, String datetime, String location) {
        this.username = username;
        this.datetime = datetime;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GetSet() {
    }

}
