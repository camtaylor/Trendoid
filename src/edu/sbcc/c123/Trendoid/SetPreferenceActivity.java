package edu.sbcc.c123.Trendoid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SetPreferenceActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Toast.makeText(this, "Nightmode unimplemented", Toast.LENGTH_LONG).show();
	}
    
    

}