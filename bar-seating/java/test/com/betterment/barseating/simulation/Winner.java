package com.betterment.barseating.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.betterment.barseating.input.Bar;
import com.betterment.barseating.input.CustomerInput;
import com.betterment.barseating.input.LineBehavior;
import com.betterment.barseating.input.SimulationInput;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;

/**
 * Created by reustmd on 1/17/14.
 */
public class Winner {

    public Integer apply(SimulationInput input) {

        Collections.sort(input.getCustomerInputs(), new Comparator<CustomerInput>() {

            Map<LineBehavior, Integer> preferenceWeights = Maps.newHashMap();

            {
                preferenceWeights.put(LineBehavior.REFUSE_LINES, 0);
                preferenceWeights.put(LineBehavior.SHORT_LINES, 1);
                preferenceWeights.put(LineBehavior.TURNOVER, 2);
            }

            @Override
            public int compare(CustomerInput customer1, CustomerInput customer2) {

                int arrivalTimeCompare = Integer.compare(customer1.getArrivalTime(), customer2.getArrivalTime());
                int customerTypeCompare = Integer.compare(preferenceWeights.get(customer1.getLineBehavior()),
                        preferenceWeights.get(customer2.getLineBehavior()));
                int sitDurationCompare = Integer.compare(customer1.getDrinkDuration(), customer2.getDrinkDuration());

                return (arrivalTimeCompare * 3) + (customerTypeCompare * 2) + sitDurationCompare;

            }
        });

        final Bar bar = new Bar(input);

        List<Integer> longTime = ContiguousSet.create(Range.closed(0, 1000), DiscreteDomain.integers()).asList();

        final Map<Boolean, Predicate<Integer>> startingStrategies = Maps.newHashMap();
        startingStrategies.put(Boolean.FALSE, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer time) {

                bar.setCurrentTime(time);

                handleSittingCustomers(time, bar);
                handleInLineCustomers(bar);
                handleArrivingCustomers(time, bar);

                return false;
            }
        });
        startingStrategies.put(Boolean.TRUE, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer time) {

                return true;
            }
        });

        Iterables.any(longTime, new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer time) {

                boolean anyOneSitting = bar.getSittingCustomers().size() > 0;
                boolean anyOneArriving = bar.getArrivingCustomers(time).size() > 0;
                //boolean isPastOrEqualClosing = time >= bar.getSimulationInput().getBarClosingTime();

                boolean anyOneWillArriveBeforeClosing = bar.willAnyoneArriveBetweenNowAndClosing(time);

                boolean shouldNotStop = anyOneSitting || anyOneArriving || anyOneWillArriveBeforeClosing;

                return startingStrategies.get(!shouldNotStop).apply(time);
            }
        });

        return bar.getCurrentTime();
    }

    private void handleArrivingCustomers(final Integer time, final Bar bar) {

        Iterable<CustomerInput> customerArrivals = bar.getArrivingCustomers(time);

        Lists.newArrayList(Iterables.transform(customerArrivals, new Function<CustomerInput, Object>() {
            @Override
            public Object apply(CustomerInput input) {

                bar.handleArrivingCustomerStrategies.get(input.getLineBehavior()).apply(input);

                return null;
            }
        }));
    }

    private void handleInLineCustomers(final Bar bar) {

        List<CustomerInput> copy = new ArrayList<>(bar.getInLineCustomers());

        Lists.newArrayList(Iterables.transform(copy, new Function<CustomerInput, Object>() {
            @Override
            public Object apply(CustomerInput currentCustomer) {

                bar.attemptToSeatInLineCustomStrategies.get(bar.hasOpenSeat()).apply(currentCustomer);

                return null;
            }
        }));
    }

    private void handleSittingCustomers(final Integer time, final Bar bar) {

        List<CustomerInput> copy = new ArrayList<>(bar.getSittingCustomers());

        Lists.newArrayList(Iterables.transform(copy, new Function<CustomerInput, Object>() {
            @Override
            public Object apply(CustomerInput currentCustomer) {

                Integer whenSat = bar.getWhenCustomerSat().get(currentCustomer);

                if (time - whenSat >= currentCustomer.getDrinkDuration()) {

                    bar.unSeatCustomer(currentCustomer);
                    bar.getAlreadyLeftCustomers().add(currentCustomer);
                }

                return null;
            }
        }));
    }

}
