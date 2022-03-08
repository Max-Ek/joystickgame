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
import se.umu.c17mea.joystickgame.game.objects.creatures.Enemy;
import se.umu.c17mea.joystickgame.game.objects.creatures.EnemyFactory;
import se.umu.c17mea.joystickgame.game.objects.controls.Joystick;
import se.umu.c17mea.joystickgame.game.objects.controls.Button;
import se.umu.c17mea.joystickgame.game.objects.creatures.Player;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    /** Game thread */
    private GameThread gameThread;

    /** Controls */
    private final Joystick leftJoystick;
    private final Button shootButton;
    private int leftJoystickID = -1;
    private int shootButtonID = -1;

    /** Creatures */
    private final Player player;
    private final ArrayList<Enemy> enemies;

    /** Spawns */
    private final EnemyFactory enemyFactory;
    private int spawnOnUpdate = 300;
    private int updateCount;

    public GamePanel(Context context) {
        super(context);

        // Add callback to surface holder.
        getHolder().addCallback(this);

        gameThread = new GameThread(this, getHolder());
        player = new Player(getContext(), 2*500,500);
        leftJoystick = new Joystick(getContext(),200, 800, 150);
        shootButton = new Button(getContext(), 1700, 800, 100);
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
        int pointerCount = event.getPointerCount();
        int actionIndex = event.getActionIndex();
        double pressedX = event.getX(actionIndex);
        double pressedY = event.getY(actionIndex);
        int eventPointerID = event.getPointerId(actionIndex);
        Log.d("DATA" , " actionIndex: " + actionIndex + " eventPointerID: " + eventPointerID);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!leftJoystick.getPressed()) { // If not pressed already.
                    if (leftJoystick.isPressed(pressedX, pressedY)) { // Check it.
                        leftJoystick.setPressed(true);
                        leftJoystickID = eventPointerID;
                        Log.d("TOUCH:", "JS pressed ID " + leftJoystickID);
                    }
                }
                if (!shootButton.getPressed()) { // If not pressed already.
                    if (shootButton.isPressed(pressedX, pressedY)) { // Check it.
                        shootButton.setPressed(true);
                        shootButtonID = eventPointerID;
                        Log.d("TOUCH:", "SB pressed ID " + shootButtonID);
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
                    Log.d("TOUCH:", "JS moved ID " + leftJoystickID);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (eventPointerID == leftJoystickID) {
                    Log.d("TOUCH:", "JS released ID " + leftJoystickID);
                    leftJoystick.setPressed(false);
                    leftJoystickID = -1;
                } else if (eventPointerID == shootButtonID) {
                    Log.d("TOUCH:", "SB released ID " + shootButtonID);
                    shootButton.setPressed(false);
                    shootButtonID = -1;
                }
                break;
            default:
                Log.d("MotionEvent:", "onTouchEvent: " + event);
        }
        return true;
    }

    public void updateGame() {

        /* Spawn enemies. */
        if (updateCount % spawnOnUpdate == 0) {
            enemies.add(enemyFactory.randomPositionEnemy());
        }

        /* Update creatures.  */
        if (leftJoystick.getPressed()) {
            player.move(leftJoystick.getActuatorX(), leftJoystick.getActuatorY());
        } else {
            player.resetVelocity();
        }

        for (Enemy enemy : enemies) {
            enemy.update(player.getBasePositionX(), player.getBasePositionY());
        }

        /* Update controls. */
        leftJoystick.updateInnerPosition();

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
