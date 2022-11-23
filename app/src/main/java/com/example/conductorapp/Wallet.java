package com.example.conductorapp;

public class Wallet {


    float tokenNo;
    String uid;

    public  Wallet(float tokenNo,String uid)
    {
        this.tokenNo=tokenNo;
        this.uid=uid;
    }

    public Wallet() {

    }

    public float getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(float tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
