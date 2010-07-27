/**
 * 
 */
package com.kahweh.rps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
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

        //Get application's version code
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.kahweh.rps", 0);
			versionCode = info.versionCode;
			
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Cannot find pacakage of com.kahweh.rps", e);
		}

		//Load the default preferences
        PreferenceManager.setDefaultValues(this, GameSettings.SETTINGS_NAME, 0, R.xml.game_settings, false);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
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
	public int parseVersionCodeFromIdString(String vString) throws IllegalArgumentException {
		Pattern p = Pattern.compile("\\[" + getResources().getText(R.string.app_id_string) + ":r(\\d+)\\]");

		Matcher m = p.matcher(vString);
		
		if (m.find()) {
			String strVer = m.group(1);
			return Integer.parseInt(strVer);
		}
		
		throw new IllegalArgumentException("Cannot parse Identification String: " + vString);
	}
	
	/**
	 * This function is used to check whether a BT device name represent
	 * a RPS game.
	 * 
	 * @param idString
	 * @return
	 */
	public boolean isIdString(String idString) {
		Pattern p = Pattern.compile("\\[" + getResources().getText(R.string.app_id_string) + ":r(\\d+)\\]");
		
		return p.matcher(idString).find();
	}

	/**
	 * @param btActivity
	 * @param service
	 */
	public void startBtGame(Context from, BtCommunicateService service) {
		btService = service;

		Intent intent= new Intent(from, GameActivity.class);
		intent.putExtra(GameActivity.GAME_TYPE, GameActivity.BT_GAME);
		startActivity(intent);
	}
}
