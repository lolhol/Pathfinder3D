package org.finder.util;

public class Transition {
    public final String transitionType;
    public final double cost;

    public Transition(String transitionType, double cost) {
        this.transitionType = transitionType;
        this.cost = cost;
    }
}
