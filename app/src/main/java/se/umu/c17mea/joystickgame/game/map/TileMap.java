package se.umu.c17mea.joystickgame.game.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import se.umu.c17mea.joystickgame.game.graphics.DisplayWindow;
import se.umu.c17mea.joystickgame.game.graphics.Sprite;

public class TileMap {

    /** Tile types */

    /** Tile size */
    public static final int TILE_SIZE = 32;
    public static final int SCALING = 10;

    /** Amount of tiles */
    public static final int MAP_WIDTH = 8;
    public static final int MAP_HEIGHT = 8;

    private int[][] layout;
    private final Sprite[] sprites;
    private Bitmap bitmap;

    private Rect dest;

    public TileMap(Sprite[] sprites) {
        this.sprites = sprites;
        this.dest = new Rect();
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

    public void draw(Canvas canvas, DisplayWindow displayWindow) {
        canvas.drawBitmap(
                bitmap,
                -displayWindow.getRect().left,
                -displayWindow.getRect().top,
                null
        );
    }

    public Rect getSize() {
        return new Rect(
                0,
                0,
                TILE_SIZE * MAP_WIDTH * SCALING,
                TILE_SIZE * MAP_HEIGHT * SCALING
        );
    }

    public int getTileType(int x, int y) {
        return (layout[y/SCALING/TILE_SIZE][x/SCALING/TILE_SIZE]);
    }

    public boolean insideMap(int x, int y) {
        return getSize().contains(x, y);
    }

    public boolean onIce(int x, int y) {
        return getTileType(x,y) == 1;
    }

}
