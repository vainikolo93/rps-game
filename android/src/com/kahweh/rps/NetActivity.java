/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author michael
 *
 */
public class NetActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Show Net Activity.
	 * 
	 * @param from
	 */
	public static void actionGame(HomeActivity from) {
		Intent i = new Intent(from, NetActivity.class);
		from.startActivity(i);
	}
}