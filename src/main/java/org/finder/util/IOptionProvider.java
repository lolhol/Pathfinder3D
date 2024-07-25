package org.finder.util;

public interface IOptionProvider {
    /**
     * @param node      The current considered Node. Where you are going to.
     * @param parent    The parent of the current considered Node. Essentially where
     *                  you are coming from.
     * @param world     The interface to get the world state.
     * @param pickStyle The pickStyle of the pathfinder for easier adaptation of
     *                  variaous pickStyles and their respective transitions.
     * @return A string of the transition type. NONE -> IF NONE THEN THE NODE WILL
     *         BE DROPPED!
     * @note Essentially when you are going from block to block, it is not enough to
     *       just tell the pathfinder if it is obstructed or not. By default it will
     *       assume that it can go up into the air and wherever it wants. This is
     *       not what we want since we cant fly. It is basically a way to tell the
     *       pathfinder what actions it can do and which it cant.
     * 
     * @note2 This will be passed inside of the "addToTotalCost" function
     */
    String getTransitionType(Node node, Node parent, IWorldProvider world, NodePickStyle pickStyle);

    /**
     * @param node  Node currently being considered
     * @param world The interface to get the world state.
     * @return The cost to add to Total Cost.
     * @note Sometimes you want to give more emphasis on some actions than others.
     *       For example, if you are walking through a forest, you probably want to
     *       go through the most clear path so not to bump into trees so often. The
     *       higher the return value, the less likely the pathfinder is to pick this
     *       block.
     */
    double addToTotalCost(Node node, Node parent, IWorldProvider world, String transitionType);
}
