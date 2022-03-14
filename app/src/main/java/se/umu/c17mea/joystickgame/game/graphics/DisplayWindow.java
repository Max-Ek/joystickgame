package se.umu.c17mea.joystickgame.game.graphics;

import android.graphics.Rect;

import se.umu.c17mea.joystickgame.game.objects.creatures.Player;

public class DisplayWindow {

    private final Rect sizeRect;
    private Rect displayRect;

    public DisplayWindow(int width, int height) {
        this.displayRect = new Rect(0, 0, width, height);
        sizeRect = new Rect(0, 0, width, height);
    }

    public void update(Player player) {
        displayRect.offsetTo(
                (int) player.getBasePositionX() - (displayRect.width() / 2),
                (int) player.getBasePositionY() - (displayRect.height() / 2)
        );
    }

    public Rect getRect() {
        return displayRect;
    }

    public Rect getSizeRect() {
        return sizeRect;
    }
}
