/**
 * 
 */
package com.kahweh.rps.ai;

import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

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
			game.makeChoice(p);
		} catch (IllegalGameStateException e) {
			Log.e("Michael", "Game state error..", e);
		}
	}

	@Override
	public void play() {
		Board board = game.getBoard(this);
		// TODO Auto-generated method stub
		ChessPiece p = null, n = null;

		for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
			for (int j = 0; j < Board.BOARD_WIDTH; j++) {
				p = board.getChessPiece(i, j);
				if (getColor() == IPlayer.BLACK && p.isBlack() && p.isMovable()) {
					//TODO
					for (int k = 0; k < 4; k++) {
						n = board.getNeighborChessPiece(p, k);
						if (n != null && (n.isBlank() || n.isRed())) {
							j = Board.BOARD_WIDTH;
							i = Board.BOARD_HEIGHT;
							break;
						}
					}
				} else if (getColor() == IPlayer.RED && p.isRed() && p.isMovable()){
					//Red player
					//TODO
					for (int k = 0; k < 4; k++) {
						n = board.getNeighborChessPiece(p, k);
						if (n != null && (n.isBlank() || n.isBlack())) {
							j = Board.BOARD_WIDTH;
							i = Board.BOARD_HEIGHT;
							break;
						}
					}
				}
			}
		}
		
		try {
			game.move(p, n);
		} catch (IllegalGameStateException e) {
			Log.w("AI Michael", e);
		}
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

}
