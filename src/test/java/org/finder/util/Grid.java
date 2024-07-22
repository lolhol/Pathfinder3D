package org.finder.util;

public class Grid implements IWorldProvider {
    public final String[] GRID_1 = new String[]{
        "_##_________##__",
        "___##__#_#__##__",
        "_##_____#___##__"
    };
    public final String[] GRID_2 = new String[]{
        "__#_________#___",
        "___#_____#__#___",
        "____#___#_______"
    };
    public final String[] GRID_3 = new String[]{
        "_##_________##__",
        "___##__#_#__##__",
        "_##_____#___##__"
    };

    @Override
    public BlockState getBlockState(int[] position) {
        if (
            position[0] >= GRID_1[0].length() || position[2] >= GRID_1.length
                || position[2] >= 3 || position[0] < 0 || position[1] < 0 || position[2] < 0
        ) {
            return BlockState.DOES_NOT_EXIST;
        }

        return (position[2] == 0 ? GRID_1 : position[0] == 1 ? GRID_2 : GRID_3)[position[2]].charAt(position[0]) ==
            '_' ? BlockState.UNOBSTRUCTED : BlockState.OBSTRUCTED;
    }

    @Override
    public boolean isTranslationValid(Node node1, Node node2) {
        return true;
    }
}
