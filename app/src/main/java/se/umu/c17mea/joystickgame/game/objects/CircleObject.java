package se.umu.c17mea.joystickgame.game.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class CircleObject extends GameObject {

    private static final int DEFAULT_COLOR = Color.RED;

    protected double radius;
    protected Paint paint;

    protected CircleObject(double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY);
        this.paint = new Paint();
        this.radius = radius;
        paint.setColor(DEFAULT_COLOR);
    }

    protected CircleObject(double basePositionX, double basePositionY, double radius, int color) {
        super(basePositionX, basePositionY);
        this.radius = radius;
        this.paint = new Paint();
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) basePositionX, (float) basePositionY, (float) radius, paint);
    }

    public boolean collides(double x, double y) {
        return (distance(x, y) < radius);
    }

    public boolean collides(CircleObject circleObject) {
        return distance(circleObject) < circleObject.getRadius() + radius;
    }

    public double getRadius() {
        return radius;
    }
}
