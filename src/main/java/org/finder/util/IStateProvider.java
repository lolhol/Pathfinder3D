package org.finder.util;

public interface IStateProvider {
    /**
     * @param position The int[] position of the block (similar to x, y, z).
     * @return The state of the block.
     * @note This is used to tell the pathfinder what IS obstructed and what is not.
     * Basically a way to tell the pathfinder about
     * the environment without giving it all of the data. Kind of like a
     * lambda but not.
     */
    BlockState getBlockState(int[] position);

    /**
     * @note Basically an adaptation of the getBlockState for the Node class. Used
     * for easier internal use.
     */
    default BlockState getBlockState(Node position) {
        return getBlockState(new int[]{position.x, position.y, position.z});
    }
}
