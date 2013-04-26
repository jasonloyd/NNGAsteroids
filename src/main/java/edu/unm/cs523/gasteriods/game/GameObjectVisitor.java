package edu.unm.cs523.gasteriods.game;

/**
 * Visitor interface for GameObject classes.
 *
 * @author John Ericksen
 */
public interface GameObjectVisitor<T, V> {

    /**
     * Visit the Player.
     *
     * @param player visited
     * @param input additional parameter
     * @return T
     */
    T visit(Player player, V input);

    /**
     * Visit the Asteroid.
     *
     * @param asteroid visited
     * @param input additional parameter
     * @return T
     */
    T visit(Asteroid asteroid, V input);

    /**
     * Visit the Shot.
     *
     * @param shot visited
     * @param input additional parameter
     * @return T
     */
    T visit(Shot shot, V input);
}
