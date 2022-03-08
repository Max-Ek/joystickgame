package se.umu.c17mea.joystickgame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import se.umu.c17mea.joystickgame.R;
import se.umu.c17mea.joystickgame.game.objects.Joystick;
import se.umu.c17mea.joystickgame.game.objects.Player;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Player player;
    private Joystick leftJoystick;

    public GamePanel(Context context) {
        super(context);

        // Add callback to surface holder.
        getHolder().addCallback(this);

        gameThread = new GameThread(this, getHolder());
        player = new Player(getContext(), 2*500,500);
        leftJoystick = new Joystick(getContext(),200, 800, 150);
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
        if (leftJoystick.getIsPressed()) {
            player.move(leftJoystick.getActuatorX(), leftJoystick.getActuatorY());
        } else {
            player.resetVelocity();
        }
        leftJoystick.updateInnerPosition();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
        drawUPS(canvas);
        player.draw(canvas);
        leftJoystick.draw(canvas);
        drawActuatorX(canvas);
        drawActuatorY(canvas);
        drawVelocity(canvas);
    }

    private void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameThread.getAverageUPS());
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 200, paint);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameThread.getAverageFPS());
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 300, paint);
    }

    private void drawActuatorX(Canvas canvas) {
        String averageFPS = Double.toString(leftJoystick.getActuatorX());
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);
        canvas.drawText("ActuatorX: " + averageFPS, 100, 400, paint);
    }

    private void drawActuatorY(Canvas canvas) {
        String averageFPS = Double.toString(leftJoystick.getActuatorY());
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);
        canvas.drawText("ActuatorY: " + averageFPS, 100, 500, paint);
    }

    private void drawVelocity(Canvas canvas) {
        String averageFPS = Double.toString(player.getVelocity());
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.teal_700));
        paint.setTextSize(50);
        canvas.drawText("Velocity: " + averageFPS, 100, 600, paint);
    }

}
