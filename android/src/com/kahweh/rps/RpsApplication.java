/**
 * 
 */
package com.kahweh.rps;

import android.app.Application;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

/**
 * @author michael
 *
 */
public class RpsApplication extends Application {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.game_settings, false);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}
