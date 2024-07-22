package org.finder.util;

public interface IOptionProvider {
    boolean isTranslationValid(Node node, Node parent, IWorldProvider world);

    double addToTotalCost(Node node, IWorldProvider world);
}
