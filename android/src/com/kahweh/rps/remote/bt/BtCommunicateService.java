/**
 * 
 */
package com.kahweh.rps.remote.bt;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;

/**
 * @author Michael
 *
 */
public class BtCommunicateService extends Thread {
	public static final int SERVER_MODE = 0;
	public static final int CLIENT_MODE = 1;
	
	private int mConnectMode;

	private BluetoothSocket mSocket;
	private InputStream in;
	private OutputStream out;

	/**
	 * @param socket
	 * @param connectType
	 */
	public BtCommunicateService(BluetoothSocket socket, int connectMode) {
		this.mSocket = socket;
		this.mConnectMode = connectMode;
	}

	@Override
	public void run() {
		//TODO
	}

	public int getConnectMode() {
		return mConnectMode;
	}
}
