package se.umu.c17mea.joystickgame.game.objects.controls;

import se.umu.c17mea.joystickgame.game.objects.CircleObject;

public abstract class ControlObject extends CircleObject {

    protected boolean pressed;

    ControlObject(double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY, radius);
    }

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
