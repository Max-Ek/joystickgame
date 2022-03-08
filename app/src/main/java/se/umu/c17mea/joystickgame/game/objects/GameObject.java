package se.umu.c17mea.joystickgame.game.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class GameObject {

    protected double basePositionX, basePositionY;
    protected double baseRadius;

    protected Paint basePaint;

    GameObject(Context context, double basePositionX, double basePositionY,
                         double baseRadius) {
        this.basePositionX = basePositionX;
        this.basePositionY = basePositionY;
        this.baseRadius = baseRadius;
        basePaint = new Paint();
    }

    /**
     * Draws a placeholder red circle.
     * @param canvas canvas
     */
    public void draw(Canvas canvas) {
        basePaint.setColor(Color.RED);
        canvas.drawCircle((float) basePositionX, (float) basePositionY, 100, basePaint);
    }

    public double getBasePositionX() {
        return basePositionX;
    }

    public double getBasePositionY() {
        return basePositionY;
    }
}
