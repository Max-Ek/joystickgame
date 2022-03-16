package se.umu.c17mea.joystickgame.game.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import se.umu.c17mea.joystickgame.game.graphics.DisplayWindow;
import se.umu.c17mea.joystickgame.game.graphics.Sprite;

/**
 * Class representing the background map in the game.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class TileMap {

    /** Tile types. */
    public static final int TYPE_GROUND = 0;
    public static final int TYPE_ICE = 1;

    /** Tile size. */
    public static final int TILE_SIZE = 32;
    public static final int SCALING = 10;

    /** Amount of tiles. */
    public static final int MAP_WIDTH = 8;
    public static final int MAP_HEIGHT = 8;

    /** Map layout. */
    private int[][] layout;

    /** Sprites. */
    private final Sprite[] sprites;
    private Bitmap bitmap;

    public TileMap(Sprite[] sprites) {
        this.sprites = sprites;
        init();
    }

    /**
     * Initiates the layout
     */
    private void initLayout() {
        layout = new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,1,1,1,0,0,0},
                {0,0,0,1,0,0,0,0},
                {0,1,1,1,0,0,0,0},
                {0,1,0,0,0,0,0,0},
                {0,1,0,0,1,0,0,0},
                {0,0,0,1,1,0,0,0},
                {0,0,0,1,0,0,0,0}
        };
    }

    /**
     * Initializes the map to a bitmap.
     * Scales it with SCALING.
     */
    private void init() {

        initLayout();
        bitmap = Bitmap.createBitmap(
                MAP_HEIGHT * TILE_SIZE,
                MAP_WIDTH * TILE_SIZE,
                Bitmap.Config.RGB_565
        );

        Canvas mapCanvas = new Canvas(bitmap);

        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                int typeIndex = layout[row][col];
                sprites[typeIndex].draw(mapCanvas, col*TILE_SIZE, row*TILE_SIZE);
            }
        }
        bitmap = Bitmap.createScaledBitmap(bitmap,
                bitmap.getWidth()*SCALING,
                bitmap.getHeight()*SCALING,
                false
        );
    }

    /**
     * Draws the map.
     * @param canvas to draw on
     * @param displayWindow part to draw
     */
    public void draw(Canvas canvas, DisplayWindow displayWindow) {
        canvas.drawBitmap(
                bitmap,
                -displayWindow.getRect().left,
                -displayWindow.getRect().top,
                null
        );
    }

    /**
     * Gets the size of the map.
     * @return size rect
     */
    public Rect getSize() {
        return new Rect(
                0,
                0,
                TILE_SIZE * MAP_WIDTH * SCALING,
                TILE_SIZE * MAP_HEIGHT * SCALING
        );
    }

    /**
     * Gets the tile type at position (x,y).
     * @param x position
     * @param y position
     * @return tile type
     */
    public int getTileType(int x, int y) {
        return (layout[y/SCALING/TILE_SIZE][x/SCALING/TILE_SIZE]);
    }

    /**
     * Checks if the point (x,y) is inside the map.
     * @param x position
     * @param y position
     * @return inside map bool
     */
    public boolean insideMap(int x, int y) {
        return getSize().contains(x, y);
    }

    /**
     * Checks if the point (x,y) is inside an ice tile.
     * @param x position
     * @param y position
     * @return point on ice tile bool
     */
    public boolean onIce(int x, int y) {
        return getTileType(x,y) == TYPE_ICE;
    }

}
