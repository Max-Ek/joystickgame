package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Rect;

import java.util.Random;

import se.umu.c17mea.joystickgame.game.graphics.AnimationFactory;

public class EnemyFactory {

    private final Rect spawnArea;
    private final Random random;
    private final Context context;
    private final AnimationFactory animationFactory;

    public EnemyFactory(Context context, Rect spawnArea, AnimationFactory animationFactory) {
        this.spawnArea = spawnArea;
        this.context = context;
        this.animationFactory = animationFactory;
        random = new Random();
    }

    public Enemy randomPositionEnemy() {
        int randomX = random.nextInt(spawnArea.right - spawnArea.left + 1) + spawnArea.left;
        int randomY = random.nextInt(spawnArea.bottom - spawnArea.top + 1) + spawnArea.top;

        return new Enemy(context, randomX, randomY, animationFactory.getEnemyAnimation());
    }

}
