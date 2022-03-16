package se.umu.c17mea.joystickgame.game.objects.controls;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;

/**
 * Super class for game control objects that are formed like a circle.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public abstract class ControlObject extends CircleObject {

    /** If the object is pressed. */
    protected boolean pressed;

    /**
     * Constructor.
     * @param basePositionX position
     * @param basePositionY position
     * @param radius size
     */
    ControlObject(double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY, radius);
    }

    /**
     * Constructor with color.
     * @param basePositionX position
     * @param basePositionY position
     * @param radius size
     * @param color color
     */
    protected ControlObject(double basePositionX, double basePositionY, double radius, int color) {
        super(basePositionX, basePositionY, radius, color);
    }

    /**
     * Checks if the ControlObject is pressed, given coordinates.
     * @param pressedPositionX position
     * @param pressedPositionY position
     * @return True if pressed, else false.
     */
    public boolean isPressed(double pressedPositionX, double pressedPositionY) {
        return distance(pressedPositionX, pressedPositionY) < radius;
    }

    /**
     * Sets the pressed instance variable.
     */
    public void setPressed(boolean bool) {
        pressed = bool;
    }

    /**
     * Gets the pressed instance variable.
     * @return pressed
     */
    public boolean getPressed() {
        return pressed;
    }
}
