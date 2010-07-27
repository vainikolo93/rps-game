/**
 * 
 */
package com.kahweh.rps.remote.bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

/**
 * @author Michael
 *
 */
public class BtCommunicateService {
	public static final int SERVER_MODE = 0;
	public static final int CLIENT_MODE = 1;
	
	private int mConnectMode;

	private BluetoothSocket mSocket;
	private InputStream in;
	private OutputStream out;
	
	private Handler mHandler;
	
	private DaemonThread mThread;

	/**
	 * @param socket
	 * @param connectType
	 */
	public BtCommunicateService(BluetoothSocket socket, int connectMode) {
		this.mSocket = socket;
		this.mConnectMode = connectMode;
	}

	/**
	 * Used to start the service loop..
	 */
	public void start(Handler handler) {
		mHandler = handler;
		mThread = new DaemonThread();
		mThread.start();
	}

	public int getConnectMode() {
		return mConnectMode;
	}

	/**
	 * This thread used to communicate with the peer device.
	 */
	class DaemonThread extends Thread {
		
		public DaemonThread() {}

		@Override
		public void run() {
		}
		
		public void cancel() {
			
		}
	}
}
