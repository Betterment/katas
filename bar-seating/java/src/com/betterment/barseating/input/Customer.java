package com.betterment.barseating.input;

public class Customer implements Comparable {

    private LineBehavior behavior;

    private int drinkDuration;

    public LineBehavior getBehavior() {

        return behavior;
    }

    public void setBehavior(LineBehavior behavior) {

        this.behavior = behavior;
    }

    public int getDrinkDuration() {

        return drinkDuration;
    }

    public void setDrinkDuration(int drinkDuration) {

        this.drinkDuration = drinkDuration;
    }

    public Customer(int drinkDuration, LineBehavior lineBehavior) {

        this.drinkDuration = drinkDuration;
        this.behavior = lineBehavior;
    }

    @Override
    public int compareTo(Object o) {

        Customer other = (Customer) o;

        return behavior.compareTo(other.getBehavior());
    }
}
