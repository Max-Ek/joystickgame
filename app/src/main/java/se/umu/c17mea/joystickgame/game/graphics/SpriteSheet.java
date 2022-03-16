package se.umu.c17mea.joystickgame.game.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import se.umu.c17mea.joystickgame.R;

/**
 * Class for retrieving sprites.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class SpriteSheet {

    /** Sprite sheet info. */
    public static final int cols = 20;
    public static final int rows = 20;
    public static final int blockSize = 32;

    /** Bitmap of the sprite sheet. */
    private Bitmap bitmap;

    /**
     * Constructor.
     * @param context to get resource from
     */
    public SpriteSheet(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, options);
    }

    /**
     * Gets the player sprites.
     * @return sprites
     */
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

    /**
     * Gets the enemy sprites.
     * @return sprites
     */
    public Sprite[] getEnemySprites() {
        Sprite idle = getBlock(0, 4, 4, 4);
        Sprite mouthClosed = getBlock(4, 4, 4, 4);

        return new Sprite[] {
                idle,
                mouthClosed,
        };
    }

    /**
     * Gets the bullet sprites.
     * @return sprites
     */
    public Sprite[] getBulletSprites() {
        Sprite b1 = getBlock(0, 12, 2, 2);
        return new Sprite[] {b1};
    }

    /**
     * Gets the tile map sprites.
     * @return sprites
     */
    public Sprite[] getTileSprites() {
        Sprite ground = getBlock(19, 19, 1, 1);
        Sprite ice = getBlock(18, 19, 1, 1);
        return new Sprite[] {ground, ice};
    }

    /**
     * Builds a sprite from a block of tiles.
     * @param col tile index
     * @param row tile index
     * @param blockWidth width (number of tiles)
     * @param blockHeight height (number of tiles)
     * @return sprite
     */
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
