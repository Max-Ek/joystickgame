package se.umu.c17mea.joystickgame.game.objects.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;

public class Button extends ControlObject {

    private final Paint pressedPaint;

    public Button(Context context, double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY, radius,
                ContextCompat.getColor(context, R.color.shoot_button));
        pressedPaint = new Paint();
        paint.setAlpha(255/2);
        pressedPaint.setColor(ContextCompat.getColor(context, R.color.shoot_button_pressed));
    }

    @Override
    public void draw(Canvas canvas) {
        if (pressed) {
            canvas.drawCircle(
                    (float) basePositionX,
                    (float) basePositionY,
                    (float) radius,
                    pressedPaint
            );
        } else {
            canvas.drawCircle(
                    (float) basePositionX,
                    (float) basePositionY,
                    (float) radius,
                    paint);
        }
    }

}
