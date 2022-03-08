package se.umu.c17mea.joystickgame.game.objects.creatures;

import android.content.Context;
import android.graphics.Rect;

import java.util.Random;

public class EnemyFactory {

    private final Rect spawnArea;
    private final Random random;
    private final Context context;

    public EnemyFactory(Context context, Rect spawnArea) {
        this.spawnArea = spawnArea;
        this.context = context;
        random = new Random();
    }

    public Enemy randomPositionEnemy() {
        int randomX = random.nextInt(spawnArea.right - spawnArea.left + 1) + spawnArea.left;
        int randomY = random.nextInt(spawnArea.bottom - spawnArea.top + 1) + spawnArea.top;

        return new Enemy(context, randomX, randomY);
    }

}
