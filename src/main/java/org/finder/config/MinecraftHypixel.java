package org.finder.config;

import org.finder.util.BlockState;
import org.finder.util.IOptionProvider;
import org.finder.util.IStateProvider;
import org.finder.util.IWorldProvider;
import org.finder.util.Node;
import org.finder.util.NodePickStyle;

/**
 * @for This config is made for hypixel skyblock and Minecraft in general.
 * @note ONLY WORKS FOR CROSS ATM!
 */
public class MinecraftHypixel implements IOptionProvider {
    public double addToTotalCost(Node node, IWorldProvider world) {
        int[] nodePosition = new int[] { node.x, node.y + 2, node.z };
        double totalCost = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    int[] newNode = new int[] { nodePosition[0] + x, nodePosition[1] + y, nodePosition[2] + z };
                    if (world.getBlockState(newNode) == BlockState.OBSTRUCTED) {
                        totalCost += 1.0;
                    }
                }
            }
        }

        return totalCost;
    }

    boolean isWalk(Node node, Node parent, IStateProvider world) {
        Node blockUnder = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node blockAbove = node.getNodeWithTransformation(new int[] { 0, 1, 0 });

        return (parent.y - node.y == 0
                && world.getBlockState(node) == BlockState.UNOBSTRUCTED
                && world.getBlockState(blockUnder) == BlockState.OBSTRUCTED
                && world.getBlockState(blockAbove) == BlockState.UNOBSTRUCTED);
    }

    boolean isJump(Node node, Node parent, IStateProvider world) {
        Node blockUnderNode = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node blockAboveNode = node.getNodeWithTransformation(new int[] { 0, 1, 0 });

        Node block1AboveParent = parent.getNodeWithTransformation(new int[] { 0, 1, 0 });
        Node block2AboveParent = parent.getNodeWithTransformation(new int[] { 0, 2, 0 });

        return (node.y - parent.y > 0
                && world.getBlockState(node) == BlockState.UNOBSTRUCTED
                && world.getBlockState(blockUnderNode) == BlockState.OBSTRUCTED
                && world.getBlockState(blockAboveNode) == BlockState.UNOBSTRUCTED
                && world.getBlockState(block1AboveParent) == BlockState.UNOBSTRUCTED
                && world.getBlockState(block2AboveParent) == BlockState.UNOBSTRUCTED);
    }

    boolean isFall(Node node, Node parent, IStateProvider world) {
        Node blockUnderNode = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node block1AboveNode = node.getNodeWithTransformation(new int[] { 0, 1, 0 });
        Node block2AboveNode = node.getNodeWithTransformation(new int[] { 0, 2, 0 });

        return (node.y - parent.y < 0
                && world.getBlockState(blockUnderNode) == BlockState.OBSTRUCTED
                && world.getBlockState(block1AboveNode) == BlockState.UNOBSTRUCTED
                && world.getBlockState(block2AboveNode) == BlockState.UNOBSTRUCTED);
    }

    @Override
    public boolean isTranslationValid(Node node, Node parent, IWorldProvider world, NodePickStyle pickStyle) {
        return isWalk(node, parent, world) || isJump(node, parent, world) || isFall(node, parent, world);
    }

    boolean isWalkSide(Node node, Node parent, IStateProvider world) {
        return isWalk(node, parent, world) && isClearOnSides(node, parent, world);
    }

    public static boolean isClearOnSides(Node one, Node two, IStateProvider world) {
        int changeX = two.x - one.x;
        int changeZ = two.z - one.z;

        return (world.getBlockState(one.getNodeWithTransformation(new int[] { 0, 0, changeZ })) == BlockState.OBSTRUCTED
                &&
                world.getBlockState(one.getNodeWithTransformation(new int[] { changeX, 0, 0 })) == BlockState.OBSTRUCTED
                &&
                world.getBlockState(one.getNodeWithTransformation(new int[] { 0, 1, changeZ })) == BlockState.OBSTRUCTED
                &&
                world.getBlockState(
                        one.getNodeWithTransformation(new int[] { changeX, 1, 0 })) == BlockState.OBSTRUCTED);
    }

    boolean isSideBlock(Node node, Node parent) {
        return node.distanceTo(parent) > 1;
    }
}
