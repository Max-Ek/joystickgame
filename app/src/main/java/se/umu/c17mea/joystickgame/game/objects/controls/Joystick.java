package se.umu.c17mea.joystickgame.game.objects.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

/**
 * Class representing a virtual joystick.
 */
public class Joystick extends ControlObject {

    /** Size of the inner circle. */
    public final static double innerRadius = 50;

    /** State of the joystick */
    private double innerPosX, innerPosY;
    private double actuatorX, actuatorY;

    /** Paint */
    private final Paint innerPaint;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param outerRadius size
     */
    public Joystick(Context context, double basePositionX, double basePositionY, double outerRadius) {
        super(basePositionX, basePositionY, outerRadius,
                ContextCompat.getColor(context, R.color.joystick_outer));
        innerPosX = basePositionX;
        innerPosY = basePositionY;

        paint.setAlpha(256/2);
        innerPaint = new Paint();
        innerPaint.setColor(ContextCompat.getColor(context, R.color.joystick_inner));
    }

    /**
     * Draws the joystick.
     * @param canvas to draw on
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) basePositionX, (float) basePositionY, (float) radius, paint);
        canvas.drawCircle((float) innerPosX, (float) innerPosY, (float) innerRadius, innerPaint);
    }

    /**
     * Sets the joystick position.
     * Actuators will be set to a value between -1 and 1.
     * @param x position
     * @param y position
     */
    public void setActuatorPosition(double x, double y) {

        double distance = this.distance(x, y);
        double[] vec = VectorUtil.toVector(basePositionX, basePositionY, x, y);

        if (distance > radius) { // If outside the boundary.
            // Distance will always be >= the vector components.
            actuatorX = vec[0] / distance;
            actuatorY = vec[1] / distance;
        } else { // Inside the boundary.
            // Radius will always be >= the vector components
            actuatorX = vec[0] / radius;
            actuatorY = vec[1] / radius;
        }
    }

    /**
     * Updates the inner circle position based on the actuator values.
     */
    public void updateInnerPosition() {
        innerPosX = basePositionX + actuatorX * radius;
        innerPosY = basePositionY + actuatorY * radius;
    }

    /**
     * Sets the pressed variable (and actuator, inner position).
     * @param bool pressed
     */
    @Override
    public void setPressed(boolean bool) {
        pressed = bool;
        if (!pressed) {
            actuatorX = 0;
            actuatorY = 0;
            innerPosX = basePositionX;
            innerPosY = basePositionY;
        }
    }

    /**
     * Get's the actuator X value scaled -1 to 1.
     * @return -1 to 1
     */
    public double getActuatorX() {
        return actuatorX;
    }

    /**
     * Get's the actuator Y value scaled -1 to 1.
     * @return -1 to 1
     */
    public double getActuatorY() {
        return actuatorY;
    }
}
