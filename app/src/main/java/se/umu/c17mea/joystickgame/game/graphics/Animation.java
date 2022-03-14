package se.umu.c17mea.joystickgame.game.graphics;

import android.graphics.Canvas;

import se.umu.c17mea.joystickgame.game.objects.creatures.Enemy;
import se.umu.c17mea.joystickgame.game.objects.creatures.Player;
import se.umu.c17mea.joystickgame.game.objects.creatures.Projectile;

public class Animation {

    private final Sprite[] sprites;

    public Animation(Sprite[] sprites) {
        this.sprites = sprites;
    }

    /**
     * Draws the player animation.
     * @param canvas to draw on
     * @param player to draw
     */
    public void draw(Canvas canvas, Player player) {
        int x = (int) player.getBasePositionX();
        int y = (int) player.getBasePositionY();
        double radians = player.getDirection();
        float degrees = (float) (radians * 180 / Math.PI);

        switch (player.getState()) {
            case IDLE:
                sprites[0].drawCenteredRotated(canvas, x, y, degrees);
                break;
            case MOVING:
                if (player.openMouth()) {
                    sprites[2].drawCenteredRotated(canvas, x, y, degrees);
                } else {
                    sprites[1].drawCenteredRotated(canvas, x, y, degrees);
                }
                break;
            default:
                sprites[3].drawCenteredRotated(canvas, x, y, degrees);
        }
    }

    public void draw(Canvas canvas, Enemy enemy) {
        switch (enemy.getState()) {
            case IDLE:
                sprites[0].drawCentered(canvas, (int) enemy.getBasePositionX(), (int) enemy.getBasePositionY());
                break;
            case BATTLE:
                sprites[1].drawCentered(canvas, (int) enemy.getBasePositionX(), (int) enemy.getBasePositionY());
                break;
        }
    }

    public void draw(Canvas canvas, Projectile projectile) {
        int x = (int) projectile.getBasePositionX();
        int y = (int) projectile.getBasePositionY();
        double radians = projectile.getAngle();
        float degrees = (float) (radians * 180 / Math.PI);
        sprites[0].drawCenteredRotated(canvas, x, y, degrees);
    }

}
