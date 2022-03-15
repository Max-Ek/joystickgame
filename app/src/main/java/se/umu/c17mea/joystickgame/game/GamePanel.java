package se.umu.c17mea.joystickgame.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.graphics.AnimationFactory;
import se.umu.c17mea.joystickgame.game.graphics.DisplayWindow;
import se.umu.c17mea.joystickgame.game.graphics.SpriteSheet;
import se.umu.c17mea.joystickgame.game.map.TileMap;
import se.umu.c17mea.joystickgame.game.objects.creatures.Bullet;
import se.umu.c17mea.joystickgame.game.objects.creatures.Enemy;
import se.umu.c17mea.joystickgame.game.objects.creatures.EnemyFactory;
import se.umu.c17mea.joystickgame.game.objects.controls.Joystick;
import se.umu.c17mea.joystickgame.game.objects.controls.Button;
import se.umu.c17mea.joystickgame.game.objects.creatures.Player;
import se.umu.c17mea.joystickgame.game.objects.creatures.PlayerState;
import se.umu.c17mea.joystickgame.game.objects.creatures.Projectile;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final double START_X = 320;
    private static final double START_Y = 320;

    /** Game thread */
    private GameThread gameThread;

    /** Scores */
    private int deadGhosts;
    private int coins;

    /** Game Over */
    private boolean gameOver;
    private int gameOverTime = 60*4;
    private int gameOverCounter = 0;

    /** Display Window */
    private DisplayMetrics displayMetrics;
    private DisplayWindow displayWindow;

    /** TileMap */
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
    private int updateCount = 0;

    /** Sprites & Animations */
    private final SpriteSheet spriteSheet;
    private final AnimationFactory animationFactory;

    /** Drawing text */
    private final Paint textPaint;

    public GamePanel(Context context) {
        super(context);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(100);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

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
        enemyFactory = new EnemyFactory(getContext(), tileMap.getSize(), animationFactory);
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

        // Update player
        if (!gameOver) {
            updatePlayer();
        } else {
            gameOverCounter++;
            if (gameOverCounter >= gameOverTime) {
                exitActivity();
                return;
            }
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

            // Check collision with player
            if (!gameOver && enemy.collides(player)) {
                gameOver();
                return;
            }

            // Check if hit by projectile
            projectileIterator = projectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile projectile = projectileIterator.next();
                if (enemy.collides(projectile)) {
                    enemyIterator.remove();
                    deadGhosts++;
                    projectileIterator.remove();
                }
            }
        }

        // Spawn new enemies
        if (updateCount >= spawnOnUpdate) {
            enemies.add(enemyFactory.randomPositionEnemy());
            updateCount = 0;
        }

        // Update joystick
        leftJoystick.updateInnerPosition();

        // Update displayWindow
        displayWindow.update(player);

        updateCount++;
    }

    private void updatePlayer() {
        if (!tileMap.insideMap( // if outside map kill
                (int) player.getBasePositionX(),
                (int) player.getBasePositionY())) {
            gameOver();
            return;
        }
        player.update();
        if (tileMap.onIce((int) player.getBasePositionX(), (int) player.getBasePositionY())) {
            player.slide();
        } else if (leftJoystick.getPressed()) {
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
    }

    public void setTurbo(boolean turbo) {
        player.setTurbo(turbo);
    }

    /**
     * Draws a game over screen for a few seconds and then exists the activity.
     */
    private void gameOver() {
        gameOver = true;
        player.setState(PlayerState.DEAD);
    }

    private void exitActivity() {
        Activity gameActivity = ((Activity) getContext());
        Intent data = new Intent();
        data.putExtra("ghosts", deadGhosts);
        data.putExtra("coins", coins);
        gameActivity.setResult(Activity.RESULT_OK, data);
        gameActivity.finish();
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

        if (gameOver) {
            drawGameOverInfo(canvas);
        }
        //drawDebugInfo(canvas);
    }

    private void drawGameOverInfo(Canvas canvas) {

        float x = displayMetrics.widthPixels / 2 - 50;
        float y = displayMetrics.heightPixels / 2 - 50;
        canvas.drawText("GAME OVER", x, y, textPaint);
        canvas.drawText("Ghosts killed: " + deadGhosts, x, y+100, textPaint);
        //canvas.drawText("Coins collected: " + coins, x, y + 200, paint);
    }

    /**
     * Draws info for debugging.
     * @param canvas to draw
     */
    private void drawDebugInfo(Canvas canvas) {
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
            canvas.drawText(str, 100, yPos, textPaint);
            yPos += 100;
        }
    }
}
