/**
 * 
 */
package com.kahweh.rps.ai;

import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
class Michael implements IPlayer {
	private Random rand = new Random(System.currentTimeMillis());
	private int color = IPlayer.IDLE;
	private Game game;

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
			game.makeChoice(p);
		} catch (IllegalGameStateException e) {
			Log.w("Michael", e);
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
	}

	@Override
	public void chooseFlagAndTrap() {
		ChessPiece flag , trap;
		int fc = rand.nextInt(7);
		int tc = rand.nextInt(7);
		if (color == BLACK) {
			flag = new ChessPiece(ChessPiece.BLACK_FLAG, 0, fc);
			trap = new ChessPiece(ChessPiece.BLACK_TRAP, 1, tc);
		} else {
			flag = new ChessPiece(ChessPiece.RED_FLAG, 5, fc);
			trap = new ChessPiece(ChessPiece.RED_TRAP, 4, tc);
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

}
