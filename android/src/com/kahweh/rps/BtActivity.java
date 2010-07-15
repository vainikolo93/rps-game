/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

/**
 * @author michael
 *
 */
public class BtActivity extends Activity {

    // Local Bluetooth adapter
    private BluetoothAdapter mBtAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.bt);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			Toast.makeText(this, R.string.toast_no_bt_device, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
	}

	/**
	 * Show BlueTooth Activity.
	 * 
	 * @param from
	 */
	public static void actionGame(HomeActivity from) {
		Intent i = new Intent(from, BtActivity.class);
		from.startActivity(i);
	}
}
