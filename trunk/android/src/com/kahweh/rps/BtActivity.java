/**
 * 
 */
package com.kahweh.rps;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import com.kahweh.rps.remote.bt.BtCommunicateService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

	//Define the BT relative intent Action ID
	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_ENABLE_BT_DISCOVERABLE = 2;

	//Define the local handler Message ID
	public static final int CONNECTING_REQUEST_RECEIVED = 0;
	public static final int SHOW_PROGRESS_DIALOG = 1;
	public static final int DISMISS_PROGRESS_DIALOG =2;

	//Define the local Dialog Box ID
	public static final int DLG_CONFIRM_REMOTE_CONNECTION = 0;
	public static final int DLG_CONNECTING_PROGRESS = 1;
	
    //Local Bluetooth adapter
    private BluetoothAdapter mBtAdapter;

    //Array adapter of paired and new devices
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    private SharedPreferences mSharedPreferences;
    
    private String mBtOldName;

    private RpsApplication rpsApp;

    //Connecting request handler thread
    private AcceptThread mAcceptThread;
    //Connecting thread
    private ConnectThread mConnectThread;
    
    private Handler mHandler;

    private UUID uuid;

    private boolean scanning = false;
    private boolean listening = false;
    private boolean connecting = false;
    private boolean connected = false;

    private BluetoothSocket mSocket;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Get the RpsApplication object
		rpsApp = (RpsApplication)getApplication();

		//To show the indeterminate progress icon in the title bar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		//Inflate the view layout
		setContentView(R.layout.bt);

        //get game preferences
		mSharedPreferences = getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
		
		//Get the Bluetooth Adapter
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
		ListView pairedDevicesListView = (ListView)findViewById(R.id.bt_paired_device_listview);
		ListView newDevicesListView = (ListView)findViewById(R.id.bt_new_device_listview);

		//Set Array Adapter for the ListViews
		pairedDevicesListView.setAdapter(mPairedDevicesArrayAdapter);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);

		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		} else {
			mPairedDevicesArrayAdapter.add(getResources().getText(R.string.bt_none_paired).toString());
		}

		//Set onItemClickListener for the ListViews
		pairedDevicesListView.setOnItemClickListener(mDeviceClickListener);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		//Button that used to start the remote divice discovery
		Button scanBtn = (Button)findViewById(R.id.bt_scandevice_btn);
		scanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		//Register BT devcie found and discovery finish intent		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		//Discovery finish intent
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
		
		filter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		registerReceiver(mReceiver, filter);

		//Define and create the local handler
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case CONNECTING_REQUEST_RECEIVED :
						mSocket = (BluetoothSocket)msg.obj;
						showDialog(CONNECTING_REQUEST_RECEIVED);
						break;
					case SHOW_PROGRESS_DIALOG :
						showDialog(DLG_CONNECTING_PROGRESS);
						break;
					case DISMISS_PROGRESS_DIALOG :
						dismissDialog(DLG_CONNECTING_PROGRESS);
					default:
						break;
				}
			}
		};

		//Load the UUID resource
		uuid = UUID.fromString(getResources().getText(R.string.bt_uuid).toString());
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
		} else {
			setBtAdapterName();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		//Make local BT device discoverable
		if (mBtAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			//Local BT device is not discoverable, ask user to enable it
			requestBtDiscoverable();
		} else if (!connected && !connecting) { 
			//Local BT device is discoverable, start the accept Thread
			kickOffAcceptThread();
		}
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
			//Recover the original BT device name
			if (mBtOldName != null) mBtAdapter.setName(mBtOldName);

			mBtAdapter.cancelDiscovery();
		}

		//Unregister broaddcast receiver
		unregisterReceiver(mReceiver);
	}

    @Override
    protected Dialog onCreateDialog(int id) {
    	switch (id) {
    		case DLG_CONFIRM_REMOTE_CONNECTION :
    			return new AlertDialog.Builder(this)
        		.setIcon(R.drawable.dialog_icon_question)
        		.setPositiveButton(R.string.bt_btn_accept_remote, new DialogInterface.OnClickListener() {
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					startGame(BtCommunicateService.SERVER_MODE);
    				}
    			})
    			.setNegativeButton(R.string.bt_btn_refuse_remote, null)
    			.create();
    		case DLG_CONNECTING_PROGRESS :
    			return ProgressDialog.show(this, "", "");
    	}
    	return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dlg) {
    	switch (id) {
    	case DLG_CONFIRM_REMOTE_CONNECTION :
    		String msg = getResources().getText(R.string.bt_prompt_confirm_remote_connection).toString();
    		if (mSocket != null) {
    			String s = mSocket.getRemoteDevice().getName() + " - " + mSocket.getRemoteDevice().getAddress();
    			msg = msg.replace("###", s);
    		} else {
    			Log.e(TAG, "The mSocket cannot be NULL..");
    		}
    		((AlertDialog)dlg).setMessage(msg);
    		break;
    	case DLG_CONNECTING_PROGRESS :
    		((ProgressDialog)dlg).setMessage("Connecting...");
    		break;
    	}
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (Config.DEBUG) {
			Log.d(TAG, "onActivityResult: " + resultCode);
		}

		switch (requestCode) {
			case REQUEST_ENABLE_BT :
				if (resultCode == Activity.RESULT_OK) {
					//User enabled BT
					setBtAdapterName();
				} else {
					//User did not enable BT
					if (Config.DEBUG) {
						Log.d(TAG, "Bluetooth device is not enabled..");
					}
					Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			case REQUEST_ENABLE_BT_DISCOVERABLE :
				if (resultCode == Activity.RESULT_OK) {
					//User enabled BT discoverable
					kickOffAcceptThread();
				} else {
					//User disabled BT discoverable
					if (Config.DEBUG) {
						Log.d(TAG, "User did not enable the BT discovery..");
					}
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
				//Find new Device
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() != BluetoothDevice.BOND_BONDED 
						&& rpsApp.isIdString(device.getName())) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				//The discovery SCAN action finished
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.str_bt_label);
				scanning = false;
			} else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
				//Check if the discoverable state is timeout
				if (intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1) 
						!= BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
					listening = false;
					requestBtDiscoverable();
				}
			}
		}
	};

	private final OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			mBtAdapter.cancelDiscovery();
			scanning = false;

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            BluetoothDevice device = null;
            if (mBtAdapter.checkBluetoothAddress(address)) {
            	device = mBtAdapter.getRemoteDevice(address);
            } else {
            	Log.w(TAG, "Not a valid MAC address: " + address);
            	Toast.makeText(BtActivity.this, "Wrong BT MAC address : " + address, Toast.LENGTH_SHORT).show();
            }

            mConnectThread = new ConnectThread(device, mHandler);
		}
	};

	/**
	 * This function is used to start the scan action. 
	 */
	private void doDiscovery() {
		if (Config.DEBUG) {
			Log.d(TAG, "Starting BT devices discovery...");
		}

		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.bt_scanning_title);

		//Start devices discovery with a async way
		mBtAdapter.startDiscovery();

		scanning = true;
	}

	/**
	 * This function is used to sent the discoverable request intent.
	 */
	private void requestBtDiscoverable() {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivityForResult(intent, REQUEST_ENABLE_BT_DISCOVERABLE);
	}
	
	/**
	 * This function is used to create and start the Accept Thread.
	 */
	private void kickOffAcceptThread() {
		if (mBtAdapter == null) {
			Log.e(TAG, "BT Adapter is null, cannot start accept listening thread..");
			return;
		}

		if (mBtAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Log.w(TAG, "The Bt device is not discoverable, so cannot start accept listening thread..");
			return;
		}

		if (connecting || connected) {
			Log.w(TAG, "State ERROR, now is STATE_CONNECTED or " +
					"STATE_CONNECTING, cannot kick off Accept Thread..");
			return;
		}
		
		mAcceptThread = new AcceptThread();
		mAcceptThread.start();

		listening = true;
	}

	/**
	 * @param mode
	 */
	private synchronized void startGame(int mode) {
		if (mSocket == null) {
			Log.w(TAG, "mSocket is NULL, cannot start game..");
			Toast.makeText(this, R.string.bt_connection_lost, Toast.LENGTH_SHORT).show();
			return;
		}

		BtCommunicateService service = new BtCommunicateService(mSocket, mode);

		
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

	/**
	 * Set local BT device name.
	 * 
	 * For example: [RPS:r100]Rock Paper Scissors - Michael
	 */
	private void setBtAdapterName() {
		mBtOldName = mBtAdapter.getName();
		String newName = rpsApp.getAppIdentificationString() 
        					+ getResources().getText(R.string.app_name) + " - "
        					+ mSharedPreferences.getString(GameSettings.PLAYER_NAME, "Player1");
		mBtAdapter.setName(newName);
	}

	/**
	 * This thread is used to connect to a remote game as client.
	 * 
	 * @author michael
	 *
	 */
	class ConnectThread extends Thread {

		BluetoothDevice mDevice;
		BluetoothSocket mLocalSocket;
		Handler mHandler;

		public ConnectThread(BluetoothDevice device, Handler handler) {
			this.mDevice = device;
			this.mHandler = handler;
			
			try {
				mLocalSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
			} catch (IOException e) {
				Log.e(TAG, "Cannot create socket to : " + mDevice.getAddress());
				Toast.makeText(BtActivity.this, "Cannot Create connection to : " + mDevice.getAddress(), Toast.LENGTH_SHORT).show();
				mLocalSocket = null;
			}
		}

		@Override
		public void run() {
			mBtAdapter.cancelDiscovery();

			mHandler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
			connecting = true;
			
			try {
				mLocalSocket.connect();
			} catch (IOException e) {
				Log.w(TAG, "Cannot establish connection through socket..");
				Toast.makeText(BtActivity.this, "Cannot establish connection through socket..", Toast.LENGTH_SHORT).show();

				try {
					mLocalSocket.close();
				} catch (IOException e1) {
					Log.e(TAG, "Close socket error..", e1);
				}
			}

			mSocket = mLocalSocket;
			startGame(BtCommunicateService.CLIENT_MODE);

			mHandler.sendEmptyMessage(DISMISS_PROGRESS_DIALOG);
			connecting = false;
		}

		public void cancel() {
			try {
				mLocalSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Close socket error..", e);
			}
		}
	}

	/**
	 * This thread is used to accept the remote connecting request.
	 * 
	 * @author Michael
	 *
	 */
	class AcceptThread extends Thread {

		private BluetoothServerSocket mServerSocket;

		public AcceptThread() {
			super();

			try {
				mServerSocket = mBtAdapter.listenUsingRfcommWithServiceRecord(mBtAdapter.getName(), uuid);
			} catch (IOException e) {
				Log.e(TAG, "Cannot create the server side listening Socket", e);
				Toast.makeText(BtActivity.this, R.string.bt_listener_socket_error, Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void run() {
			if (Config.DEBUG) {
				Log.d(TAG, "Start the server side listening action..");
			}

			setName("Accept Listening Thread");

			listening = true;

			BluetoothSocket socket = null;

			while (!connecting && !connected) {
				try {
					socket = mServerSocket.accept();
				} catch (IOException e) {
					Log.e(TAG, "Accept client socket error..", e);
					break;
				}

				if (socket != null) {
					synchronized (BtActivity.this) {
						if (!connecting && !connected) {
							connecting = true;
							mHandler.obtainMessage(CONNECTING_REQUEST_RECEIVED, socket);
							try {
								mServerSocket.close();
							} catch (IOException e) {
								Log.e(TAG, "Cannot close server socket..", e);
							}
						}
					}
				}
			}

			listening = false;

			if (Config.DEBUG) {
				Log.d(TAG, "End the server side listening action..");
			}
		}

		public void cancel() {
			if (mServerSocket != null) {
				try {
					mServerSocket.close();
				} catch (IOException e) {
					Log.e(TAG, "Cannot close the server side listening Socket", e);
				}
			}
		}
	}
}
