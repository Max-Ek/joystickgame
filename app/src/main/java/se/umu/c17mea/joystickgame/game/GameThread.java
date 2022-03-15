package se.umu.c17mea.joystickgame.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    public static final float MAX_UPS = 60;
    public static final float UPS_PERIOD = 1000/MAX_UPS;

    private final GamePanel gamePanel;
    private final SurfaceHolder surfaceHolder;

    private volatile boolean running = false;

    private double averageFPS;
    private double averageUPS;

    /**
     * Constructor.
     * @param gamePanel to draw and update
     * @param surfaceHolder to get and lock canvas
     */
    public GameThread(GamePanel gamePanel, SurfaceHolder surfaceHolder) {
        this.gamePanel = gamePanel;
        this.surfaceHolder = surfaceHolder;
    }

    /**
     * The game loop with frame pacing.
     */
    @Override
    public void run() {
        running = true;

        int updateCount = 0;
        int frameCount = 0;

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long sleepTime;

        while (running) {
            Canvas canvas = null;

            /* Update the game */
            gamePanel.updateGame();
            updateCount++;

            if (!running) {
                break;
            }

            // Try to draw
            try {
                canvas = surfaceHolder.lockCanvas(); // Lock the canvas from other threads.
                synchronized (surfaceHolder) {
                    gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas); // Don't forget to unlock it.
                    frameCount++;
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);

            /* Sleep if there's spare time in the frame. */
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /* If behind on time, catch up without rendering frames. */
            while (sleepTime < 0 && updateCount < MAX_UPS && running) {
                gamePanel.updateGame();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            /* After each second, calculate UPS/FPS & reset counters/timers */
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (elapsedTime / 1000.0);
                averageFPS = frameCount / (elapsedTime / 1000.0);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }

    /**
     * Sets the running instance variable.
     * @param running boolean
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Get's the average FPS.
     * @return fps
     */
    public double getAverageFPS() {
        return averageFPS;
    }

    /**
     * Gets the average UPS.
     * @return ups
     */
    public double getAverageUPS() {
        return averageUPS;
    }
}
