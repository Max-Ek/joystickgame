package se.umu.c17mea.joystickgame.game.graphics;

import android.graphics.Rect;

import se.umu.c17mea.joystickgame.game.objects.creatures.Player;

/**
 * Class for keeping track of the viewport of the game.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class DisplayWindow {

    /** Rect with position 0,0. */
    private final Rect sizeRect;

    /** The actual display rect. */
    private Rect displayRect;

    /**
     * Constructor.
     * @param width of the window
     * @param height of the window
     */
    public DisplayWindow(int width, int height) {
        this.displayRect = new Rect(0, 0, width, height);
        sizeRect = new Rect(0, 0, width, height);
    }

    /**
     * Updates the viewport to center on player.
     * @param player to center on
     */
    public void update(Player player) {
        displayRect.offsetTo(
                (int) player.getBasePositionX() - (displayRect.width() / 2),
                (int) player.getBasePositionY() - (displayRect.height() / 2)
        );
    }


    /**
     * Gets the viewport rect.
     * @return viewport rect
     */
    public Rect getRect() {
        return displayRect;
    }

    /**
     * Gets the size of the viewport as a rect.
     * @return size rect
     */
    public Rect getSizeRect() {
        return sizeRect;
    }
}
