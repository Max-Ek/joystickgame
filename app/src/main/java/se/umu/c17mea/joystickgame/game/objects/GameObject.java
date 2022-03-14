package se.umu.c17mea.joystickgame.game.objects;

import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.utils.VectorUtil;

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
        return VectorUtil.euclideanDistance(
                this.basePositionX,
                this.basePositionY,
                x,
                y
        );
    }

    public double distance(GameObject gameObject) {
        return VectorUtil.euclideanDistance(
                this.basePositionX,
                this.basePositionY,
                gameObject.getBasePositionX(),
                gameObject.getBasePositionY()
        );
    }
}
