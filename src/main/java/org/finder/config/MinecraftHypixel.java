package org.finder.config;

import org.finder.util.BlockState;
import org.finder.util.IOptionProvider;
import org.finder.util.IStateProvider;
import org.finder.util.IWorldProvider;
import org.finder.util.Node;
import org.finder.util.NodePickStyle;
import org.finder.util.Transition;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @note This only works for CROSS atm. Hopefully an optimal(ish) way to check
 *       if the transition is valid.
 * @note2 ATM does not work for unrendered blocks and will count them as
 *        unobstructed. (womp womp)
 */
public class MinecraftHypixel implements IOptionProvider {
    final boolean allowMineBlocks;

    public MinecraftHypixel(boolean allowMineBlocks) {
        this.allowMineBlocks = allowMineBlocks;
    }

    @Override
    public Transition getTransition(Node node, Node parent, IWorldProvider world, NodePickStyle pickStyle) {
        Transition walk = isWalk(node, parent, world);
        if (walk != null) {
            return walk;
        } else {
            Transition jump = isJump(node, parent, world);
            if (jump != null) {
                return jump;
            } else {
                Transition fall = isFall(node, parent, world);
                if (fall != null) {
                    return fall;
                }
            }
        }

        return null;
    }

    double addToTotalCostDefault(Node node, IStateProvider world) {
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

    Transition isWalk(Node node, Node parent, IStateProvider world) {
        Node blockUnder = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node blockAbove = node.getNodeWithTransformation(new int[] { 0, 1, 0 });

        if (parent.y - node.y == 0) {
            boolean isNodeObstructed = world.getBlockState(node) == BlockState.OBSTRUCTED;
            boolean isBlockAboveObstructed = world.getBlockState(blockAbove) == BlockState.OBSTRUCTED;
            boolean isBlockUnderObstructed = world.getBlockState(blockUnder) == BlockState.OBSTRUCTED;

            if (!isNodeObstructed && !isBlockAboveObstructed && isBlockUnderObstructed) {
                return new Transition(1.0);
            }

            if (isBlockUnderObstructed && !node.getBroken().contains(blockUnder) && allowMineBlocks) {
                HashSet<Node> toRemove = new HashSet<>();
                if (isBlockAboveObstructed) {
                    toRemove.add(blockAbove);
                }

                if (isNodeObstructed) {
                    toRemove.add(node);
                }

                return new Transition(toRemove.size() * 4, toRemove);
            }
        }

        return null;
    }

    Transition isJump(Node node, Node parent, IStateProvider world) {
        Node blockUnderNode = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node blockAboveNode = node.getNodeWithTransformation(new int[] { 0, 1, 0 });

        Node block1AboveParent = parent.getNodeWithTransformation(new int[] { 0, 1, 0 });
        Node block2AboveParent = parent.getNodeWithTransformation(new int[] { 0, 2, 0 });

        if (node.y - parent.y > 0) {
            boolean isNodeObstructed = world.getBlockState(node) == BlockState.OBSTRUCTED;
            boolean isBlockUnderNodeObstructed = world.getBlockState(blockUnderNode) == BlockState.OBSTRUCTED;
            boolean isBlockAboveNodeObstructed = world.getBlockState(blockAboveNode) == BlockState.OBSTRUCTED;
            boolean isBlock1AboveParentObstructed = world.getBlockState(block1AboveParent) == BlockState.OBSTRUCTED;
            boolean isBlock2AboveParentObstructed = world.getBlockState(block2AboveParent) == BlockState.OBSTRUCTED;

            if (isBlockUnderNodeObstructed &&
                    !isBlockAboveNodeObstructed && !isBlock1AboveParentObstructed && !isBlock2AboveParentObstructed
                    && !isNodeObstructed) {
                return new Transition(1.0);
            }

            if (isBlockUnderNodeObstructed && !node.getBroken().contains(blockUnderNode) && allowMineBlocks) {
                HashSet<Node> toRemove = new HashSet<>();

                if (isBlock1AboveParentObstructed) {
                    toRemove.add(block1AboveParent);
                }

                if (isBlock2AboveParentObstructed) {
                    toRemove.add(block2AboveParent);
                }

                if (isBlockAboveNodeObstructed) {
                    toRemove.add(blockAboveNode);
                }

                if (isNodeObstructed) {
                    toRemove.add(node);
                }

                return new Transition(toRemove.size() * 4, toRemove);
            }
        }

        return null;
    }

    Transition isFall(Node node, Node parent, IStateProvider world) {
        Node blockUnderNode = node.getNodeWithTransformation(new int[] { 0, -1, 0 });
        Node block1AboveNode = node.getNodeWithTransformation(new int[] { 0, 1, 0 });
        Node block2AboveNode = node.getNodeWithTransformation(new int[] { 0, 2, 0 });

        if (node.y - parent.y < 0) {
            boolean isNodeObstructed = world.getBlockState(node) == BlockState.OBSTRUCTED;
            boolean isBlockUnderObstructed = world.getBlockState(blockUnderNode) == BlockState.OBSTRUCTED;
            boolean isBlock1AboveObstructed = world.getBlockState(block1AboveNode) == BlockState.OBSTRUCTED;
            boolean isBlock2AboveObstructed = world.getBlockState(block2AboveNode) == BlockState.OBSTRUCTED;

            if (!isNodeObstructed && isBlockUnderObstructed && !isBlock1AboveObstructed && !isBlock2AboveObstructed) {
                return new Transition(1.0);
            }

            if (isBlockUnderObstructed && !node.getBroken().contains(blockUnderNode) && allowMineBlocks) {
                HashSet<Node> toBreak = new HashSet<>();
                if (isNodeObstructed) {
                    toBreak.add(node);
                }

                if (isBlock1AboveObstructed) {
                    toBreak.add(block1AboveNode);
                }

                if (isBlock2AboveObstructed) {
                    toBreak.add(block2AboveNode);
                }

                return new Transition(toBreak.size() * 4, toBreak);
            }
        }

        return null;
    }
}
