package se.umu.c17mea.joystickgame.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.utils.DistanceUtil;

public class Player extends GameObject {

    private static final int RADIUS = 30;
    private static final int MAX_VELOCITY = 5;

    private double velocity;

    private final Paint paint;

    public Player(Context context, double posX, double posY) {
        super(context, posX, posY, RADIUS);
        this.paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.player));
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) basePositionX, (float) basePositionY, RADIUS, paint);
    }

    /**
     * Attempts to move in the direction specified.
     * @param actuatorX -1 to 1
     * @param actuatorY -1 to 1
     */
    public void move(double actuatorX, double actuatorY) {

        // Avoid exceeding MAX_VELOCITY for diagonal movement.
        if (Math.abs(actuatorX) + Math.abs(actuatorY) > 1) {
            double[] res = DistanceUtil.vectorNormalize(actuatorX, actuatorY);
            actuatorX = res[0];
            actuatorY = res[1];
        }

        double velocityX = actuatorX*MAX_VELOCITY;
        double velocityY = actuatorY*MAX_VELOCITY;
        basePositionX += velocityX;
        basePositionY += velocityY;
        velocity = Math.abs(velocityX)+Math.abs(velocityY);
    }

    /**
     * Moves in the assigned direction,
     * NOTE: only moves at MAX_VELOCITY.
     * @param actuatorX -1 to 1
     * @param actuatorY -1 to 1
     */
    public void move_vector_normalized(float actuatorX, float actuatorY) {
        float sum = Math.abs(actuatorX) + Math.abs(actuatorY);
        basePositionX += (actuatorX/sum)*MAX_VELOCITY;
        basePositionY += (actuatorY/sum)*MAX_VELOCITY;
        velocity = (Math.abs(actuatorX)/sum + Math.abs(actuatorY)/sum)*MAX_VELOCITY;
    }

    public double getVelocity() {
        return velocity;
    }

    public void resetVelocity() {
        velocity = 0;
    }
}
