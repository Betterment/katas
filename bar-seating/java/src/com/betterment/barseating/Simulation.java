package com.betterment.barseating;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.betterment.barseating.input.BarSeat;
import com.betterment.barseating.input.CustomerInput;
import com.betterment.barseating.input.LineBehavior;
import com.betterment.barseating.input.SimulationInput;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ranges;

/**
 * @author Mike Matsui
 * @version $Id$
 * @since 1/17/14
 */
public class Simulation {

    private static interface GetsInLine {
        public static GetsInLine GETS_IN_LINE = new GetsInLine() {
            @Override
            public void invoke(Queue<CustomerInput> line, CustomerInput customer) {

                line.add(customer);
            }
        };

        public static GetsInLine LEAVES = new GetsInLine() {
            @Override
            public void invoke(Queue<CustomerInput> line, CustomerInput customer) {

            }
        };

        public void invoke(Queue<CustomerInput> line, CustomerInput customer);
    }

    public static Map<LineBehavior, CustomerStrategy> STRATEGY_MAP = Maps.newHashMap();
    public static Map<Boolean, GetsInLine> IN_LINE_MAP = Maps.newHashMap();
    public static Map<Boolean, Function<Integer, Boolean>> EXIT_MAP = Maps.newHashMap();

    static {
        STRATEGY_MAP.put(LineBehavior.REFUSE_LINES, CustomerStrategy.REFUSE_LINES);
        STRATEGY_MAP.put(LineBehavior.SHORT_LINES, CustomerStrategy.SHORT_LINES);
        STRATEGY_MAP.put(LineBehavior.TURNOVER, CustomerStrategy.TURNOVER);

        IN_LINE_MAP.put(true, GetsInLine.GETS_IN_LINE);
        IN_LINE_MAP.put(false, GetsInLine.LEAVES);

        EXIT_MAP.put(true, new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer currentTime) {

                throw new IllegalStateException("" + currentTime);
            }
        });

        EXIT_MAP.put(false, new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer currentTime) {

                return false;

            }
        });
    }

    public int runSimulation(final SimulationInput simulationInput) {

        Iterable<CustomerInput> sortedCustomer = sortAndFilterCustomers(simulationInput);
        List<BarSeat> seats = createBarSeats(simulationInput.getNumberOfBarSeats());
        Queue<CustomerInput> line = new LinkedList<CustomerInput>();

        try {

            System.out.println("WAT?!" + runSimulationForTime(0, simulationInput.getBarClosingTime(), line, seats, sortedCustomer));

        } catch (IllegalStateException e) {
            return Integer.parseInt(e.getMessage());
        }
        return 0;
    }

    public boolean runSimulationForTime(final int currentTime, final int barClosingTime, final Queue<CustomerInput> currentLine, final List<BarSeat> barSeats,
            final Iterable<CustomerInput> allCustomers) {

        Iterable<CustomerInput> customersToSeat = Iterables.filter(allCustomers, new Predicate<CustomerInput>() {
            @Override
            public boolean apply(CustomerInput input) {

                return Integer.compare(input.getArrivalTime(), currentTime) == 0;
            }
        });

        Iterable<CustomerInput> customersYetToArrive = Iterables.filter(allCustomers, new Predicate<CustomerInput>() {
            @Override
            public boolean apply(CustomerInput input) {

                return Integer.compare(input.getArrivalTime(), currentTime) > 0;
            }
        });

        // what time it current is
        // what customer arrive at this time
        // we know that the order of toProcess is the order in which we should process them
        // what seats are current occupied and who is sitting in  them?       

        // ITERATE OVER SEATS TO PROCESS GET UPS
        emptyLine(currentTime, currentLine, barSeats);
        handleArrivingCustomers(currentTime, currentLine, barSeats, customersToSeat);

        // ITERATE OVER LINE

        // tODO FIX ME
        //        remaining.removeAll(toProcess);

        EXIT_MAP.get((currentTime > barClosingTime && allSeatsEmptyAt(currentTime, barSeats) ||
                (!customersYetToArrive.iterator().hasNext() && allSeatsEmptyAt(currentTime, barSeats)))).apply(currentTime);

        return runSimulationForTime(currentTime + 1, barClosingTime, currentLine, barSeats, allCustomers);
    }

    private boolean allSeatsEmptyAt(final int currentTime, final List<BarSeat> barSeats) {

        boolean allSeatsEmpty = Iterables.all(barSeats, new Predicate<BarSeat>() {
            @Override
            public boolean apply(BarSeat input) {

                return input.isEmptyAt(currentTime);
            }
        });

        return allSeatsEmpty;
    }

    private void handleArrivingCustomers(final int currentTime, final Queue<CustomerInput> currentLine, final List<BarSeat> barSeats,
            Iterable<CustomerInput> customersToSeat) {

        Iterables.all(customersToSeat, new Predicate<CustomerInput>() {

            @Override
            public boolean apply(CustomerInput customer) {

                IN_LINE_MAP.get(STRATEGY_MAP.get(customer.getLineBehavior()).willGetInLine(currentTime, currentLine, barSeats)).invoke(currentLine, customer);
                emptyLine(currentTime, currentLine, barSeats);
                return true;
            }
        });
    }

    private void emptyLine(final int currentTime, final Queue<CustomerInput> currentLine, Iterable<BarSeat> currentSeats) {

        Iterable<BarSeat> seatsToVacate = Iterables.filter(currentSeats, new Predicate<BarSeat>() {
            @Override
            public boolean apply(BarSeat input) {

                return input.isEmptyAt(currentTime);
            }
        });

        Iterables.all(seatsToVacate, new Predicate<BarSeat>() {
            @Override
            public boolean apply(BarSeat input) {

                input.leave();
                input.sit(currentLine.poll(), currentTime);
                return true;
            }
        });
    }

    private List<CustomerInput> sortAndFilterCustomers(SimulationInput simulationInput) {

        List<CustomerInput> arrivingBeforeClose = Lists.newArrayList(Iterables.filter(simulationInput.getCustomerInputs(),
                arrivesBeforeClosingTime(simulationInput.getBarClosingTime())));

        Collections.sort(arrivingBeforeClose, CustomerComparator.ASCENDING);

        return arrivingBeforeClose;

    }

    private static Predicate<CustomerInput> arrivesBeforeClosingTime(final int barClosingTime) {

        return new Predicate<CustomerInput>() {

            @Override
            public boolean apply(CustomerInput input) {

                return input.getArrivalTime() <= barClosingTime;
            }
        };
    }

    private static List<BarSeat> createBarSeats(int numberOfSeats) {

        return Lists.newArrayList(Iterables.transform(Ranges.closed(1, numberOfSeats).asSet(DiscreteDomain.integers()), new Function<Integer, BarSeat>() {

            @Override
            public BarSeat apply(Integer input) {

                return new BarSeat();
            }
        }));
    }

}
