package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Rect;

import java.util.Random;

import se.umu.c17mea.joystickgame.game.graphics.AnimationFactory;

/**
 * Class for creating enemies.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class EnemyFactory {

    /** Area to spawn in. */
    private final Rect spawnArea;

    /** Necessary for creating enemies. */
    private final AnimationFactory animationFactory;
    private final Context context;

    /** For randomized position spawns. */
    private final Random random;

    /**
     * Constructor.
     * @param context for resources
     * @param spawnArea for randomized spawns
     * @param animationFactory supply animations to enemy objects
     */
    public EnemyFactory(Context context, Rect spawnArea, AnimationFactory animationFactory) {
        this.spawnArea = spawnArea;
        this.context = context;
        this.animationFactory = animationFactory;
        random = new Random();
    }

    /**
     * Creates an enemy with a randomized position within the spawn area.
     * @return enemy
     */
    public Enemy randomPositionEnemy() {
        int randomX = random.nextInt(spawnArea.right - spawnArea.left + 1) + spawnArea.left;
        int randomY = random.nextInt(spawnArea.bottom - spawnArea.top + 1) + spawnArea.top;

        return new Enemy(context, randomX, randomY, animationFactory.getEnemyAnimation());
    }

}
