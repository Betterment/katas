package com.betterment.barseating.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Created by harriseffron on 1/17/14.
 */
public class BarSimulator {

    public int simulate(SimulationInput input) {

        int output = 0;
        int closingTime = input.getBarClosingTime();
        List<CustomerInput> customers = input.getCustomerInputs();
        int seats = input.getNumberOfBarSeats();


        BarState barState = new BarState(seats, closingTime);

        next(customers, barState, 0);

        return barState.timeWithPeople;
    }


    private void next(final List<CustomerInput> customers, final BarState barState, int currentTime) {

        if (currentTime < Integer.MAX_VALUE) {
            return;
        }

        barState.next(currentTime);

        Iterables.transform(customers, new Function<CustomerInput, Object>() {
            @Override
            public Object apply(CustomerInput input) {

                barState.apply(input);

                return null;
            }
        });

        Iterables.transform(customers, new Function<CustomerInput, Object>() {
            @Override
            public Object apply(CustomerInput input) {

                barState.apply(input);

                return null;
            }
        });

        next(customers, barState, currentTime+1);

    }

    private class SeatState {
        public Optional<CustomerInput> customerInput;
        public int ejectionTime;

        public boolean reset() {
            ejectionTime = 0;
            customerInput = Optional.absent();
        }
    }

    private class BarState {
        public List<SeatState> seats;
        public List<CustomerInput> line;
        public int currentTime;
        public int closeTime;
        public int timeWithPeople;

        public BarState(int numSeats, int closeTime) {
            this.currentTime = 0;

            seats = Lists.newArrayList(Iterables.transform(getListOfSize(numSeats), new Function<Object, SeatState>() {
                @Override
                public SeatState apply(Object input) {
                    return new SeatState();
                }
            }));

            this.closeTime = closeTime;

            this.line = new ArrayList<>();
        }

        public void apply(CustomerInput input) {

        }

        public void next(final int currentTime) {
            Iterables.transform(this.seats, new Function<SeatState, Boolean>() {
                @Override
                public Boolean apply(SeatState input) {

                    return input.ejectionTime == currentTime && input.reset();
                }
            });
        }
    }

    private static Iterable<Object> getListOfSize(int size) {
        Object[] array = new Object[size];

        Arrays.fill(array, 0, size, new Object());

        return Arrays.asList(array);
    }
}
