package se.umu.c17mea.joystickgame.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Game thread responsible for calling game update and draw.
 * Uses frame pacing to avoid lag spikes.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class GameThread extends Thread {

    /** Tries to avoid exceeding this value of UPS. */
    public static final float MAX_UPS = 60;
    public static final float UPS_PERIOD = 1000/MAX_UPS;

    /** Game to update. */
    private final Game game;

    /** SurfaceHolder to get canvas from. */
    private final SurfaceHolder surfaceHolder;

    /** Thread state. */
    private volatile boolean running = false;

    /** Average UPS/FPS values. */
    private double averageUPS;
    private double averageFPS;

    /**
     * Constructor.
     * @param game to draw and update
     * @param surfaceHolder to get and lock canvas
     */
    public GameThread(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    /**
     * Starts the game loop with frame pacing.
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
            game.updateGame();
            updateCount++;

            if (!running) {
                break;
            }

            // Try to draw
            try {
                canvas = surfaceHolder.lockCanvas(); // Lock the canvas from other threads.
                synchronized (surfaceHolder) {
                    game.draw(canvas);
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
                game.updateGame();
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
