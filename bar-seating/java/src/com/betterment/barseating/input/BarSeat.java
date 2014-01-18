package com.betterment.barseating.input;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/17/14
 */

public class BarSeat {
    private CustomerInput customer;
    private int sitTime;

    public void sit(CustomerInput customer, int sitTime) {

        this.customer = customer;
        this.sitTime = sitTime;
    }

    public boolean isEmptyAt(int time) {

        return customer == null || time >= sitTime + customer.getDrinkDuration();
    }

    public void leave() {

        this.customer = null;
        this.sitTime = -1;
    }
}
