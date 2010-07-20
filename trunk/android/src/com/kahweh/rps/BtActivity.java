/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Michael
 *
 */
public class BtActivity extends Activity {
	private static String TAG = "com.kahweh.rps.BtActivity";

	private static final int REQUEST_ENABLE_BT = 1;

    // Local Bluetooth adapter
    private BluetoothAdapter mBtAdapter = null;
    
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//To show the indeterminate progress icon in the title bar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.bt);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			Toast.makeText(this, R.string.toast_no_bt_device, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		//Initialize the Arrary Adapter, one for the pired devices, one for the new devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_devicename);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.bt_devicename);

		//Get the instances of ListView
		ListView pairedDevices = (ListView)findViewById(R.id.bt_paired_device_listview);
		ListView newDevices = (ListView)findViewById(R.id.bt_new_device_listview);

		//Set Array Adapter for the ListViews
		pairedDevices.setAdapter(mPairedDevicesArrayAdapter);
		newDevices.setAdapter(mNewDevicesArrayAdapter);

		//Set onItemClickListener for the ListViews
		pairedDevices.setOnItemClickListener(mDeviceClickListener);
		newDevices.setOnItemClickListener(mDeviceClickListener);

		//Button that used to start the remote divice discovery
		Button scanBtn = (Button)findViewById(R.id.bt_scandevice_btn);
		scanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (Config.DEBUG) {
			Log.d(TAG, "onStart()..");
		}

		if (mBtAdapter == null) {
			Log.e(TAG, "BluetoothAdapter is NULL...");
			finish();
			return;
		}

		if (!mBtAdapter.isEnabled()) {
			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(i, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Cancel the discovery action
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		//Unregister broaddcast receiver
		unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (Config.DEBUG) {
			Log.d(TAG, "onActivityResult: " + resultCode);
		}

		switch (requestCode) {
			case REQUEST_ENABLE_BT:
				if (resultCode == Activity.RESULT_OK) {
					//User enabled BT
					//TODO
				} else {
					//User did not enable BT
					if (Config.DEBUG) {
						Log.d(TAG, "Bluetooth device is not enabled..");
					}
					Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			default:
				break;
		}
	}

	//Local private Broadcast Receiver
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				//TODO
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.str_bt_label);
			} else {
			}
		}
	};

	private final OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
		}
	};

	private void doDiscovery() {
		if (Config.DEBUG) {
			Log.d(TAG, "Starting BT devices discovery...");
		}

		//Make local BT device discoverable
		if (mBtAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(intent);
		}

		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.bt_scanning_title);
		
		//Start devices discovery with a async way
		mBtAdapter.startDiscovery();
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