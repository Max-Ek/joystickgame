package se.umu.c17mea.joystickgame.game.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Super class for any circle formed game object.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public abstract class CircleObject extends GameObject {

    /** Radius of the object. */
    protected double radius;

    /** Optional: paint for the object. */
    protected Paint paint;

    /**
     * Constructor.
     * @param basePositionX position
     * @param basePositionY position
     * @param radius circle size
     */
    protected CircleObject(double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY);
        this.radius = radius;
    }

    /**
     * Constructor with color.
     * @param basePositionX position
     * @param basePositionY position
     * @param radius circle size
     * @param color circle color
     */
    protected CircleObject(double basePositionX, double basePositionY, double radius, int color) {
        super(basePositionX, basePositionY);
        this.radius = radius;
        this.paint = new Paint();
        paint.setColor(color);
    }

    /**
     * Draws a circle with the paint.
     * @param canvas to draw on
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) basePositionX, (float) basePositionY, (float) radius, paint);
    }

    /**
     * Checks if the circle collides with point (x,y).
     * @param x position
     * @param y position
     * @return true if collides, else false.
     */
    public boolean collides(double x, double y) {
        return (distance(x, y) < radius);
    }

    /**
     * Checks if this CircleObject collides with another CircleObject.
     * @param circleObject to check collision with
     * @return true if collides, else false.
     */
    public boolean collides(CircleObject circleObject) {
        return distance(circleObject) < circleObject.getRadius() + radius;
    }

    /**
     * Gets the radius of this CircleObject.
     * @return radius
     */
    public double getRadius() {
        return radius;
    }
}
