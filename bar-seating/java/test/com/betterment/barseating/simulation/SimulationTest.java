package com.betterment.barseating.simulation;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.betterment.barseating.input.Customer;
import com.betterment.barseating.input.LinePreference;
import com.betterment.barseating.input.SimulationInput;
import com.google.common.collect.Lists;

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
                .andCustomer(0, LinePreference.SHORT_LINES, 15);

        thenTheBarClosesAt(15);
    }

    @Test
    public void leavingAndSittingDownHappensInstantly() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LinePreference.REFUSE_LINES, 3)
                .andCustomer(3, LinePreference.REFUSE_LINES, 4)
                .andCustomer(7, LinePreference.REFUSE_LINES, 2)
                .andCustomer(9, LinePreference.REFUSE_LINES, 2);

        thenTheBarClosesAt(11);
    }

    @Test
    public void theBarNeverOpened() {

        givenBarClosesAt(0)
                .withSeats(1)
                .andCustomer(1, LinePreference.REFUSE_LINES, 3);

        thenTheBarClosesAt(0);
    }

    @Test
    public void customersCanArriveRightAtClosingTime() {

        givenBarClosesAt(0)
                .withSeats(1)
                .andCustomer(0, LinePreference.REFUSE_LINES, 3);

        thenTheBarClosesAt(3);
    }

    @Test
    public void customersDoNotHaveToArriveWhenTheBarOpens() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(5, LinePreference.REFUSE_LINES, 3);

        thenTheBarClosesAt(8);
    }

    @Test
    public void customerPriority() {

        givenBarClosesAt(10)
                .withSeats(1)
                .andCustomer(0, LinePreference.TURNOVER, 3)
                .andCustomer(0, LinePreference.SHORT_LINES, 2)
                .andCustomer(0, LinePreference.REFUSE_LINES, 6)
                .andCustomer(0, LinePreference.REFUSE_LINES, 2);

        thenTheBarClosesAt(10);

    }

    private InputBuilder givenBarClosesAt(int closingTime) {

        return testInputBuilder = new InputBuilder().barClosesAt(closingTime);
    }

    private void thenTheBarClosesAt(int closingTime) {

        SimulationInput input = testInputBuilder.build();
        int result = entryPoint.runSimulation(input);

        assertEquals(closingTime, result);
    }

    private static final SimulationEntryPoint entryPoint = new SimulationEntryPoint() {
        @Override
        public int runSimulation(SimulationInput input) {

            // you gotsta implement this
            throw new NotImplementedException();
        }
    };

    private static class InputBuilder {
        private int closingTime = 0;
        private int numberOfBarSeats = 1;
        private List<Customer> customers = Lists.newArrayList();

        public InputBuilder barClosesAt(int closingTime) {

            this.closingTime = closingTime;
            return this;
        }

        public InputBuilder withSeats(int numberOfSeats) {

            this.numberOfBarSeats = numberOfSeats;
            return this;
        }

        public InputBuilder andCustomer(int arrivalTime, LinePreference linePreference, int duration) {

            customers.add(new Customer(arrivalTime, linePreference, duration));
            return this;
        }

        public InputBuilder andNoCustomers() {

            customers.clear();
            return this;
        }

        public SimulationInput build() {

            return new SimulationInput(this.closingTime, this.numberOfBarSeats, customers);
        }
    }
}
