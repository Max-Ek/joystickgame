package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.graphics.Animation;
import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class Player extends CircleObject {

    public static final int RADIUS = 64;
    public static final int MAX_VELOCITY = 16;


    private final Animation animation;
    private static final int WALK_ANIMATION_RATE = 10;
    private int walkAnimationTimer = 0;
    private boolean openMouth;

    private PlayerState state;
    private static final int FIRING_RATE = 15;
    private long shootTimer = 0;

    private double velocityX;
    private double velocityY;
    private double direction;

    public Player(Context context, double basePositionX, double basePositionY, Animation animation) {
        super(basePositionX, basePositionY, RADIUS, ContextCompat.getColor(context, R.color.player));
        state = PlayerState.IDLE;
        this.animation = animation;
    }

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

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }

    /**
     * Attempts to move in the direction specified.
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

        velocityX = actuatorX*MAX_VELOCITY;
        velocityY = actuatorY*MAX_VELOCITY;
        basePositionX += velocityX;
        basePositionY += velocityY;
        direction = VectorUtil.vectorAngle(velocityX, velocityY);
        state = PlayerState.MOVING;
    }

    public boolean shoot() {
        if (shootTimer >= FIRING_RATE) {
            shootTimer = 0;
            return true;
        }
        return false;
    }

    public void resetVelocity() {
        velocityX = 0;
        velocityY = 0;
        state = PlayerState.IDLE;
        walkAnimationTimer = 0;
    }

    public double getDirection() {
        return direction;
    }

    public PlayerState getState() {
        return state;
    }

    public boolean openMouth() {
        return openMouth;
    }
}
