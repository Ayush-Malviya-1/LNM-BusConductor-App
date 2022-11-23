package com.example.conductorapp;

import java.sql.Array;

public class model {

    String busNumber ;
    String busType ;
    String date  ;
    String destination ;
    String driverName ;
    String source ;
    String time ;
    String  totalSeats ;
    String seatsAvailable;
    String _id;
    String driverContactNum;



    public String getDriverContactNum() {
        return driverContactNum;
    }

    public void setDriverContactNum(String driverContactNum) {
        this.driverContactNum = driverContactNum;
    }





    public model(String busNumber, String busType, String date, String destination, String driverName, String source, String time, String totalSeats, String seatsAvailable,String _id) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.date = date;
        this.destination = destination;
        this.driverName = driverName;
        this.source = source;
        this.time = time;
        this.totalSeats = totalSeats;
        this.seatsAvailable = seatsAvailable;
        this._id=_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public model() {
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }


}
