package com.betterment.barseating.input;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public enum LineBehavior {
    /**
     * If there are any people waiting in line to sit down when this person enters the bar, they will leave. For customers arriving at the same time, this type
     * of customer takes priority over both {@code SHORT_LINES} and {@code TURNOVER} customers.
     */
    REFUSE_LINES(0),
    /**
     * If there are > 3 people in line when this person enters the bar, they will leave. For customers arriving at the same time, this type of customer takes
     * priority over {@code TURNOVER} customers.
     */
    SHORT_LINES(1),
    /**
     * At the time the customer enters the bar, if the max(remaining time of sitting customers) > 5, they will leave.
     */
    TURNOVER(2);

    private final int weight;

    LineBehavior(int weight) {

        this.weight = weight;
    }

    public int getWeight() {

        return weight;
    }
}
