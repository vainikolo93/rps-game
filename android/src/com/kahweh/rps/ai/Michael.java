/**
 * 
 */
package com.kahweh.rps.ai;

import android.graphics.Bitmap;
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
class Michael extends AbstractAiPlayer {
	private static String TAG = "com.kahweh.rps.ai.Michael";

	/**
	 * Constructor. 
	 */
	public Michael() {
		super();
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

	@Override
	public void play() {
		thinkThread = new ThinkThread();
		thinkThread.setEngine(EngineFactory.getRandomEngine(game.getBoard(), 0));
		thinkThread.start();
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

}
