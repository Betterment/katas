package com.betterment.barseating.input;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public enum LinePreference {
    /**
     * If there are any people waiting in line to sit down when this person enters the bar, they will leave. For customers arriving at the same time, this type
     * of customer takes priority over both {@code SHORT_LINES} and {@code TURNOVER} customers.
     */
    REFUSE_LINES,
    /**
     * If there are > 3 people in line when this person enters the bar, they will leave. For customers arriving at the same time, this type of customer takes
     * priority over {@code TURNOVER} customers.
     */
    SHORT_LINES,
    /**
     * At the time the customer enters the bar, if the max(remaining time of sitting customers) > 5, they will leave.
     */
    TURNOVER;
}
