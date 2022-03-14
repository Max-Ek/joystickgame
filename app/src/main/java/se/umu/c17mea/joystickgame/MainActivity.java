package se.umu.c17mea.joystickgame;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import se.umu.c17mea.joystickgame.game.GameActivity;

/**
 * The start screen before the game.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> gameLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemBars();

        // Setup listeners.
        setupStartGameListener();

        // Look for activity results.
        gameLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes.
                        Intent data = result.getData();
                        // TODO use the result data.
                    }
                }
        );
    }

    /**
     * Sets up start game button listener.
     */
    private void setupStartGameListener() {
        findViewById(R.id.start_button).setOnClickListener(
                view -> openGameActivityForResult()
        );
    }

    /**
     * Launches the game activity.
     */
    private void openGameActivityForResult() {
        Intent intent = new Intent(this, GameActivity.class);
        gameLauncher.launch(intent);
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

}