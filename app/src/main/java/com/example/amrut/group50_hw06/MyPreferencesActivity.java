package com.example.amrut.group50_hw06;

import android.content.Intent;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MyPreferencesActivity extends PreferenceActivity {
static ListPreference listPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            listPreference= (ListPreference) findPreference("tempPref");

            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Toast.makeText(preference.getContext(), "Temperature unit has been changed to "+newValue, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(preference.getContext(), MainActivity.class);
                    startActivity(intent);

                    return true;
                }
            });
        }
    }
}

