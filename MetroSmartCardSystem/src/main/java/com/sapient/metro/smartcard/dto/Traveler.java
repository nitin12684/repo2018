package com.sapient.metro.smartcard.dto;

public class Traveler {

    private String travelerId;
    private SmartCard card;

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    public Traveler() {
        super();
    }

    public Traveler(String travelerId) {
        super();
        this.travelerId = travelerId;
    }

    public SmartCard getCard() {
        return card;
    }

    public void setCard(SmartCard card) {
        this.card = card;
    }

}
