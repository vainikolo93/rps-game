/**
 * 
 */
package com.kahweh.rps.remote.bt;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.kahweh.rps.GameActivity;
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
	public static final int SERVER_MODE = 0;
	public static final int CLIENT_MODE = 1;
	
	private int mConnectMode;

	private BluetoothSocket mSocket;
	private InputStream in;
	private OutputStream out;
	
	private Handler mHandler;
	private Game mGame;

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
	public void start(Handler handler, Game game) {
		mHandler = handler;
		mGame = game;

		mThread = new DaemonThread();
		mThread.start();

		mHandler.sendEmptyMessage(GameActivity.MSG_SHAKEHAND);

		shakeHands();
	}

	/**
	 * All the shake-hands processes goes here..
	 */
	private void shakeHands() {
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

		public DaemonThread() {}

		@Override
		public void run() {
		}

		public void cancel() {

		}
	}
}
