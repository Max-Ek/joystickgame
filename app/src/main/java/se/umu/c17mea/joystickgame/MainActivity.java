package se.umu.c17mea.joystickgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The start screen before the game.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        NavigationUI.setupWithNavController(
                toolbar,
                navController,
                appBarConfiguration
        );


        hideSystemBars();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_button) {
            openSettingsFragment();
        } else if (item.getItemId() == R.id.help_button) {
            openHelpFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    /**
     * Opens the settings fragment.
     */
    public void openSettingsFragment() {
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.action_mainFragment_to_settingsFragment4);
    }

    /**
     * Opens the help fragment.
     */
    public void openHelpFragment() {
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.action_mainFragment_to_helpFragment);
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