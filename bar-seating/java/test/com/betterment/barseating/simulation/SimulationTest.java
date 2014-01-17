package com.betterment.barseating.simulation;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import com.betterment.barseating.input.CustomerInput;
import com.betterment.barseating.input.LineBehavior;
import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;

import com.betterment.barseating.input.SimulationInput;
import com.google.common.collect.Lists;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/15/14
 */
public class SimulationTest {

    private InputBuilder testInputBuilder;

    @Before
    public void setUp() {

        testInputBuilder = null;
    }

    @Test
    public void noCustomersMeansTheBarNeverHadToOpen() {

        givenBarClosesAt(10)
                .andNoCustomers();

        thenTheBarClosesAt(0);
    }

    @Test
    public void theBarDoesNotCloseUntilTheLastCustomerLeaves() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.SHORT_LINES, 15);

        thenTheBarClosesAt(15);
    }

    @Test
    public void leavingAndSittingDownHappensInstantly() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(3, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(7, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(9, LineBehavior.REFUSE_LINES, 2);

        thenTheBarClosesAt(11);
    }

    @Test
    public void theBarNeverOpened() {

        givenBarClosesAt(0)
                .withSeats(1)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 3);

        thenTheBarClosesAt(0);
    }

    @Test
    public void customersCanArriveRightAtClosingTime() {

        givenBarClosesAt(0)
                .withSeats(1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 3);

        thenTheBarClosesAt(3);
    }

    @Test
    public void customersDoNotHaveToArriveWhenTheBarOpens() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(5, LineBehavior.REFUSE_LINES, 3);

        thenTheBarClosesAt(8);
    }

    @Test
    public void customerPriority() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.TURNOVER, 3)
                .andCustomer(0, LineBehavior.SHORT_LINES, 2)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 7);

        thenTheBarClosesAt(15);

    }

    @Test
    public void multipleBarSeats() {

        givenBarClosesAt(10)
                .withSeats(2)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 5)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 8);

        thenTheBarClosesAt(13);
    }

    @Test
    public void shortLinesCustomerLeaveWhenFourOrMorePeopleAreInLineInFrontOfThem() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.SHORT_LINES, 2)
                .andCustomer(0, LineBehavior.SHORT_LINES, 3)
                .andCustomer(0, LineBehavior.SHORT_LINES, 4)
                .andCustomer(0, LineBehavior.SHORT_LINES, 5)
                .andCustomer(0, LineBehavior.SHORT_LINES, 6)
                .andCustomer(0, LineBehavior.SHORT_LINES, 7);

        thenTheBarClosesAt(20);
    }

    @Test
    public void turnoverCustomersOnlyCareAboutThePeopleCurrentlySeated() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(0, LineBehavior.TURNOVER, 3)
                .andCustomer(0, LineBehavior.TURNOVER, 4);

        thenTheBarClosesAt(6);

    }

    @Test
    public void turnoverCustomersOnlyCareAboutPeopleCurrentlySeatedPart2() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(0, LineBehavior.TURNOVER, 6)
                .andCustomer(0, LineBehavior.TURNOVER, 6);

        thenTheBarClosesAt(16);
    }

    @Test
    public void refuseLinesCustomersLeaveWhenAnyCustomerIsInLineInFrontOfThem() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(0, LineBehavior.REFUSE_LINES, 5);

        thenTheBarClosesAt(3);
    }

    @Test
    public void biggerSimulation() {

        givenBarClosesAt(30)
                .withSeats(4)
                .andCustomer(6, LineBehavior.SHORT_LINES, 6)
                .andCustomer(9, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(20, LineBehavior.SHORT_LINES, 9)
                .andCustomer(12, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(27, LineBehavior.SHORT_LINES, 6)
                .andCustomer(12, LineBehavior.SHORT_LINES, 9)
                .andCustomer(15, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(30, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(8, LineBehavior.REFUSE_LINES, 8)
                .andCustomer(26, LineBehavior.SHORT_LINES, 8)
                .andCustomer(23, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(17, LineBehavior.SHORT_LINES, 3)
                .andCustomer(12, LineBehavior.SHORT_LINES, 5)
                .andCustomer(7, LineBehavior.SHORT_LINES, 3)
                .andCustomer(16, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(8, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(17, LineBehavior.SHORT_LINES, 8)
                .andCustomer(6, LineBehavior.REFUSE_LINES, 8)
                .andCustomer(23, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(10, LineBehavior.REFUSE_LINES, 8)
                .andCustomer(5, LineBehavior.SHORT_LINES, 9)
                .andCustomer(4, LineBehavior.SHORT_LINES, 7)
                .andCustomer(5, LineBehavior.REFUSE_LINES, 9)
                .andCustomer(15, LineBehavior.REFUSE_LINES, 5)
                .andCustomer(3, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(25, LineBehavior.SHORT_LINES, 6)
                .andCustomer(13, LineBehavior.SHORT_LINES, 9)
                .andCustomer(7, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(17, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(29, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(16, LineBehavior.SHORT_LINES, 2)
                .andCustomer(2, LineBehavior.SHORT_LINES, 4)
                .andCustomer(19, LineBehavior.SHORT_LINES, 1)
                .andCustomer(5, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(20, LineBehavior.SHORT_LINES, 6)
                .andCustomer(19, LineBehavior.SHORT_LINES, 9)
                .andCustomer(12, LineBehavior.SHORT_LINES, 5)
                .andCustomer(13, LineBehavior.REFUSE_LINES, 9)
                .andCustomer(3, LineBehavior.REFUSE_LINES, 9)
                .andCustomer(16, LineBehavior.SHORT_LINES, 1)
                .andCustomer(11, LineBehavior.SHORT_LINES, 2)
                .andCustomer(22, LineBehavior.SHORT_LINES, 1)
                .andCustomer(23, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(10, LineBehavior.SHORT_LINES, 9)
                .andCustomer(23, LineBehavior.SHORT_LINES, 6)
                .andCustomer(10, LineBehavior.SHORT_LINES, 4)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(30, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(4, LineBehavior.SHORT_LINES, 1)
                .andCustomer(25, LineBehavior.REFUSE_LINES, 9)
                .andCustomer(14, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(13, LineBehavior.SHORT_LINES, 4)
                .andCustomer(4, LineBehavior.SHORT_LINES, 7)
                .andCustomer(14, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(11, LineBehavior.SHORT_LINES, 3)
                .andCustomer(13, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(28, LineBehavior.SHORT_LINES, 5)
                .andCustomer(30, LineBehavior.SHORT_LINES, 7)
                .andCustomer(29, LineBehavior.SHORT_LINES, 7)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(8, LineBehavior.SHORT_LINES, 7)
                .andCustomer(10, LineBehavior.SHORT_LINES, 7)
                .andCustomer(20, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(9, LineBehavior.SHORT_LINES, 7)
                .andCustomer(16, LineBehavior.SHORT_LINES, 1)
                .andCustomer(5, LineBehavior.SHORT_LINES, 6)
                .andCustomer(19, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(26, LineBehavior.SHORT_LINES, 8)
                .andCustomer(24, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(29, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(27, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(8, LineBehavior.SHORT_LINES, 7)
                .andCustomer(18, LineBehavior.REFUSE_LINES, 4)
                .andCustomer(1, LineBehavior.SHORT_LINES, 3)
                .andCustomer(1, LineBehavior.SHORT_LINES, 7)
                .andCustomer(21, LineBehavior.SHORT_LINES, 5)
                .andCustomer(20, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(27, LineBehavior.REFUSE_LINES, 3)
                .andCustomer(10, LineBehavior.SHORT_LINES, 6)
                .andCustomer(6, LineBehavior.REFUSE_LINES, 5)
                .andCustomer(6, LineBehavior.SHORT_LINES, 1)
                .andCustomer(28, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(10, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(15, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(20, LineBehavior.SHORT_LINES, 2)
                .andCustomer(8, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(3, LineBehavior.REFUSE_LINES, 8)
                .andCustomer(25, LineBehavior.SHORT_LINES, 8)
                .andCustomer(1, LineBehavior.REFUSE_LINES, 1)
                .andCustomer(23, LineBehavior.SHORT_LINES, 9)
                .andCustomer(30, LineBehavior.REFUSE_LINES, 7)
                .andCustomer(15, LineBehavior.REFUSE_LINES, 6)
                .andCustomer(29, LineBehavior.SHORT_LINES, 3)
                .andCustomer(25, LineBehavior.SHORT_LINES, 9)
                .andCustomer(8, LineBehavior.SHORT_LINES, 8)
                .andCustomer(10, LineBehavior.SHORT_LINES, 8)
                .andCustomer(23, LineBehavior.SHORT_LINES, 6)
                .andCustomer(11, LineBehavior.SHORT_LINES, 5)
                .andCustomer(18, LineBehavior.REFUSE_LINES, 2)
                .andCustomer(16, LineBehavior.SHORT_LINES, 1);

        thenTheBarClosesAt(45);

    }

    private InputBuilder givenBarClosesAt(int closingTime) {

        return testInputBuilder = new InputBuilder().barClosesAt(closingTime);
    }

    private void thenTheBarClosesAt(int closingTime) {

        SimulationInput input = testInputBuilder.build();
        int result = entryPoint.apply(input);

        assertEquals(closingTime, result);
    }

    private static final Function<SimulationInput, Integer> entryPoint = new Function<SimulationInput, Integer>() {

        @Override
        public Integer apply(SimulationInput input) {

            /* you have to fill this out. this is the facade to however you decide to implement your simulation */
            throw new NotImplementedException();
        }
    };

    private static class InputBuilder {
        private int closingTime = 0;
        private int numberOfBarSeats = 1;
        private List<CustomerInput> customerInputs = Lists.newArrayList();

        public InputBuilder barClosesAt(int closingTime) {

            this.closingTime = closingTime;
            return this;
        }

        public InputBuilder withSeats(int numberOfSeats) {

            this.numberOfBarSeats = numberOfSeats;
            return this;
        }

        public InputBuilder andCustomer(int arrivalTime, LineBehavior lineBehavior, int duration) {

            customerInputs.add(new CustomerInput(arrivalTime, lineBehavior, duration));
            return this;
        }

        public InputBuilder andNoCustomers() {

            customerInputs.clear();
            return this;
        }

        public SimulationInput build() {

            return new SimulationInput(this.closingTime, this.numberOfBarSeats, customerInputs);
        }
    }
}
