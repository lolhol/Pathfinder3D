package org.finder.util;

public interface IWorldProvider {
    BlockState getBlockState(int[] position);

    /**
     * @param position The node's position.
     * @return Node's BlockState.
     */
    default BlockState getBlockState(Node position) {
        return getBlockState(new int[]{position.x, position.y, position.z});
    }

    /**
     * @param node   The current node.
     * @param parent The current node's parent
     * @return True if the translation is valid and can be done. False if it is invalid (node is dropped)
     */
    default boolean isTranslationValid(final Node node, final Node parent) {
        return true;
    }

    /**
     * @param node the node to which you can add the total cost.
     * @return the total cost to add to the specific node.
     */
    default double addToTotalCost(final Node node) {
        return 0.0;
    }
}
