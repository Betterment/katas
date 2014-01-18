package com.betterment.barseating;

import java.util.List;
import java.util.Queue;

import com.betterment.barseating.input.BarSeat;
import com.betterment.barseating.input.CustomerInput;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/17/14
 */
public interface CustomerStrategy {

    public static final CustomerStrategy REFUSE_LINES = new CustomerStrategy() {
        @Override
        public boolean willGetInLine(int currentTime, Queue<CustomerInput> currentLine, List<BarSeat> currentSeats) {

            return currentLine.size() == 0;
        }
    };

    public static final CustomerStrategy SHORT_LINES = new CustomerStrategy() {
        @Override
        public boolean willGetInLine(int currentTime, Queue<CustomerInput> currentLine, List<BarSeat> currentSeats) {

            return currentLine.size() <= 3;
        }
    };

    public static final CustomerStrategy TURNOVER = new CustomerStrategy() {
        @Override
        public boolean willGetInLine(final int currentTime, final Queue<CustomerInput> currentLine, final List<BarSeat> currentSeats) {

            return Iterables.all(currentSeats, new Predicate<BarSeat>() {
                @Override
                public boolean apply(BarSeat input) {

                    return input.isEmptyAt(currentTime + 5);
                }
            });

        }
    };

    boolean willGetInLine(final int currentTime, final Queue<CustomerInput> currentLine, final List<BarSeat> currentSeats);
}
