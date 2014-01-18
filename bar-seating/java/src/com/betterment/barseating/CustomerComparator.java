package com.betterment.barseating;

import java.util.Comparator;
import java.util.Map;

import com.betterment.barseating.input.CustomerInput;
import com.betterment.barseating.input.LineBehavior;
import com.google.common.collect.Maps;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/17/14
 */
public class CustomerComparator implements Comparator<CustomerInput> {

    public static Comparator<CustomerInput> ASCENDING = new CustomerComparator();

    private static final Map<LineBehavior, Integer> preferenceWeights = Maps.newHashMap();

    static {
        preferenceWeights.put(LineBehavior.REFUSE_LINES, 0);
        preferenceWeights.put(LineBehavior.SHORT_LINES, 1);
        preferenceWeights.put(LineBehavior.TURNOVER, 2);
    }

    @Override
    public int compare(CustomerInput customer1, CustomerInput customer2) {

        int arrivalTimeCompare = Integer.compare(customer1.getArrivalTime(), customer2.getArrivalTime());
        int customerTypeCompare = Integer.compare(preferenceWeights.get(customer1.getLineBehavior()), preferenceWeights.get(customer2.getLineBehavior()));
        int sitDurationCompare = Integer.compare(customer1.getDrinkDuration(), customer2.getDrinkDuration());

        return (arrivalTimeCompare * 3) + (customerTypeCompare * 2) + sitDurationCompare;

    }
}
