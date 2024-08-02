package org.finder.util;

import java.util.HashSet;
import java.util.Stack;

@SuppressWarnings("rawtypes")
public class Node implements Comparable {
    public final int x, y, z;
    double costH;
    double costP;
    double costTotal;
    final Node parent;
    final HashSet<Node> broken = new HashSet<>();

    public Node(int x, int y, int z, Node parent) {
        this(x, y, z, 0, 0, parent);
    }

    public Node(int x, int y, int z, double costH, double costP, Node parent) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.costH = costH;
        this.costP = costP;
        this.costTotal = costH + costP;

        this.parent = parent;
    }

    public void initiateCosts(IWorldProvider world, Node endGoal, double extra) {
        this.costH = endGoal.distanceTo(this);
        this.costP = parent != null ? parent.costP + this.distanceTo(parent) : 0;
        this.costTotal = costH + costP + extra;
    }

    public HashSet<Node> getBroken() {
        return this.broken;
    }

    /**
     * @param node the node to check if is broken
     * @return true if node is broken in the local storage false if it is not
     * @note a bit of a slower method of checking ik but if we store the data of all
     *       of the other nodes, the size gets 2x every new node
     */
    public boolean isNodeBroken(Node node) {
        Node currentNode = this.parent;
        while (currentNode != null) {
            if (currentNode.getBroken().contains(currentNode)) {
                return true;
            }

            currentNode = currentNode.parent;
        }

        return false;
    }

    public void addToBroken(HashSet<Node> additionalBroken) {
        this.broken.addAll(additionalBroken);
    }

    public void addToBroken(Node node) {
        this.broken.add(node);
    }

    public double distanceTo(Node o) {
        return Math.sqrt(
                (o.x - this.x) * (o.x - this.x) + (o.y - this.y) * (o.y - this.y) + (o.z - this.z) * (o.z - this.z));
    }

    public Node getNodeWithTransformation(int[] transformationMatrix) {
        return new Node(this.x + transformationMatrix[0], this.y + transformationMatrix[1],
                this.z + transformationMatrix[2], this);
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(this.costTotal, ((Node) o).costTotal);
    }

    @Override
    public boolean equals(Object o) {
        Node other = (Node) o;
        return other.x == this.x && other.y == this.y && other.z == this.z;
    }

    public Stack<Node> toStack() {
        Node currentNode = this;
        Stack<Node> returnStack = new Stack<>();
        while (currentNode != null) {
            returnStack.push(currentNode);
            currentNode = currentNode.parent;
        }

        return returnStack;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public int hashCode() {
        return (this.y + this.z * 31) * 31 + this.x;
    }
}
