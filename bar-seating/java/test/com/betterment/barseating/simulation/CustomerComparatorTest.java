package com.betterment.barseating.simulation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import com.betterment.barseating.CustomerComparator;
import com.betterment.barseating.input.CustomerInput;
import com.betterment.barseating.input.LineBehavior;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/17/14
 */
public class CustomerComparatorTest {

    @Test
    public void equals() {

        CustomerInput a = new CustomerInput(0, LineBehavior.REFUSE_LINES, 10);
        CustomerInput b = new CustomerInput(0, LineBehavior.REFUSE_LINES, 10);

        assertEquals(0, CustomerComparator.ASCENDING.compare(a, b));
    }

    @Test
    public void arrivalTimeMattersMost() {

        CustomerInput a = new CustomerInput(1, LineBehavior.REFUSE_LINES, 10);
        CustomerInput b = new CustomerInput(2, LineBehavior.REFUSE_LINES, 10);

        assertTrue(CustomerComparator.ASCENDING.compare(a, b) < 0);
        assertTrue(CustomerComparator.ASCENDING.compare(b, a) > 0);
    }

    @Test
    public void customerTypeMattersNext() {

        CustomerInput a = new CustomerInput(1, LineBehavior.REFUSE_LINES, 10);
        CustomerInput b = new CustomerInput(1, LineBehavior.SHORT_LINES, 10);

        assertTrue(CustomerComparator.ASCENDING.compare(a, b) < 0);
        assertTrue(CustomerComparator.ASCENDING.compare(b, a) > 0);
    }

    @Test
    public void durationMattersLeast() {

        CustomerInput a = new CustomerInput(1, LineBehavior.REFUSE_LINES, 9);
        CustomerInput b = new CustomerInput(2, LineBehavior.REFUSE_LINES, 10);

        assertTrue(CustomerComparator.ASCENDING.compare(a, b) < 0);
        assertTrue(CustomerComparator.ASCENDING.compare(b, a) > 0);
    }

}
