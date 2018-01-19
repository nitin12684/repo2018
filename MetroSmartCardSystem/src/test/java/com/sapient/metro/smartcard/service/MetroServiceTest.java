package com.sapient.metro.smartcard.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sapient.metro.smartcard.dto.SmartCard;
import com.sapient.metro.smartcard.dto.Station;
import com.sapient.metro.smartcard.dto.Transaction;
import com.sapient.metro.smartcard.dto.Traveler;
import com.sapient.metro.smartcard.exception.InsufficientBalanceException;

/**
 * To test MetroService class.
 * 
 * @author vfroot
 *
 */
public class MetroServiceTest {

    private static final float INITIAL_BALANCE = 250.00f;

    List<Traveler> travelers = new ArrayList<>();

    MetroService metroService = new MetroServiceImpl();

    @Before
    public void setUp() {
        Traveler traveler = new Traveler();
        traveler.setTravelerId("123456");
        SmartCard card = new SmartCard();
        card.setSmartCardId("38029843");
        card.setTravelerId("123456");
        card.setBalance(INITIAL_BALANCE);
        traveler.setCard(card);

        Traveler traveler1 = new Traveler();
        traveler1.setTravelerId("123457");
        SmartCard card1 = new SmartCard();
        card1.setSmartCardId("38029844");
        card1.setTravelerId("123457");
        card1.setBalance(INITIAL_BALANCE);
        traveler1.setCard(card1);

        Traveler traveler2 = new Traveler();
        traveler2.setTravelerId("1234562");
        SmartCard card2 = new SmartCard();
        card2.setSmartCardId("380298432");
        card2.setTravelerId("1234562");
        card2.setBalance(INITIAL_BALANCE);
        traveler2.setCard(card2);

        Traveler traveler3 = new Traveler();
        traveler3.setTravelerId("1234563");
        SmartCard card3 = new SmartCard();
        card3.setSmartCardId("380298433");
        card3.setTravelerId("1234563");
        card3.setBalance(INITIAL_BALANCE);
        traveler3.setCard(card3);

        Traveler traveler4 = new Traveler();
        traveler4.setTravelerId("1234564");
        SmartCard card4 = new SmartCard();
        card4.setSmartCardId("380298434");
        card4.setTravelerId("1234564");
        card4.setBalance(INITIAL_BALANCE);
        traveler4.setCard(card4);

        Traveler traveler5 = new Traveler();
        traveler5.setTravelerId("1234565");
        SmartCard card5 = new SmartCard();
        card5.setSmartCardId("380298435");
        card5.setTravelerId("1234565");
        card5.setBalance(INITIAL_BALANCE);
        traveler5.setCard(card5);

        travelers.add(traveler);
        travelers.add(traveler1);
        travelers.add(traveler2);
        travelers.add(traveler3);
        travelers.add(traveler4);
        travelers.add(traveler5);

    }

    @After
    public void tearDown() {
        travelers.clear();
    }

    @Test
    public void testSwapInWhenCardHasLowBalance() {
        travelers.get(0).getCard().setBalance(3.00f);
        try {
            metroService.swipeIn(travelers.get(0), Station.A1, new Date());
            Assert.fail("Exception must be thrown due to low balance.");
        } catch (InsufficientBalanceException e) {
            Assert.assertEquals("Insufficient balance. Card Id: 38029843", e.getMessage());
        }

    }

    @Test
    public void testSuccessfulSwapIn() {
        travelers.get(0).getCard().setBalance(50.0f);
        try {
            Transaction transaction = metroService.swipeIn(travelers.get(0), Station.A1, new Date());
            Assert.assertEquals(Station.A1, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
    }

    @Test
    public void testSuccessfulSwapOutOnWeekdays() {
        Traveler traveler = travelers.get(0);
        traveler.getCard().setBalance(50.0f);
        Transaction transaction = null;
        try {
            transaction = metroService.swipeIn(traveler, Station.A1, new Date());
            Assert.assertEquals(Station.A1, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        Assert.assertNotNull(transaction);
        // Weekdays- Say Monday
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        try {
            metroService.swipeOut(transaction, Station.A3, calendar.getTime());
            Assert.assertEquals(Station.A3, transaction.getSwipeOutStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }

        Assert.assertEquals(14.00d, transaction.getTotalFare(), .1);
    }

    @Test
    public void testSuccessfulSwapOutOnWeekend() {
        Traveler traveler = travelers.get(0);
        traveler.getCard().setBalance(50.0f);
        Transaction transaction = null;
        try {
            transaction = metroService.swipeIn(traveler, Station.A1, new Date());
            Assert.assertEquals(Station.A1, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        Assert.assertNotNull(transaction);
        // Weekend- Say Monday
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_WEEK, 6);
        try {
            metroService.swipeOut(transaction, Station.A3, calendar.getTime());
            Assert.assertEquals(Station.A3, transaction.getSwipeOutStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }

        Assert.assertEquals(14.00d, transaction.getTotalFare(), .1);
    }

    @Test
    public void testSuccessfulSwapOutOnInReverseDirection() {
        Traveler traveler = travelers.get(0);
        traveler.getCard().setBalance(50.0f);
        Transaction transaction = null;
        try {
            transaction = metroService.swipeIn(traveler, Station.A3, new Date());
            Assert.assertEquals(Station.A3, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        Assert.assertNotNull(transaction);
        // Weekend- Say Saturday
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        try {
            metroService.swipeOut(transaction, Station.A1, calendar.getTime());
            Assert.assertEquals(Station.A1, transaction.getSwipeOutStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }

        Assert.assertEquals(14.00d, transaction.getTotalFare(), .1);
    }

    @Test
    public void testNotSufficientBalanceWhileSwapOut() {
        Traveler traveler = travelers.get(0);
        traveler.getCard().setBalance(10.0f);
        Transaction transaction = null;
        try {
            transaction = metroService.swipeIn(traveler, Station.A3, new Date());
            Assert.assertEquals(Station.A3, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        Assert.assertNotNull(transaction);
        // Weekend- Say Saturday
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_WEEK, 6);
        try {
            metroService.swipeOut(transaction, Station.A1, calendar.getTime());
            Assert.fail("Exception must be thrown.");
        } catch (InsufficientBalanceException e) {
            Assert.assertEquals("Insufficient balance. Card Id: 38029843", e.getMessage());
        }
    }

    @Test
    public void testGeneratePerCardReport() {
        Traveler traveler = travelers.get(0);
        traveler.getCard().setBalance(50.0f);
        Transaction transaction = null;
        try {
            transaction = metroService.swipeIn(traveler, Station.A3, new Date());
            Assert.assertEquals(Station.A3, transaction.getSwipeInStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        Assert.assertNotNull(transaction);
        // Weekend- Say Saturday
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_WEEK, 6);
        try {
            metroService.swipeOut(transaction, Station.A1, calendar.getTime());
            Assert.assertEquals(Station.A1, transaction.getSwipeOutStation());
        } catch (InsufficientBalanceException e) {
            Assert.fail("Exception must not be thrown.");
        }
        // test operation - Generate report
        String report = transaction.toString();
        String expectedReport = "Card 38029843 used to travel from station A3 to station A1. Fare is Rs. 000014.00 and balance on credit card is Rs. 000036.00";
        Assert.assertEquals(expectedReport, report);
    }

    @Test
    public void testFootFallReport() throws Exception {
        Transaction transaction = null;
        for (Traveler traveler : travelers) {
            transaction = metroService.swipeIn(traveler, Station.A3, new Date());
            metroService.swipeOut(transaction, Station.A1, new Date());
        }
        for (Traveler traveler : travelers) {
            transaction = metroService.swipeIn(traveler, Station.A1, new Date());
            metroService.swipeOut(transaction, Station.A3, new Date());
        }

        for (Traveler traveler : travelers) {
            transaction = metroService.swipeIn(traveler, Station.A6, new Date());
            metroService.swipeOut(transaction, Station.A5, new Date());
        }

        for (Traveler traveler : travelers) {
            transaction = metroService.swipeIn(traveler, Station.A5, new Date());
            metroService.swipeOut(transaction, Station.A10, new Date());
        }
        Assert.assertEquals(12, metroService.getTotalNumberOfFootFall(Station.A3));
        Assert.assertEquals(12, metroService.getTotalNumberOfFootFall(Station.A1));
        Assert.assertEquals(0, metroService.getTotalNumberOfFootFall(Station.A2));
        Assert.assertEquals(6, metroService.getTotalNumberOfFootFall(Station.A10));
        Assert.assertEquals(6, metroService.getTotalNumberOfFootFall(Station.A6));
        Assert.assertEquals(12, metroService.getTotalNumberOfFootFall(Station.A5));
    }

}
