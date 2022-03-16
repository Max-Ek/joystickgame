package se.umu.c17mea.joystickgame.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;

import se.umu.c17mea.joystickgame.R;

/**
 * The game activity.
 *
 * Starts up the game.
 * Hides the system bars.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class GameActivity extends AppCompatActivity {

    /** THE GAME. */
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSystemBars();

        Intent intent = getIntent();
        boolean turbo = intent.getBooleanExtra(getString(R.string.turbo_key), false);

        // Create the game panel.
        game = new Game(this);
        game.setTurbo(turbo);
        setContentView(game);
    }

    /**
     * Hides the system bars (full screen).
     */
    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}