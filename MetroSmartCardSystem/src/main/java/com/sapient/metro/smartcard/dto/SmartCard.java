package com.sapient.metro.smartcard.dto;

public class SmartCard {

    private String smartCardId;
    private String travelerId;
    private double balance;

    public String getSmartCardId() {
        return smartCardId;
    }

    public void setSmartCardId(String smartCardId) {
        this.smartCardId = smartCardId;
    }

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public SmartCard(String smartCardId, String travelerId, double balance) {
        super();
        this.smartCardId = smartCardId;
        this.travelerId = travelerId;
        this.balance = balance;
    }

    public SmartCard() {
        super();
    }

}
