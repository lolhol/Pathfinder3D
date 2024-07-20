package org.finder.calculator;

import org.finder.util.BlockState;
import org.finder.util.IWorldProvider;
import org.finder.util.Node;
import org.finder.util.NodePickStyle;

import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarPathfinder {
    private boolean calcing = false;

    /**
     * @param world     The IWorldProvider interface that will dictate the surroundings.
     * @param startNode The start position. Assuming that all of the costs are already there btw.
     * @param endNode   The end position. Assuming that all of the costs are already there btw.
     * @param pickStyle The style in which the nodes will be picked.
     */
    public Node calculate0(IWorldProvider world, NodePickStyle pickStyle, int maxIter, Node startNode, Node endNode) {
        calcing = true;
        int curIter = 0;

        HashSet<Node> closed = new HashSet<>();
        PriorityQueue<Node> open = new PriorityQueue<>();
        open.add(startNode);

        while (!open.isEmpty() && calcing && curIter < maxIter) {
            Node best = open.poll();
            closed.add(best);

            if (endNode.equals(best)) {
                return best;
            }

            for (int[] transformation : pickStyle.styleArray) {
                Node transformed = best.getNodeWithTransformation(transformation);

                if (closed.contains(transformed))
                    continue;
                if (world.getBlockState(transformation) == BlockState.OBSTRUCTED) {
                    closed.add(transformed); // we can close this node because it is is obstructed
                    continue;
                }
                if (!world.isTranslationValid(best, transformed)) {
                    continue;
                }

                transformed.initiateCosts(world, endNode);
                open.add(transformed);
            }

            curIter++;
        }

        calcing = false;
        return null;
    }

    /**
     * @param world     The IWorldProvider interface that will dictate the surroundings.
     * @param pos1      The initial position in 3D. It can be anything and it does not have to have costs.
     * @param pos2      The end position in 3D. It can be anything and it does not have to have costs.
     * @param pickStyle The style in which the nodes will be picked.
     */
    public void calculate1(IWorldProvider world, NodePickStyle pickStyle, int[] pos1, int[] pos2) {
        Node start = new Node(pos1[0], pos1[1], pos1[2], null);
        Node end = new Node(pos2[0], pos2[1], pos2[2], null);

        this.calculate0(world,
            pickStyle,
            Integer.MAX_VALUE,
            new Node(start.x, start.y, start.z, start.distanceTo(end), 0, null),
            new Node(start.x, start.y, start.z, 0, end.distanceTo(start), null)
        );
    }

    /**
     * @param world     The IWorldProvider interface that will dictate the surroundings.
     * @param pos1      The initial position in 3D. It can be anything and it does not have to have costs.
     * @param pos2      The end position in 3D. It can be anything and it does not have to have costs.
     * @param pickStyle The style in which the nodes will be picked.
     * @param maxIter   The maximum iterations for the pathfinder. Used to cap the time.
     */
    public void calculate1(IWorldProvider world, NodePickStyle pickStyle, int maxIter, int[] pos1, int[] pos2) {
        Node start = new Node(pos1[0], pos1[1], pos1[2], null);
        Node end = new Node(pos2[0], pos2[1], pos2[2], null);

        this.calculate0(world,
            pickStyle,
            maxIter,
            new Node(start.x, start.y, start.z, start.distanceTo(end), 0, null),
            new Node(start.x, start.y, start.z, 0, end.distanceTo(start), null)
        );
    }


    /**
     * @usage this is created to stop the finding process if it gets out of hand. A kind of turn off switch.
     */
    public void turnOffPathing() {
        calcing = false;
    }
}
