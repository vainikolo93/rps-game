/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;

/**
 * @author Michael
 *
 */
public class GameSettings extends PreferenceActivity {

	/**
	 * Display and edit game settings.
	 * @param from
	 * @param accountId
	 */
	public static void actionSetting(Activity from) {
        Intent i = new Intent(from, GameSettings.class);
        from.startActivity(i);
	}
}
