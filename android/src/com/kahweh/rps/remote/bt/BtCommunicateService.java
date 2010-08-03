/**
 * 
 */
package com.kahweh.rps.remote.bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.kahweh.rps.RpsApplication;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.remote.AbstractCommunicateService;

/**
 * This class is used to setup and maintain the communication between
 * two remote players.
 * 
 * @author Michael
 *
 */
public class BtCommunicateService extends AbstractCommunicateService {
	private static final String TAG = "com.kahweh.rps.remote.bt.BtCommunicateService";

	public static final int SERVER_MODE = 0;
	public static final int CLIENT_MODE = 1;

	private int mConnectMode;

	private BluetoothSocket mSocket;
	private Handler mHandler;
	private Game mGame;

	/**
	 * Constructor.
	 * 
	 * @param socket
	 * @param connectType
	 */
	public BtCommunicateService(BluetoothSocket socket, int connectMode, Handler handler) {
		this.mSocket = socket;
		this.mConnectMode = connectMode;
		this.mHandler = handler;

		mThread = new DaemonThread(socket);
		mThread.start();
	}

	public int getmConnectMode() {
		return mConnectMode;
	}

	public void setmConnectMode(int mConnectMode) {
		this.mConnectMode = mConnectMode;
	}

	public Handler getmHandler() {
		return mHandler;
	}

	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public Game getmGame() {
		return mGame;
	}

	public void setmGame(Game mGame) {
		this.mGame = mGame;
	}

	private DaemonThread mThread;

	/**
	 * Used to start the service loop..
	 */
	public void start(Handler handler) {
	}

	/**
	 * All the shake-hands processes goes here..
	 */
	public void shakeHands() {
		mHandler.sendEmptyMessage(RpsApplication.MSG_SHAKEHANDS);

		if (this.mConnectMode == CLIENT_MODE) {
		} else {
		}
	}

	public int getConnectMode() {
		return mConnectMode;
	}

	/**
	 * This thread used to communicate with the peer device.
	 */
	class DaemonThread extends Thread {
		private static final String TAG = "com.kahweh.rps.remote.bt." +
				"BtCommunicateService#DaemonThread";

		private BluetoothSocket mSocket;
		private InputStream in;
		private OutputStream out;

		public DaemonThread(BluetoothSocket socket) {
			this.mSocket = socket;
			try {
				in = socket.getInputStream();
				out = socket.getOutputStream();
			} catch (IOException e) {
				Log.e(TAG, "Cannot get InputStream or OutputStream..", e);
			}
		}

		@Override
		public void run() {
			
		}

		public void cancel() {
			try {
				mSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "Close mSocket error..", e);
			}
		}
	}
}
