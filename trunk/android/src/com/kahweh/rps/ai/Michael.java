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
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.MoveAction;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

/**
 * @author Michael
 *
 */
class Michael implements IPlayer, IEngine.DecisionMadeCallback{
	private static String TAG = "com.kahweh.rps.ai.Michael";

	private static final int CONCEDE= 1;
	private static final int MOVEDECISION_MADE = 2;

	private Random rand = new Random(System.currentTimeMillis());
	private int color = IPlayer.IDLE;
	private Game game;

	private Handler mHandler= new Handler() {
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
						game.concede(Michael.this);
					} catch (IllegalGameStateException e) {
						Log.e(TAG, "Current game state cannot perform CONCEDE action.", e);
					}
					break;
				default:
					break;
			}
		}
	};

	/**
	 * Constructor. 
	 */
	public Michael() {
		//This handler is used by AI engine to post the AI decision to Main thread
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public long getGameNumbers() {
		return 0;
	}

	@Override
	public Bitmap getIcon() {
		return null;
	}

	@Override
	public long getLoseNumbers() {
		return 0;
	}

	@Override
	public String getName() {
		return "Michael";
	}

	@Override
	public long getWinNumbers() {
		return 0;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void metConflict() {
		ChessPiece p = null;
		if (color == BLACK) {
			p = game.getBlackConfPiece();
			switch (rand.nextInt(3)) {
			case 0:
				p.setType(ChessPiece.BLACK_ROCK);
				break;
			case 1:
				p.setType(ChessPiece.BLACK_PAPER);
				break;
			case 2:
				p.setType(ChessPiece.BLACK_SCISSORS);
				break;
			}
		} else {
			p = game.getRedConfPiece();
			switch (rand.nextInt(3)) {
			case 0:
				p.setType(ChessPiece.RED_ROCK);
				break;
			case 1:
				p.setType(ChessPiece.RED_PAPER);
				break;
			case 2:
				p.setType(ChessPiece.RED_SCISSORS);
				break;
			}
		}

		try {
			game.makeChoice(p.open());
		} catch (IllegalGameStateException e) {
			Log.e("Michael", "Game state error..", e);
		}
	}

	private IEngine engine = null;
	private Runnable thinkThread = new Runnable() {
		@Override
		public void run() {
			if (engine == null) {
				Log.e(TAG, "The AI Engine cannot be NULL..");
				return;
			}
			try {
				engine.getNextMove(getColor(), Michael.this);
			} catch (IllegalGameStateException e) {
				Log.e(TAG, "Met wrong Game state", e);
			}
		}
	};

	@Override
	public void play() {

		if (rand.nextInt(10) == 0) {
			randomMove();
		} else {
			//goto AI..
			randomMove();
		}

		new Thread(thinkThread).start();
	}

	/**
	 * Random move.
	 */
	private void randomMove() {
		IBoard board = game.getBoard(this);
		ChessPiece ori = null, des = null;
		ChessPiece t1 = null, t2 = null;

		for (int i = 0; i < board.getBoardHeight(); i++) {
			for (int j = 0; j < board.getBoardWidth(); j++) {
				t1 = board.getChessPiece(i, j);
				if (getColor() == IPlayer.BLACK && t1.isBlack() && t1.isMovable()) {
					//Black player and get a movable black piece
					for (int k = 0; k < 4; k++) {
						t2 = board.getNeighborChessPiece(t1, k);
						if (t2 != null && (t2.isBlank() || t2.isRed())) {
							if (ori == null || rand.nextInt(2) == 0) {
								ori = t1;
								des = t2;
							}
						}
					}
				} else if (getColor() == IPlayer.RED && t1.isRed() && t1.isMovable()){
					//Red player and get a movable red piece
					for (int k = 0; k < 4; k++) {
						t2 = board.getNeighborChessPiece(t1, k);
						if (t2 != null && (t2.isBlank() || t2.isBlack())) {
							if (ori == null || rand.nextInt(2) == 0) {
								ori = t1;
								des = t2;
							}
						}
					}
				}
			}
		}

		try {
			if (ori == null || des == null) {
				game.concede(this);
			} else {
				game.move(ori, des);
			}
		} catch (IllegalGameStateException e) {
			Log.w("AI Michael", e);
		}
	}
	
	@Override
	public void chooseFlagAndTrap() {
		ChessPiece flag , trap;
		int fc = rand.nextInt(game.getBoard().getBoardWidth());
		int tc = rand.nextInt(game.getBoard().getBoardWidth());
		if (color == BLACK) {
			flag = new ChessPiece(ChessPiece.BLACK_FLAG, 0, fc);
			trap = new ChessPiece(ChessPiece.BLACK_TRAP, 1, tc);
		} else {
			flag = new ChessPiece(ChessPiece.RED_FLAG, game.getBoard().getBoardHeight()-1, fc);
			trap = new ChessPiece(ChessPiece.RED_TRAP, game.getBoard().getBoardHeight()-2, tc);
		}
		try {
			game.placeFlagAndTrap(flag, trap);
		} catch (IllegalGameStateException e) {
			Log.w("Michael", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#notifyQuit()
	 */
	@Override
	public void notifyQuit() {
		this.game = null;
		this.color = IDLE;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	@Override
	public boolean setFlag(ChessPiece p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTrap(ChessPiece p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlack() {
		return color == IPlayer.BLACK;
	}

	@Override
	public boolean isRed() {
		return color == IPlayer.RED;
	}

	@Override
	public void boardUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFinish(IPlayer winner) throws IllegalPlayerStateException {}

	/**
	 * This is callback function for the AI engine. 
	 */
	@Override
	public void engineMove(ChessPiece from, ChessPiece to) {
		// TODO Auto-generated method stub
		
	}
}
