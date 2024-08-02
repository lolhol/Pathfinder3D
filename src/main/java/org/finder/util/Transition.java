package org.finder.util;

import java.util.HashSet;
import java.util.Optional;

public class Transition {
    public final double cost;
    public final HashSet<Node> toBreak;

    public Transition(double cost) {
        this(cost, null);
    }

    public Transition(double cost, HashSet<Node> toBreak) {
        this.cost = cost;
        this.toBreak = toBreak;
    }
}
