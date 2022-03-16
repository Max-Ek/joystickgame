package se.umu.c17mea.joystickgame.game.graphics;

import android.content.Context;

/**
 * Class for retrieving animations.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class AnimationFactory {

    /** Sprite sheet to get sprites from */
    private final SpriteSheet spriteSheet;

    /**
     * Constructor.
     * @param context context
     * @param spriteSheet for retrieving sprites
     */
    public AnimationFactory(Context context, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    /**
     * Gets the player animation.
     * @return player animation
     */
    public Animation getPlayerAnimation() {
        return new Animation(spriteSheet.getPlayerSprites());
    }

    /**
     * Gets the enemy animation.
     * @return enemy animation
     */
    public Animation getEnemyAnimation() {
        return new Animation(spriteSheet.getEnemySprites());
    }

    /**
     * Gets the bullet animation.
     * @return bullet animation
     */
    public Animation getBulletAnimation() {
        return new Animation(spriteSheet.getBulletSprites());
    }
}
