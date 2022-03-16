package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.graphics.Animation;
import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

/**
 * Enemy class that can detect and chase anything at point through update(x, y).
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class Enemy extends CircleObject {

    /** Enemy properties. */
    public static final int RADIUS = 64;
    public static final int DETECT_RADIUS = 500;
    public static final int MAX_VELOCITY = 8;
    private EnemyState state;

    /** Enemy animation. */
    private Animation animation;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param animation animation
     */
    protected Enemy(Context context, double basePositionX, double basePositionY, Animation animation) {
        super(basePositionX, basePositionY, RADIUS);
        state = EnemyState.IDLE;
        this.animation = animation;
    }

    /**
     * If point (x,y) is within detect radius, chases it.
     * @param x position
     * @param y position
     */
    public void update(double x, double y) {
        if (detects(x, y)) {
            move(x, y);
            state = EnemyState.BATTLE;
        } else {
            state = EnemyState.IDLE;
        }
    }

    /**
     * Moves towards point (x,y).
     * @param x position
     * @param y position
     */
    private void move(double x, double y) {
        double[] vec = VectorUtil.toVector(basePositionX, basePositionY, x, y);
        double[] vecNormalized = VectorUtil.normalize(vec[0], vec[1]);
        basePositionX += vecNormalized[0]*MAX_VELOCITY;
        basePositionY += vecNormalized[1]*MAX_VELOCITY;
    }

    /**
     * See if point (x,y) is within the detection radius.
     * @param x position
     * @param y position
     * @return true if in range, else false.
     */
    private boolean detects(double x, double y) {
        return distance(x,y) < DETECT_RADIUS;
    }

    /**
     * Gets the enemy state.
     * @return state
     */
    public EnemyState getState() {
        return state;
    }

    /**
     * Draws the enemy.
     * @param canvas to draw on
     */
    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }
}
