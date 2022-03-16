package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.graphics.Animation;
import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

/**
 * Player class to control with actuators.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class Player extends CircleObject {

    /** Static properties. */
    public static final int RADIUS = 64;
    public static final int DEFAULT_MAX_VELOCITY = 16;
    private static final int FIRING_RATE = 15;

    /** Animation properties. */
    private final Animation animation;
    private static final int WALK_ANIMATION_RATE = 10;
    private int walkAnimationTimer = 0;
    private boolean openMouth;

    /** Player properties. */
    private PlayerState state;
    private long shootTimer = 0;
    private int maxVelocity = 16;
    private double velocityX;
    private double velocityY;
    private double direction;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param animation animation
     */
    public Player(Context context, double basePositionX, double basePositionY, Animation animation) {
        super(basePositionX, basePositionY, RADIUS);
        state = PlayerState.IDLE;
        this.animation = animation;
    }

    /**
     * Updates the player timers/counters.
     */
    public void update() {
        if (shootTimer < FIRING_RATE) {
            shootTimer++;
        }

        // Alternate walking animation
        if (walkAnimationTimer >= WALK_ANIMATION_RATE) {
            openMouth = false;
            walkAnimationTimer = 0;
        } else if (walkAnimationTimer >= WALK_ANIMATION_RATE / 2) {
            openMouth = true;
        }
    }

    /**
     * Draws the player.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }

    /**
     * Slides the player in the direction already facing, at maxVelocity.
     * Sets the player state to IDLE.
     */
    public void slide() {
        double[] vec = VectorUtil.toVector(direction);
        double[] vecNormalized = VectorUtil.normalize(vec[0], vec[1]);
        velocityX = vecNormalized[0] * maxVelocity;
        velocityY = vecNormalized[1] * maxVelocity;
        basePositionX += velocityX;
        basePositionY += velocityY;
        state = PlayerState.IDLE;
    }

    /**
     * Moves the player in the direction specified,
     * without exceeding max velocity.
     * @param actuatorX -1 to 1
     * @param actuatorY -1 to 1
     */
    public void move(double actuatorX, double actuatorY) {

        walkAnimationTimer++;

        // Avoid exceeding MAX_VELOCITY for diagonal movement.
        if (Math.abs(actuatorX) + Math.abs(actuatorY) > 1) {
            double[] res = VectorUtil.normalize(actuatorX, actuatorY);
            actuatorX = res[0];
            actuatorY = res[1];
        }

        velocityX = actuatorX * maxVelocity;
        velocityY = actuatorY * maxVelocity;
        basePositionX += velocityX;
        basePositionY += velocityY;
        direction = VectorUtil.vectorAngle(velocityX, velocityY);
        state = PlayerState.MOVING;
    }

    /**
     * Attempts to shoot.
     * NOTE: Will only reset the shootTimer, not actually create a bullet.
     * @return True if could shoot, else false.
     */
    public boolean shoot() {
        if (shootTimer >= FIRING_RATE) {
            shootTimer = 0;
            return true;
        }
        return false;
    }

    /**
     * Resets the player state and velocity.
     */
    public void resetVelocity() {
        velocityX = 0;
        velocityY = 0;
        state = PlayerState.IDLE;
        walkAnimationTimer = 0;
    }

    /**
     * Gets the player direction in radians.
     * @return angle in radians
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Sets the turbo mode (high maxVelocity) of the player.
     * @param turbo mode
     */
    public void setTurbo(boolean turbo) {
        if (turbo) {
            maxVelocity = DEFAULT_MAX_VELOCITY * 2;
        } else {
            maxVelocity = DEFAULT_MAX_VELOCITY;
        }
    }

    /**
     * Sets the player state.
     * @param state to set
     */
    public void setState(PlayerState state) {
        this.state = state;
    }

    /**
     * Gets the player state
     * @return player state
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Returns whether the player should have an open mouth for animation.
     * @return open mouth
     */
    public boolean openMouth() {
        return openMouth;
    }
}
