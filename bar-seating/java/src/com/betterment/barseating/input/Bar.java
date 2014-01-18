package com.betterment.barseating.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Created by reustmd on 1/17/14.
 */
public class Bar {

    public final Map<Boolean, Function<CustomerInput, Boolean>> attemptToSeatInLineCustomStrategies = Maps.newHashMap();
    public final Map<LineBehavior, Function<CustomerInput, Boolean>> handleArrivingCustomerStrategies = Maps.newHashMap();
    public final Map<Boolean, Function<CustomerInput, Boolean>> lineBehaviorCustomerStrategies = Maps.newHashMap();

    private Map<CustomerInput, Integer> whenCustomerSat;
    private SimulationInput simulationInput;

    private List<CustomerInput> sittingCustomers;
    private List<CustomerInput> inLineCustomers;
    private List<CustomerInput> alreadyLeftCustomers;
    private int currentTime;

    public Bar(SimulationInput simulationInput) {

        this.currentTime = 0;
        this.simulationInput = simulationInput;
        this.whenCustomerSat = new HashMap<>();
        this.sittingCustomers = Lists.newArrayList();
        this.inLineCustomers = Lists.newArrayList();
        this.alreadyLeftCustomers = Lists.newArrayList();

        attemptToSeatInLineCustomStrategies.put(Boolean.TRUE, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                seatCustomer(input);
                getInLineCustomers().remove(input);

                return false;
            }
        });
        attemptToSeatInLineCustomStrategies.put(Boolean.FALSE, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                return false;
            }
        });

        lineBehaviorCustomerStrategies.put(Boolean.TRUE, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                getInLineCustomers().remove(input);
                getAlreadyLeftCustomers().add(input);
                return false;
            }
        });

        lineBehaviorCustomerStrategies.put(Boolean.FALSE, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                getInLineCustomers().add(input);
                attemptToSeatInLineCustomStrategies.get(hasOpenSeat()).apply(input);
                return false;
            }
        });

        handleArrivingCustomerStrategies.put(LineBehavior.SHORT_LINES, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                lineBehaviorCustomerStrategies.get(getInLineCustomers().size() > 3).apply(input);

                return false;
            }
        });

        handleArrivingCustomerStrategies.put(LineBehavior.TURNOVER, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                lineBehaviorCustomerStrategies.get(Iterables.any(getSittingCustomers(), new Predicate<CustomerInput>() {
                    @Override
                    public boolean apply(CustomerInput input) {

                        return input.getDrinkDuration() - currentTime > 5;
                    }
                })).apply(input);

                return false;
            }
        });

        handleArrivingCustomerStrategies.put(LineBehavior.REFUSE_LINES, new Function<CustomerInput, Boolean>() {
            @Override
            public Boolean apply(CustomerInput input) {

                lineBehaviorCustomerStrategies.get(getInLineCustomers().size() > 0).apply(input);
                return false;
            }
        });
    }

    public List<CustomerInput> getArrivingCustomers(int tick) {

        return Lists.newArrayList(Iterables.filter(getSimulationInput().getCustomerInputs(), new Predicate<CustomerInput>() {
            @Override
            public boolean apply(CustomerInput input) {

                return getCurrentTime() == input.getArrivalTime();
            }
        }));
    }

    public boolean willAnyoneArriveBetweenNowAndClosing(final int tick) {

        return Iterables.any(getSimulationInput().getCustomerInputs(), new Predicate<CustomerInput>() {
            @Override
            public boolean apply(CustomerInput input) {

                return input.getArrivalTime() >= tick && input.getArrivalTime() <= getSimulationInput().getBarClosingTime();
            }
        });
    }

    public void seatCustomer(CustomerInput input) {

        this.getSittingCustomers().add(input);
        this.whenCustomerSat.put(input, this.getCurrentTime());
    }

    public void unSeatCustomer(CustomerInput input) {

        this.getSittingCustomers().remove(input);
    }

    public void setCurrentTime(int currentTime) {

        this.currentTime = currentTime;
    }

    public int getCurrentTime() {

        return currentTime;
    }

    public Map<CustomerInput, Integer> getWhenCustomerSat() {

        return whenCustomerSat;
    }

    public List<CustomerInput> getSittingCustomers() {

        return sittingCustomers;
    }

    public List<CustomerInput> getInLineCustomers() {

        return inLineCustomers;
    }

    public List<CustomerInput> getAlreadyLeftCustomers() {

        return alreadyLeftCustomers;
    }

    public SimulationInput getSimulationInput() {

        return simulationInput;
    }

    public boolean hasOpenSeat() {

        return sittingCustomers.size() < simulationInput.getNumberOfBarSeats();
    }

}
