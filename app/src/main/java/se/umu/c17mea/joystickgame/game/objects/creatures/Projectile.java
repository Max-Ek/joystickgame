package se.umu.c17mea.joystickgame.game.objects.creatures;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

/**
 * Super class for projectiles.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public abstract class Projectile extends CircleObject {

    /** DEFAULT PROPERTIES. */
    public static final double DEFAULT_VELOCITY = 40;
    public static final double DEFAULT_MAX_RANGE = 1000;

    /** Projectile properties. */
    private double range;
    private double angle;
    private double travelDistance = 0;

    /**
     * Constructor.
     * @param basePositionX position
     * @param basePositionY position
     * @param radius size
     * @param angle direction of projectile
     * @param range before it stops
     */
    protected Projectile(double basePositionX, double basePositionY, double radius, double angle,
                         double range) {
        super(basePositionX, basePositionY, radius);
        this.angle = angle;
        this.range = range;
    }

    /**
     * Updates the projectile, moves it with default velocity.
     */
    public void update() {
        move(DEFAULT_VELOCITY);
    }

    /**
     * Moves the projectile in its direction, with specified velocity.
     * @param velocity projectile speed
     */
    protected void move(double velocity) {
        double[] vec = VectorUtil.toVector(angle);
        double[] vecNormalized = VectorUtil.normalize(vec[0], vec[1]);
        basePositionX += velocity * vecNormalized[0];
        basePositionY += velocity * vecNormalized[1];
        travelDistance += velocity;
    }

    /**
     * Checks if the projectile has reached its range.
     * @return boolean
     */
    public boolean shouldStop() {
        return travelDistance >= range;
    }

    /**
     * Gets the direction of the projectile in radians.
     * @return radians
     */
    public double getAngle() {
        return angle;
    }
}
