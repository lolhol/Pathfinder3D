package org.finder.util;

public enum NodePickStyle {
    /**
     * @shape: _*_ _*_ _*_
     *         *** *_* ***
     *         _*_ _*_ _*_
     */
    CROSS(
            getCrossArr()),

    /**
     * @shape: *** *** ***
     *         *** *_* ***
     *         *** *** ***
     */
    SIDES(
            getSidesArr()),
    /**
     * @shape: _*_
     *         *_*
     *         _*_
     */
    TWO_D_CROSS(
            get2DCrossArr()),
    /**
     * @shape: ***
     *         *_*
     *         ***
     */
    TWO_D_SSIDES(
            get2DSidesArr());

    public final int[][] styleArray;

    NodePickStyle(int[][] ints) {
        styleArray = ints;
    }

    static int[][] getSidesArr() {
        int[][] returnInts = new int[25][3];
        int i = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0)
                        continue;
                    returnInts[i] = new int[] { x, y, z };
                    i++;
                }
            }
        }

        return returnInts;
    }

    static int[][] getCrossArr() {
        int[][] returnInts = new int[12][3];
        int i = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0)
                        continue;
                    if ((z == 0 && x != 0) || (z != 0 && x == 0)) {
                        returnInts[i] = new int[] { x, y, z };
                        i++;
                    }
                }
            }
        }

        return returnInts;
    }

    static int[][] get2DCrossArr() {
        int[][] returnInts = new int[4][3];
        int i = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0)
                    continue;
                if ((z == 0 && x != 0) || (z != 0 && x == 0)) {
                    returnInts[i] = new int[] { x, 0, z };
                    i++;
                }
            }
        }

        return returnInts;
    }

    static int[][] get2DSidesArr() {
        int[][] returnInts = new int[8][3];
        int i = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0)
                    continue;
                returnInts[i] = new int[] { x, 0, z };
                i++;
            }
        }

        return returnInts;
    }
}
