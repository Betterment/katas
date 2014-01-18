package com.betterment.barseating.input;

public class Arrival {
    private Customer customer;

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public int getTime() {

        return time;
    }

    public void setTime(int time) {

        this.time = time;
    }

    private int time;

    public Arrival(Customer customer, int arrivalTime) {

        this.customer = customer;
        this.time = arrivalTime;
    }
}
