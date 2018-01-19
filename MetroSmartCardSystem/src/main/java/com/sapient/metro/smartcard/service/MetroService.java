package com.sapient.metro.smartcard.service;

import java.util.Date;

import com.sapient.metro.smartcard.dto.Station;
import com.sapient.metro.smartcard.dto.Transaction;
import com.sapient.metro.smartcard.dto.Traveler;
import com.sapient.metro.smartcard.exception.InsufficientBalanceException;

public interface MetroService {
    /**
     * To get total number of foot fall
     * 
     * @param station
     * @return
     */
    long getTotalNumberOfFootFall(Station station);

    public Transaction swipeIn(Traveler traveler, Station wipeInstation, Date swapInTime)
            throws InsufficientBalanceException;

    public void swipeOut(Transaction transaction, Station swipeOutStation, Date swapOutTime)
            throws InsufficientBalanceException;
}
