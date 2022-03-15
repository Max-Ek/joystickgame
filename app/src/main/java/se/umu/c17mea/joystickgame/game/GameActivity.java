package se.umu.c17mea.joystickgame.game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;

import se.umu.c17mea.joystickgame.R;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity {

    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSystemBars();

        Intent intent = getIntent();
        boolean turbo = intent.getBooleanExtra(getString(R.string.turbo_key), false);

        // Create the game panel.
        gamePanel = new GamePanel(this);
        gamePanel.setTurbo(turbo);
        setContentView(gamePanel);
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}