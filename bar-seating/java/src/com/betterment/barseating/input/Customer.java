package com.betterment.barseating.input;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public class Customer {

    private final int arrivalTime;
    private final LinePreference linePreference;
    private final int drinkDuration;

    public Customer(int arrivalTime, LinePreference linePreference, int drinkDuration) {

        this.arrivalTime = arrivalTime;
        this.linePreference = linePreference;
        this.drinkDuration = drinkDuration;
    }

    /**
     * The time at which the customer arrives at the bar
     */
    public int getArrivalTime() {

        return arrivalTime;
    }

    /**
     * What behavior the customer uses when choosing a line
     */
    public LinePreference getLinePreference() {

        return linePreference;
    }

    /**
     * How long the customer will stay at the bar
     */
    public int getDrinkDuration() {

        return drinkDuration;
    }
}
