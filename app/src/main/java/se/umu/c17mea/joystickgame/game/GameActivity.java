package se.umu.c17mea.joystickgame.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity {

    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set full screen.
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Hide the app bar.
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Create the game panel.
        gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }

}