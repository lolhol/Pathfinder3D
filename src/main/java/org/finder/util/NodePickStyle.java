package org.finder.util;

// TODO: finish the "SIDES" since they are broken.
public enum NodePickStyle {
    CROSS(new int[][]{
        new int[]{
            1, 0, 0
        },
        new int[]{
            -1, 0, 0
        },
        new int[]{
            0, 1, 0
        },
        new int[]{
            0, -1, 0
        },
        new int[]{
            0, 0, 1
        },
        new int[]{
            0, 0, -1
        },
    }),
    SIDES(
        new int[][]{
            new int[]{
                1, 0, 0
            },
            new int[]{
                -1, 0, 0
            },
            new int[]{
                0, 1, 0
            },
            new int[]{
                0, -1, 0
            },
            new int[]{
                0, 0, 1
            },
            new int[]{
                0, 0, -1
            },
            new int[]{
                0, 1, 1
            },
            new int[]{
                0, -1, -1
            },
        }
    );

    public final int[][] styleArray;

    NodePickStyle(int[][] ints) {
        styleArray = ints;
    }
}
