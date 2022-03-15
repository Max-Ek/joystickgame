package se.umu.c17mea.joystickgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import se.umu.c17mea.joystickgame.game.GameActivity;

/**
 *
 */
public class MainFragment extends Fragment {


    private ActivityResultLauncher<Intent> gameLauncher;
    private int highScore;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        loadHighScore();

        // Look for activity results.
        gameLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes.
                        Intent data = result.getData();
                        if (data != null) {
                            int ghostsKilled = data.getIntExtra("ghosts", -1);
                            int coins = data.getIntExtra("coins", -1);

                            if (ghostsKilled != -1) {
                                if (ghostsKilled > highScore) {
                                    SharedPreferences sharedPref =
                                            getActivity().getSharedPreferences(
                                                    getString(R.string.preference_file_key),
                                                    Context.MODE_PRIVATE
                                            );
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt(
                                            getString(R.string.preference_file_key_ghosts),
                                            ghostsKilled
                                    );
                                    editor.apply();
                                }
                            }
                        }
                    }
                }
        );

    }

    public void loadHighScore() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int highScoreLoaded = sharedPref.getInt(getString(R.string.preference_file_key_ghosts), 0);
        highScore = highScoreLoaded;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Add start game listener
        getView().findViewById(R.id.start_button).setOnClickListener(buttonView -> {
            openGameActivityForResult();
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHighScore();
        // Display high score
        TextView textView = getView().findViewById(R.id.high_score_text_view);
        String str = getString(R.string.high_score, highScore);
        textView.setText(str);
    }

    /**
     * Launches the game activity.
     */
    public void openGameActivityForResult() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Boolean turbo = prefs.getBoolean(getString(R.string.turbo_key), false);

        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(getString(R.string.turbo_key), turbo);

        gameLauncher.launch(intent);
    }

}