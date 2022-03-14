package se.umu.c17mea.joystickgame.game.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import se.umu.c17mea.joystickgame.R;

public class SpriteSheet {

    public static final int cols = 20;
    public static final int rows = 20;
    public static final int blockSize = 32;

    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, options);
    }

    public Sprite[] getPlayerSprites() {
        Sprite idle = getBlock(0, 0, 4, 4);
        Sprite mouthClosed = getBlock(4, 0, 4, 4);
        Sprite mouthOpen = getBlock(8, 0, 4, 4);
        Sprite dead = getBlock(12, 0, 4, 4);

        return new Sprite[] {
                idle,
                mouthClosed,
                mouthOpen,
                dead
        };
    }

    public Sprite[] getEnemySprites() {
        Sprite idle = getBlock(0, 4, 4, 4);
        Sprite mouthClosed = getBlock(4, 4, 4, 4);

        return new Sprite[] {
                idle,
                mouthClosed,
        };
    }

    public Sprite[] getBulletSprites() {
        Sprite b1 = getBlock(0, 12, 2, 2);
        return new Sprite[] {b1};
    }

    public Sprite[] getTileSprites() {
        Sprite ground = getBlock(19, 19, 1, 1);
        Sprite ice = getBlock(18, 19, 1, 1);
        return new Sprite[] {ground, ice};
    }

    Sprite getBlock(int col, int row, int blockWidth, int blockHeight) {
        if (col >= cols || row >= rows) {
            throw new IllegalArgumentException("Outside sprite sheet");
        }

        int left = col*blockSize;
        int right = left+blockSize*blockWidth;
        int top = row*blockSize;
        int bottom = top+blockSize*blockHeight;
        return new Sprite(bitmap, new Rect(left, top, right, bottom));
    }

}
