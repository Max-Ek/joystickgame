package se.umu.c17mea.joystickgame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.graphics.Animation;
import se.umu.c17mea.joystickgame.game.graphics.AnimationFactory;
import se.umu.c17mea.joystickgame.game.graphics.DisplayWindow;
import se.umu.c17mea.joystickgame.game.graphics.Sprite;
import se.umu.c17mea.joystickgame.game.graphics.SpriteSheet;
import se.umu.c17mea.joystickgame.game.map.TileMap;
import se.umu.c17mea.joystickgame.game.objects.creatures.Bullet;
import se.umu.c17mea.joystickgame.game.objects.creatures.Enemy;
import se.umu.c17mea.joystickgame.game.objects.creatures.EnemyFactory;
import se.umu.c17mea.joystickgame.game.objects.controls.Joystick;
import se.umu.c17mea.joystickgame.game.objects.controls.Button;
import se.umu.c17mea.joystickgame.game.objects.creatures.Player;
import se.umu.c17mea.joystickgame.game.objects.creatures.Projectile;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final double START_X = 1000;
    private static final double START_Y = 500;

    /** Game thread */
    private GameThread gameThread;

    /** Display Window */
    private DisplayMetrics displayMetrics;
    private DisplayWindow displayWindow;

    /** Map */
    private final TileMap tileMap;

    /** Controls */
    private final Joystick leftJoystick;
    private final Button shootButton;
    private int leftJoystickID = -1;
    private int shootButtonID = -1;

    /** Creatures */
    private final Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Projectile> projectiles;

    /** Spawns */
    private final EnemyFactory enemyFactory;
    private int spawnOnUpdate = 120;
    private int updateCount;

    /** Sprites & Animations */
    private final SpriteSheet spriteSheet;
    private final AnimationFactory animationFactory;

    public GamePanel(Context context) {
        super(context);

        // Add callback to surface holder.
        getHolder().addCallback(this);

        displayMetrics = getResources().getDisplayMetrics();
        displayWindow = new DisplayWindow(displayMetrics.widthPixels, displayMetrics.heightPixels);

        spriteSheet = new SpriteSheet(getContext());
        animationFactory = new AnimationFactory(getContext(), spriteSheet);

        tileMap = new TileMap(spriteSheet.getTileSprites());
        player = new Player(getContext(), START_X, START_Y, animationFactory.getPlayerAnimation());
        leftJoystick = new Joystick(getContext(),200, 800, 150);
        shootButton = new Button(getContext(), 1700, 800, 100);
        enemyFactory = new EnemyFactory(getContext(), new Rect(0, 0, 2000, 1000), animationFactory);
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();

        // Finally start the thread.
        gameThread = new GameThread(this, getHolder());

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameThread = new GameThread(this, getHolder());
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        try {
            gameThread.setRunning(false);
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        int actionIndex = event.getActionIndex();
        double pressedX = event.getX(actionIndex);
        double pressedY = event.getY(actionIndex);
        int eventPointerID = event.getPointerId(actionIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!leftJoystick.getPressed()) { // If not pressed already.
                    if (leftJoystick.isPressed(pressedX, pressedY)) { // Check it.
                        leftJoystick.setPressed(true);
                        leftJoystickID = eventPointerID;
                    }
                }
                if (!shootButton.getPressed()) { // If not pressed already.
                    if (shootButton.isPressed(pressedX, pressedY)) { // Check it.
                        shootButton.setPressed(true);
                        shootButtonID = eventPointerID;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // CAUTION: actionIndex is always 0 for this event.
                if (leftJoystick.getPressed()) {
                    // If multi-touched move, the x & y values have to be looked up.
                    if (pointerCount > 1) {
                        pressedX = event.getX(event.findPointerIndex(leftJoystickID));
                        pressedY = event.getY(event.findPointerIndex(leftJoystickID));
                    }
                    leftJoystick.setActuatorPosition(pressedX, pressedY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (eventPointerID == leftJoystickID) {
                    leftJoystick.setPressed(false);
                    leftJoystickID = -1;
                } else if (eventPointerID == shootButtonID) {
                    shootButton.setPressed(false);
                    shootButtonID = -1;
                }
                break;
        }
        return true;
    }

    public void updateGame() {

        // Spawn enemy
        if (updateCount % spawnOnUpdate == 0) {
            enemies.add(enemyFactory.randomPositionEnemy());
        }

        // Update player
        player.update();
        if (leftJoystick.getPressed()) {
            player.move(leftJoystick.getActuatorX(), leftJoystick.getActuatorY());
        } else {
            player.resetVelocity();
        }

        // Shoot
        if (shootButton.getPressed() && player.shoot()) {
            projectiles.add(
                    new Bullet(
                            getContext(),
                            player.getBasePositionX(),
                            player.getBasePositionY(),
                            player.getDirection(),
                            animationFactory.getBulletAnimation()
                    )
            );
        }

        // Remove dead projectiles
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile.shouldStop()) {
                projectileIterator.remove();
            } else {
                projectile.update();
            }
        }

        // Update enemies
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.update(player.getBasePositionX(), player.getBasePositionY());

            // Check if hit by projectile
            projectileIterator = projectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                if (enemy.collides(projectile)) {
                    enemyIterator.remove();
                    projectileIterator.remove();
                }
            }
        }

        // Update joystick
        leftJoystick.updateInnerPosition();

        // Update displayWindow
        displayWindow.update(player);

        updateCount++;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // First draw tileMap
        tileMap.draw(canvas, displayWindow);

        // Offset canvas so that player is in center.
        canvas.save();
        canvas.translate(
                (float) ((displayMetrics.widthPixels / 2) - player.getBasePositionX()),
                (float) ((displayMetrics.heightPixels / 2) - player.getBasePositionY())
        );

        // Draw relative objects.
        player.draw(canvas);

        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(canvas);
        }

        // Reset canvas position to default.
        canvas.restore();

        // Finally draw fixed position objects.
        leftJoystick.draw(canvas);
        shootButton.draw(canvas);
        drawInfo(canvas);
    }

    /**
     * Draws info for debugging.
     * @param canvas to draw
     */
    private void drawInfo(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);

        String[] strings = {
                "UPS: " + gameThread.getAverageUPS(),
                "FPS: " + gameThread.getAverageFPS(),
                "PlayerX: " + player.getBasePositionX(),
                "PlayerY: " + player.getBasePositionY(),
                "DisplayX: " + displayWindow.getRect().left,
                "DisplayY: " + displayWindow.getRect().top,
        };

        int yPos = 100;
        for (String str : strings) {
            canvas.drawText(str, 100, yPos, paint);
            yPos += 100;
        }

    }

}
