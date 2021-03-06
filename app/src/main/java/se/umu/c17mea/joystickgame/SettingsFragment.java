package se.umu.c17mea.joystickgame;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Fragment for displaying and editing the settings.
 *
 * @author c17mea
 * @version 1.0
 * @since 2022-03-16
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Preference resetButton = findPreference(getString(R.string.reset_key));
        resetButton.setOnPreferenceClickListener(preference -> {
            getContext().getSharedPreferences(getString(R.string.preference_file_key),0)
                    .edit().clear().commit();
            // create a toast as feedback
            Toast toast = Toast.makeText(getContext(), "Data Cleared", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}