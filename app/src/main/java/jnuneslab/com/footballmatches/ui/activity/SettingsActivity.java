package jnuneslab.com.footballmatches.ui.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import java.util.Map;

import jnuneslab.com.footballmatches.R;
import jnuneslab.com.footballmatches.util.Util;

/**
 * SettingsActivity class responsible for handle which leagues should be shown in Main Activity
 */
public class SettingsActivity  extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.pref_general);

        // Get all leagues
        Map<Integer,String> mapLeagues = Util.getLeagues(getApplicationContext());

        // Create all checkbox preference in the screen, one for each league
        for (Map.Entry<Integer, String> entry : mapLeagues.entrySet()) {
            CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
            checkBoxPreference.setKey(entry.getKey().toString());
            checkBoxPreference.setTitle(entry.getValue());
            checkBoxPreference.setChecked(true);
            getPreferenceScreen().addPreference(checkBoxPreference);
        }
    }
}