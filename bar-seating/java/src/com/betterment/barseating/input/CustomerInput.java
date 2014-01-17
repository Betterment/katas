package com.betterment.barseating.input;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public class CustomerInput {

    private final int arrivalTime;
    private final LineBehavior lineBehavior;
    private final int drinkDuration;

    public CustomerInput(int arrivalTime, LineBehavior lineBehavior, int drinkDuration) {

        this.arrivalTime = arrivalTime;
        this.lineBehavior = lineBehavior;
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
    public LineBehavior getLineBehavior() {

        return lineBehavior;
    }

    /**
     * How long the customer will stay at the bar
     */
    public int getDrinkDuration() {

        return drinkDuration;
    }
}
