package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.graphics.Animation;

/**
 * Class representing a bullet from a gun.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class Bullet extends Projectile {

    /** Bullet properties. */
    public static final double VELOCITY = 60;
    public static final double RADIUS = 32;
    public static final double MAX_RANGE = 1300;

    /** Bullet animation. */
    private Animation animation;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param angle shot at
     * @param animation animation
     */
    public Bullet(Context context, double basePositionX, double basePositionY, double angle, Animation animation) {
        super(basePositionX, basePositionY, RADIUS, angle, MAX_RANGE);
        this.animation = animation;
    }

    /**
     * Updates the bullet position based on it's velocity.
     */
    @Override
    public void update() {
        super.move(VELOCITY);
    }

    /**
     * Draws the bullet.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }
}
