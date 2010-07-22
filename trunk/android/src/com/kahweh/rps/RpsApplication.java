/**
 * 
 */
package com.kahweh.rps;

import java.util.regex.Pattern;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import com.kahweh.rps.remote.bt.BtCommunicateService;

/**
 * @author michael
 *
 */
public class RpsApplication extends Application {
	private static final String TAG = "com.kahweh.rps.RpsApplication";
	
	//Bt Communication service
	private BtCommunicateService btService;
	private int versionCode = 0;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		//Load the default preferences
        PreferenceManager.setDefaultValues(this, R.xml.game_settings, false);

        //Get application's version code
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.kahweh.rps", 0);
			versionCode = info.versionCode;
			
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Cannot find pacakage of com.kahweh.rps", e);
		}

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public void setBtService(BtCommunicateService service) {
		btService = service;
	}
	public BtCommunicateService getBtService() {
		return btService;
	}
	
	/**
	 * This function is used to get the Identification String of game.
	 * 
	 * For example: [RPS:r100]
	 * 
	 * @return
	 */
	public String getAppIdentificationString() {
		return "[" + getResources().getText(R.string.app_id_string) + ":r" + versionCode + "]";
	}
	
	/**
	 * This function is used to parse the version code from a indentification String.
	 * 
	 * @param vString
	 * @return
	 */
	public int parseVersionCodeFromIdString(String vString) {
		//TODO
		return 0;
	}
	
	public boolean checkIdString(String idString) {
		Pattern p = Pattern.compile();
		return true;
	}
}
