package org.finder.util;

public interface IOptionProvider {
    boolean isTranslationValid(Node node, Node parent, IWorldProvider world, NodePickStyle pickStyle);

    double addToTotalCost(Node node, IWorldProvider world);
}
