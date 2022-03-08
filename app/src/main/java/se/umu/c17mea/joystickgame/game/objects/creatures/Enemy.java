package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class Enemy extends CircleObject {

    private static final int RADIUS = 50;
    private static final int DETECT_RADIUS = 500;
    private static final int MAX_VELOCITY = 4;

    protected Enemy(Context context, double basePositionX, double basePositionY) {
        super(basePositionX, basePositionY, RADIUS);
    }

    public void update(double x, double y) {
        if (detects(x, y)) {
            move(x, y);
        }
    }

    private void move(double x, double y) {
        double[] vec = VectorUtil.toVector(basePositionX, basePositionY, x, y);
        double[] vecNormalized = VectorUtil.vectorNormalize(vec[0], vec[1]);
        basePositionX += vecNormalized[0]*MAX_VELOCITY;
        basePositionY += vecNormalized[1]*MAX_VELOCITY;
    }

    private boolean detects(double x, double y) {
        return distance(x,y) < DETECT_RADIUS;
    }
}
