package com.betterment.barseating.input;

import com.google.common.collect.ImmutableList;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public class SimulationInput {

    private final int barClosingTime;
    private final ImmutableList<CustomerInput> customerInputs;
    private final int numberOfBarSeats;

    public SimulationInput(int barClosingTime, int numberOfBarSeats, Iterable<CustomerInput> customers) {

        this.barClosingTime = barClosingTime;
        this.numberOfBarSeats = numberOfBarSeats;
        this.customerInputs = ImmutableList.<CustomerInput> builder().addAll(customers).build();
    }

    public int getBarClosingTime() {

        return barClosingTime;
    }

    public int getNumberOfBarSeats() {

        return numberOfBarSeats;
    }

    public ImmutableList<CustomerInput> getCustomerInputs() {

        return customerInputs;
    }

}
