package org.finder.util;

public interface IBlockPosProvider {
    int getX();

    int getY();

    int getZ();

    default int[] getPos() {
        return new int[]{
            this.getX(), this.getY(), this.getZ()
        };
    }
}
