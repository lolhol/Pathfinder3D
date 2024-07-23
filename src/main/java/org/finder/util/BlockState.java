package org.finder.util;

/**
 * @OBSTRUCTED obstructed node
 * @UNOBSTRUCTED unobstructed node
 * @DOES_NOT_EXIST node does not exist (ex: not rendered). If this is returned,
 *                 the pathfinder will not consider it at all.
 */
public enum BlockState {
    OBSTRUCTED,
    UNOBSTRUCTED,
    DOES_NOT_EXIST
}
