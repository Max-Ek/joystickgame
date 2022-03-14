package se.umu.c17mea.joystickgame.game.graphics;

import android.content.Context;

public class AnimationFactory {

    private final SpriteSheet spriteSheet;

    public AnimationFactory(Context context, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Animation getPlayerAnimation() {
        return new Animation(spriteSheet.getPlayerSprites());
    }

    public Animation getEnemyAnimation() {
        return new Animation(spriteSheet.getEnemySprites());
    }

    public Animation getBulletAnimation() {
        return new Animation(spriteSheet.getBulletSprites());
    }
}
