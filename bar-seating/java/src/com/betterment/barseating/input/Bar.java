package com.betterment.barseating.input;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public class Bar {

    private int numSeats;
    private int closingTime;

    private Set<Customer> seatedCustomers;
    private FluentIterable<Arrival> line;

    public Bar(SimulationInput input) {

        this.numSeats = input.getNumberOfBarSeats();

        this.closingTime = input.getBarClosingTime();
        this.seatedCustomers = new HashSet<Customer>(this.numSeats);

        line = FluentIterable.from(input.getCustomerInputs()).transform(new Function<CustomerInput, Arrival>() {
            public Arrival apply(CustomerInput input) {

                Customer customer = new Customer(input.getDrinkDuration(), input.getLineBehavior());
                Arrival arrival = new Arrival(customer, input.getArrivalTime());

                return arrival;
            }
        });

    }

    public Integer run() {

        return run(0, line);
    }

    private Integer run(int startTimeInput, FluentIterable<Arrival> arrivals) {

        final int startTime = startTimeInput;

        ImmutableList<Arrival> sortedArrivals = arrivals.toSortedList(new Comparator<Arrival>() {
            public int compare(Arrival a1, Arrival a2) {

                return a1.getTime() - a2.getTime();
            }
        });

        FluentIterable<Arrival> arrivalsAtNow = FluentIterable.from(sortedArrivals).filter(new Predicate<Arrival>() {
            public boolean apply(Arrival arrival) {

                return arrival.getTime() == startTime;
            }
        });

        ImmutableList<Arrival> sortedArrivalsAtNow = arrivalsAtNow.toSortedList(new Comparator<Arrival>() {
            public int compare(Arrival a1, Arrival a2) {

                return a1.getCustomer().compareTo(a2.getCustomer());
            }
        });

        final Arrival nextArrival = sortedArrivalsAtNow.get(0);

        FluentIterable<Arrival> remainingArrivals = FluentIterable.from(sortedArrivals).filter(new Predicate<Arrival>() {
            public boolean apply(Arrival arrival) {

                return arrival != nextArrival;
            }
        });

        processArrival(nextArrival);

        return remainingArrivals.isEmpty() ? startTime : run(startTime + 1, remainingArrivals);
    }

    private void processArrival(Arrival arrival) {

    }
}
