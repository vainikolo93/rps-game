/**
 * 
 */
package com.kahweh.rps.ai;

import java.util.Random;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.MoveAction;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

/**
 * @author Michael
 *
 */
public class AbstractAiPlayer implements IPlayer {
	private static String TAG = "com.kahweh.rps.ai.AbstractAiPlayer";

	protected static final int CONCEDE= 1;
	protected static final int MOVEDECISION_MADE = 2;

	protected Random rand = new Random(System.currentTimeMillis());
	protected int color = IPlayer.IDLE;
	protected Game game;

	//This handler is used by AI engine to post the AI decision to Main thread
	protected Handler mHandler;

	protected AbstractAiPlayer() {
		mHandler= new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case MOVEDECISION_MADE:
						try {
							game.move((MoveAction)msg.obj);
						} catch (IllegalGameStateException e) {
							Log.e(TAG, "Current game state cannot perform MOVE action.", e);
						}
						break;
					case CONCEDE:
						try {
							game.concede(AbstractAiPlayer.this);
						} catch (IllegalGameStateException e) {
							Log.e(TAG, "Current game state cannot perform CONCEDE action.", e);
						}
						break;
					default:
						break;
				}
			}
		};
	}

	protected ThinkThread thinkThread;

	protected class ThinkThread extends Thread {
		private IEngine engine;

		@Override
		public void run() {
			if (engine == null) {
				Log.e(TAG, "The AI Engine cannot be NULL..");
				return;
			}
			try {
				MoveAction mv = engine.getNextMove(getColor());
				if (mv.isValid()) {
					mHandler.sendMessage(mHandler.obtainMessage(AbstractAiPlayer.MOVEDECISION_MADE, mv));
				} else {
					mHandler.sendMessage(mHandler.obtainMessage(AbstractAiPlayer.CONCEDE));
				}
			} catch (IllegalGameStateException e) {
				Log.e(TAG, "Met wrong Game state", e);
			}
		}

		public void setEngine(IEngine engine) {
			this.engine = engine;
		}

		public IEngine getEngine() {
			return engine;
		}
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#boardInitialized()
	 */
	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#boardUpdated()
	 */
	@Override
	public void boardUpdated() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#chooseFlagAndTrap()
	 */
	@Override
	public void chooseFlagAndTrap() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getColor()
	 */
	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getGame()
	 */
	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getGameNumbers()
	 */
	@Override
	public long getGameNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getIcon()
	 */
	@Override
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getLoseNumbers()
	 */
	@Override
	public long getLoseNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#getWinNumbers()
	 */
	@Override
	public long getWinNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#isBlack()
	 */
	@Override
	public boolean isBlack() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#isRed()
	 */
	@Override
	public boolean isRed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#metConflict()
	 */
	@Override
	public void metConflict() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#notifyFinish(com.kahweh.rps.game.player.IPlayer)
	 */
	@Override
	public void notifyFinish(IPlayer winner) throws IllegalPlayerStateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#notifyQuit()
	 */
	@Override
	public void notifyQuit() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#play()
	 */
	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#setColor(int)
	 */
	@Override
	public void setColor(int color) throws IllegalPlayerStateException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#setFlag(com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public boolean setFlag(ChessPiece p) throws IllegalPlayerStateException,
			IllegalGameStateException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#setGame(com.kahweh.rps.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.player.IPlayer#setTrap(com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public boolean setTrap(ChessPiece p) throws IllegalPlayerStateException,
			IllegalGameStateException {
		// TODO Auto-generated method stub
		return false;
	}

}
