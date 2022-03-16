package se.umu.c17mea.joystickgame.game.objects.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import se.umu.c17mea.joystickgame.R;

/**
 * Class representing a button that can be pressed.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class Button extends ControlObject {

    /** Paint when pressed. */
    private final Paint pressedPaint;

    /**
     * Constructor.
     * @param context for resources
     * @param basePositionX position
     * @param basePositionY position
     * @param radius size
     */
    public Button(Context context, double basePositionX, double basePositionY, double radius) {
        super(basePositionX, basePositionY, radius,
                ContextCompat.getColor(context, R.color.shoot_button));
        pressedPaint = new Paint();
        paint.setAlpha(255/2);
        pressedPaint.setColor(ContextCompat.getColor(context, R.color.shoot_button_pressed));
    }

    /**
     * Draws the button depending on it's state.
     * @param canvas to draw on
     */
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
