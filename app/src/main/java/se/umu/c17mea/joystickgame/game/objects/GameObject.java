package se.umu.c17mea.joystickgame.game.objects;

import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

/**
 * Standard GameObject class to inherit from.
 * Every GameObject has a base position and can be drawn on a canvas.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public abstract class GameObject {

    /** Position. */
    protected double basePositionX, basePositionY;

    /**
     * Constructor.
     * @param basePositionX position
     * @param basePositionY position
     */
    GameObject(double basePositionX, double basePositionY) {
        this.basePositionX = basePositionX;
        this.basePositionY = basePositionY;
    }

    /**
     * Draws the GameObject on the canvas.
     * @param canvas to draw on
     */
    public abstract void draw(Canvas canvas);

    /**
     * Gets the base x position.
     * @return x position
     */
    public double getBasePositionX() {
        return basePositionX;
    }

    /**
     * Gets the base y position.
     * @return y position
     */
    public double getBasePositionY() {
        return basePositionY;
    }

    /**
     * Gets the euclidean distance between this GameObject's base position and point (x,y).
     * @param x position
     * @param y position
     * @return distance
     */
    public double distance(double x, double y) {
        return VectorUtil.euclideanDistance(
                this.basePositionX,
                this.basePositionY,
                x,
                y
        );
    }

    /**
     * Gets the euclidean distance between this GameObject' base position and
     * another GameObject's base position.
     * @param gameObject to calculate distance between
     * @return distance
     */
    public double distance(GameObject gameObject) {
        return VectorUtil.euclideanDistance(
                this.basePositionX,
                this.basePositionY,
                gameObject.getBasePositionX(),
                gameObject.getBasePositionY()
        );
    }
}
