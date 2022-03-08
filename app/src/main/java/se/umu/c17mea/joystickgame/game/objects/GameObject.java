package se.umu.c17mea.joystickgame.game.objects;

import android.graphics.Canvas;

public abstract class GameObject {

    protected double basePositionX, basePositionY;

    GameObject(double basePositionX, double basePositionY) {
        this.basePositionX = basePositionX;
        this.basePositionY = basePositionY;
    }

    public abstract void draw(Canvas canvas);

    public double getBasePositionX() {
        return basePositionX;
    }

    public double getBasePositionY() {
        return basePositionY;
    }

    public double distance(double x, double y) {
        return Math.sqrt(Math.pow(x - basePositionX, 2) + Math.pow(y - basePositionY, 2));
    }

    public double distance(GameObject gameObject) {
        return Math.sqrt(
                Math.pow(gameObject.getBasePositionX() - basePositionX, 2)
                + Math.pow(gameObject.getBasePositionY() - basePositionY, 2)
        );
    }
}
