package org.finder.util;

public interface IWorldProvider {
    BlockState getBlockState(int[] position);
    default BlockState getBlockState(Node position) {
        return getBlockState(new int[] {position.x, position.y, position.z});
    }
}
