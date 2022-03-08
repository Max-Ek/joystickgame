package se.umu.c17mea.joystickgame.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.utils.DistanceUtil;

public class Joystick extends GameObject {

    /** State of the joystick */
    private double innerPosX, innerPosY;
    private double actuatorX, actuatorY;
    private boolean pressed;

    /** Size of the inner circle. */
    private final static double innerRadius = 50;

    /** Paint */
    private final Paint innerPaint;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param outerRadius radius
     */
    public Joystick(Context context, double basePositionX, double basePositionY, double outerRadius) {
        super(context, basePositionX, basePositionY, outerRadius);
        innerPosX = basePositionX;
        innerPosY = basePositionY;

        basePaint.setColor(ContextCompat.getColor(context, R.color.joystick_outer));
        innerPaint = new Paint();
        innerPaint.setColor(ContextCompat.getColor(context, R.color.joystick_inner));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) basePositionX, (float) basePositionY, (float) baseRadius, basePaint);
        canvas.drawCircle((float) innerPosX, (float) innerPosY, (float) innerRadius, innerPaint);
    }

    /**
     * Checks if the joystick is pressed, and sets the pressed instance variable.
     * @param pressedPositionX joystick position
     * @param pressedPositionY joystick position
     * @return True if pressed, else false.
     */
    public boolean isPressed(double pressedPositionX, double pressedPositionY) {

        if (pressed) { // If already pressed
            return true;
        }

        double distance = DistanceUtil.distance(
                pressedPositionX,
                basePositionX,
                pressedPositionY,
                basePositionY
        );

        boolean steer = distance < baseRadius;
        if (steer) {
            pressed = true;
        }

        return steer;
    }

    /**
     * Sets the joystick position.
     * @param x position
     * @param y position
     */
    public void setActuatorPosition(double x, double y) {

        double distance = DistanceUtil.distance(x, basePositionX, y, basePositionY);

        double deltaX = x - basePositionX;
        double deltaY = y - basePositionY;

        if (distance > baseRadius) { // If outside the boundary.
            actuatorX = deltaX/distance;
            actuatorY = deltaY/distance;
        } else { // Inside the boundary.
            actuatorX = deltaX / baseRadius;
            actuatorY = deltaY / baseRadius;
        }
    }

    public void updateInnerPosition() {
        innerPosX = basePositionX + actuatorX*baseRadius;
        innerPosY = basePositionY + actuatorY*baseRadius;
    }

    /**
     * Resets the joystick position to default.
     */
    public void resetActuatorPosition() {
        innerPosX = basePositionX;
        innerPosY = basePositionY;
        actuatorX = 0;
        actuatorY = 0;
        pressed = false;
    }

    /**
     * Gets the pressed instance variable.
     * @return pressed
     */
    public boolean getIsPressed() {
        return pressed;
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
