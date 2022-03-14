package se.umu.c17mea.joystickgame.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final Bitmap bitmap;
    private final Rect src;
    private Rect dest;

    public Sprite(Bitmap bitmap, Rect rect) {
        this.bitmap = bitmap;
        this.src = rect;
        dest = new Rect();
    }

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

    public void drawScaled(Canvas canvas, int x, int y, int scale) {
        dest.set(
                x,
                y,
                (x+src.width()) * scale,
                (y+src.height()) * scale
        );

        canvas.drawBitmap(
                bitmap,
                src,
                dest,
                null
        );
    }

    public void drawCenteredScaled(Canvas canvas, int x, int y, int scale) {
        dest.set(
                (x - (src.width() / 2)) * scale,
                (y - (src.height() / 2)) * scale,
                (x + (src.width() / 2)) * scale,
                (y + (src.height() / 2)) * scale
        );

        canvas.drawBitmap(
                bitmap,
                src,
                dest,
                null
        );
    }

}
