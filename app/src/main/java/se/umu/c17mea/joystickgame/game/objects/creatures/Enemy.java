package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.graphics.Animation;
import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class Enemy extends CircleObject {

    public static final int RADIUS = 64;
    public static final int DETECT_RADIUS = 500;
    public static final int MAX_VELOCITY = 8;

    private EnemyState state;
    private Animation animation;

    protected Enemy(Context context, double basePositionX, double basePositionY, Animation animation) {
        super(basePositionX, basePositionY, RADIUS);
        state = EnemyState.IDLE;
        this.animation = animation;
    }

    public void update(double x, double y) {
        if (detects(x, y)) {
            move(x, y);
            state = EnemyState.BATTLE;
        } else {
            state = EnemyState.IDLE;
        }
    }

    private void move(double x, double y) {
        double[] vec = VectorUtil.toVector(basePositionX, basePositionY, x, y);
        double[] vecNormalized = VectorUtil.normalize(vec[0], vec[1]);
        basePositionX += vecNormalized[0]*MAX_VELOCITY;
        basePositionY += vecNormalized[1]*MAX_VELOCITY;
    }

    private boolean detects(double x, double y) {
        return distance(x,y) < DETECT_RADIUS;
    }

    public EnemyState getState() {
        return state;
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas, this);
    }
}
