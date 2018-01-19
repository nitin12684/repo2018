package com.sapient.metro.smartcard.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sapient.metro.smartcard.dto.FareStrategy;
import com.sapient.metro.smartcard.dto.Station;
import com.sapient.metro.smartcard.dto.Transaction;
import com.sapient.metro.smartcard.dto.Traveler;
import com.sapient.metro.smartcard.exception.InsufficientBalanceException;

public class MetroServiceImpl implements MetroService {

    // Transaction DB (In memory) - for per traveler id
    // Key will be
    // "<TravelerId>_<Card_Id>_<Swap_In_Station_Id>_<Swap_Out_Station_Id>_<SwapInTimeInMillis>"
    Map<String, Transaction> transactions = new HashMap<>();

    /**
     * Minimum balance require for check-in;
     */
    private static final double MINIMUM_BALANCE = 5.5;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sapient.metro.smartcard.service.MetroService#getTotalNumberOfFootFall
     * (com.sapient.metro.smartcard.dto.Station)
     */
    @Override
    public long getTotalNumberOfFootFall(Station station) {
        int count = 0;
        for (String key : transactions.keySet()) {
            if (key.contains(String.format("_%s_", station.name()))) {
                count++;
            }
        }
        return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sapient.metro.smartcard.service.MetroService#swipeIn(com.sapient.
     * metro.smartcard.dto.Traveler, com.sapient.metro.smartcard.dto.Station,
     * java.util.Date)
     */
    @Override
    public Transaction swipeIn(Traveler traveler, Station station, Date swipeInTime)
            throws InsufficientBalanceException {
        if (traveler.getCard().getBalance() < MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Card Id: %s", traveler.getCard().getSmartCardId()));
        }
        Transaction transaction = new Transaction();
        transaction.setSwipeInStation(station);
        transaction.setTraveler(traveler);
        transaction.setSwipeInTime(swipeInTime);
        return transaction;
    }

    private String getKey(Transaction transaction) {
        return "<TravelerId>_<Card_Id>_<Swap_In_Station_Id>_<Swap_Out_Station_Id>_<SwapInTimeInMillis>"
                .replace("<TravelerId>", transaction.getTraveler().getTravelerId())
                .replace("<Card_Id>", transaction.getTraveler().getCard().getSmartCardId())
                .replace("<Swap_In_Station_Id>", transaction.getSwipeInStation().name())
                .replace("<Swap_Out_Station_Id>",
                        (transaction.getSwipeOutStation().name() != null ? transaction.getSwipeOutStation().name()
                                : "NA"))
                .replace("<SwapInTimeInMillis>", String.valueOf(transaction.getSwipeInTime().getTime()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sapient.metro.smartcard.service.MetroService#swipeOut(com.sapient.
     * metro.smartcard.dto.Transaction, com.sapient.metro.smartcard.dto.Station,
     * java.util.Date)
     */
    @Override
    public void swipeOut(Transaction transaction, Station swipeOutStation, Date swapOutTime)
            throws InsufficientBalanceException {
        transaction.setSwipeOutStation(swipeOutStation);
        int numberOfStations = Math
                .abs(transaction.getSwipeOutStation().getWeight() - transaction.getSwipeInStation().getWeight());
        double totalFare = FareStrategy.getStrategy(swapOutTime).getFare() * numberOfStations;
        if (totalFare > transaction.getTraveler().getCard().getBalance()) {
            throw new InsufficientBalanceException(String.format("Insufficient balance. Card Id: %s",
                    transaction.getTraveler().getCard().getSmartCardId()));
        } else {
            transaction.getTraveler().getCard()
                    .setBalance(transaction.getTraveler().getCard().getBalance() - totalFare);
        }
        // Put transaction into map for tracking
        String key = getKey(transaction);
        transactions.put(key, transaction);
        transaction.setTotalFare(totalFare);
    }

}
