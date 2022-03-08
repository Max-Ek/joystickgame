package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.objects.CircleObject;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

public class Player extends CircleObject {

    private static final int RADIUS = 30;
    private static final int MAX_VELOCITY = 7;

    private double velocity;

    public Player(Context context, double basePositionX, double basePositionY) {
        super(basePositionX, basePositionY, RADIUS, ContextCompat.getColor(context, R.color.player));
    }

    /**
     * Attempts to move in the direction specified.
     * @param actuatorX -1 to 1
     * @param actuatorY -1 to 1
     */
    public void move(double actuatorX, double actuatorY) {

        // Avoid exceeding MAX_VELOCITY for diagonal movement.
        if (Math.abs(actuatorX) + Math.abs(actuatorY) > 1) {
            double[] res = VectorUtil.vectorNormalize(actuatorX, actuatorY);
            actuatorX = res[0];
            actuatorY = res[1];
        }

        double velocityX = actuatorX*MAX_VELOCITY;
        double velocityY = actuatorY*MAX_VELOCITY;
        basePositionX += velocityX;
        basePositionY += velocityY;
        velocity = Math.abs(velocityX)+Math.abs(velocityY);
    }

    public double getVelocity() {
        return velocity;
    }

    public void resetVelocity() {
        velocity = 0;
    }
}
