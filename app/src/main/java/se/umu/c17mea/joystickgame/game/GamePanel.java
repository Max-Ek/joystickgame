package se.umu.c17mea.joystickgame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.objects.Enemy;
import se.umu.c17mea.joystickgame.game.objects.EnemyFactory;
import se.umu.c17mea.joystickgame.game.objects.Joystick;
import se.umu.c17mea.joystickgame.game.objects.Player;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;

    private final Player player;
    private final Joystick leftJoystick;
    private final EnemyFactory enemyFactory;
    private final ArrayList<Enemy> enemies;

    private int spawnOnUpdate = 300;
    private int updateCount;

    public GamePanel(Context context) {
        super(context);

        // Add callback to surface holder.
        getHolder().addCallback(this);

        gameThread = new GameThread(this, getHolder());
        player = new Player(getContext(), 2*500,500);
        leftJoystick = new Joystick(getContext(),200, 800, 150);
        enemyFactory = new EnemyFactory(getContext(), new Rect(0, 0, 2000, 1000));
        enemies = new ArrayList<>();
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
        Log.d("MotionEvent:", "onTouchEvent: " + event);
        switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if (leftJoystick.isPressed(event.getX(), event.getY())) {
                        leftJoystick.setActuatorPosition(event.getX(), event.getY());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    leftJoystick.resetActuatorPosition();
                    break;
                default:
                    Log.d("MotionEvent:", "onTouchEvent: " + event);
        }
        return true;
    }

    public void updateGame() {

        /* Spawn enemies */
        if (updateCount % spawnOnUpdate == 0) {
            enemies.add(enemyFactory.randomPositionEnemy());
        }

        /* Move player */
        if (leftJoystick.getIsPressed()) {
            player.move(leftJoystick.getActuatorX(), leftJoystick.getActuatorY());
        } else {
            player.resetVelocity();
        }
        leftJoystick.updateInnerPosition();

        /* Move enemies */
        for (Enemy enemy : enemies) {
            enemy.update(player.getBasePositionX(), player.getBasePositionY());
        }

        updateCount++;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        player.draw(canvas);

        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

        leftJoystick.draw(canvas);
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
                "ActuatorX: " + leftJoystick.getActuatorX(),
                "ActuatorY: " + leftJoystick.getActuatorY(),
                "Velocity: " + player.getVelocity(),
                "Enemies: " + enemies.size()
        };

        int yPos = 100;
        for (String str : strings) {
            canvas.drawText(str, 100, yPos, paint);
            yPos += 100;
        }

    };

}
