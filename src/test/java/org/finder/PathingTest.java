package org.finder;

import org.finder.calculator.AStarPathfinder;
import org.finder.util.Grid;
import org.finder.util.Node;
import org.finder.util.NodePickStyle;
import org.junit.jupiter.api.Test;

public class PathingTest {
    @Test
    public void pathingTestOne() {
        Grid grid = new Grid();
        int[] start = new int[]{0, 0, 0};
        int[] end = new int[]{0, 1, 0};
        AStarPathfinder pather = new AStarPathfinder();
        Node path = pather.calculate1(grid, NodePickStyle.CROSS, 100, start, end);
        System.out.println(path.toStack());
    }
}
