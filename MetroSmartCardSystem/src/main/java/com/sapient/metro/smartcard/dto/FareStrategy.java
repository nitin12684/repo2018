package com.sapient.metro.smartcard.dto;

import java.util.Calendar;
import java.util.Date;

public enum FareStrategy {

    WEEKDAYS(7.0), WEEKEND(5.5);

    private double fare;

    FareStrategy(double fare) {
        this.fare = fare;
    }

    public double getFare() {
        return fare;
    }

    /**
     * To get fare strategy based on time.
     * 
     * @param swapOutTime
     * @return
     */
    public static FareStrategy getStrategy(Date swapOutTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(swapOutTime);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < 6) {
            return FareStrategy.WEEKDAYS;
        } else {
            return FareStrategy.WEEKEND;
        }
    }

}
