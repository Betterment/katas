package com.betterment.barseating.input;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public class SimulationInput {

    private final int barClosingTime;
    private final List<CustomerInput> customerInputs;
    private final int numberOfBarSeats;

    public SimulationInput(int barClosingTime, int numberOfBarSeats, Iterable<CustomerInput> customers) {

        this.barClosingTime = barClosingTime;
        this.numberOfBarSeats = numberOfBarSeats;
        this.customerInputs = Lists.newArrayList(customers);
    }

    public int getBarClosingTime() {

        return barClosingTime;
    }

    public int getNumberOfBarSeats() {

        return numberOfBarSeats;
    }

    public List<CustomerInput> getCustomerInputs() {

        return customerInputs;
    }

}
