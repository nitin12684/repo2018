package com.sapient.metro.smartcard.dto;

import java.text.DecimalFormat;
import java.util.Date;

public class Transaction {

    private Traveler traveler;
    private Station swipeInStation;
    private Station swipeOutStation;
    private double totalFare;
    private Date swipeInTime;
    private Date swipeOutTime;

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Station getSwipeInStation() {
        return swipeInStation;
    }

    public void setSwipeInStation(Station swipeInStation) {
        this.swipeInStation = swipeInStation;
    }

    public Station getSwipeOutStation() {
        return swipeOutStation;
    }

    public void setSwipeOutStation(Station swipeOutStation) {
        this.swipeOutStation = swipeOutStation;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public Transaction() {
        super();
    }

    public Date getSwipeInTime() {
        return swipeInTime;
    }

    public void setSwipeInTime(Date swipeInTime) {
        this.swipeInTime = swipeInTime;
    }

    public Date getSwipeOutTime() {
        return swipeOutTime;
    }

    public void setSwipeOutTime(Date swipeOutTime) {
        this.swipeOutTime = swipeOutTime;
    }

    @Override
    public String toString() {
        DecimalFormat myFormatter = new DecimalFormat("000000.00");
        String formatStr = "Card %s used to travel from station %s to station %s. Fare is Rs. %s and balance on credit card is Rs. %s";
        return String.format(formatStr, getTraveler().getCard().getSmartCardId(), getSwipeInStation().name(),
                getSwipeOutStation().name(), myFormatter.format(getTotalFare()),
                myFormatter.format(getTraveler().getCard().getBalance()));
    }

}
