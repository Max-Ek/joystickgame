package se.umu.c17mea.joystickgame.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Class for drawing sprites from a bitmap.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class Sprite {

    /** Bitmap of the sprite sheet. */
    private final Bitmap bitmap;

    /** Where on the sprite sheet. */
    private final Rect src;

    /** Where to draw on screen. */
    private Rect dest;

    /**
     * Constructor
     * @param bitmap the sprite sheet
     * @param rect what part of the sprite sheet
     */
    public Sprite(Bitmap bitmap, Rect rect) {
        this.bitmap = bitmap;
        this.src = rect;
        dest = new Rect();
    }

    /**
     * Draws the sprite on the canvas.
     * @param canvas to draw on
     * @param x base position
     * @param y base position
     */
    public void draw(Canvas canvas, int x, int y) {
        dest.set(
                x,
                y,
                x+src.width(),
                y+src.height()
        );

        canvas.drawBitmap(
                bitmap,
                src,
                dest,
                null
        );
    }

    /**
     * Draws the sprite centered on the canvas position (x,y).
     * @param canvas to draw on
     * @param x center position
     * @param y center position
     */
    public void drawCentered(Canvas canvas, int x, int y) {
        dest.set(
                x - (src.width() / 2),
                y - (src.height() / 2),
                x + (src.width() / 2),
                y + (src.height() / 2)
        );

        canvas.drawBitmap(
                bitmap,
                src,
                dest,
                null
        );
    }

    /**
     * Draws the sprite centered and rotated around (x,y).
     * @param canvas to draw on
     * @param x center position
     * @param y center position
     * @param degrees rotation
     */
    public void drawCenteredRotated(Canvas canvas, int x, int y, float degrees) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            canvas.save();
        canvas.rotate(degrees, x, y);

        dest.set(
                x - (src.width() / 2),
                y - (src.height() / 2),
                x + (src.width() / 2),
                y + (src.height() / 2)
        );

        canvas.drawBitmap(
                bitmap,
                src,
                dest,
                null
        );
        canvas.restore();
    }

}
