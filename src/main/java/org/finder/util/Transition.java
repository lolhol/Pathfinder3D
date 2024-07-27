package org.finder.util;

import java.util.HashSet;
import java.util.Optional;

public class Transition {
    public final String transitionType;
    public final double cost;
    public final HashSet<Node> toBreak;

    public Transition(String transitionType, double cost) {
        this(transitionType, cost, null);
    }

    public Transition(String transitionType, double cost, HashSet<Node> toBreak) {
        this.transitionType = transitionType;
        this.cost = cost;
        this.toBreak = toBreak;
    }
}
