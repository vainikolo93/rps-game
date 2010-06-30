/**
 * 
 */
package com.kahweh.rps.game;

import com.kahweh.rps.game.player.IPlayer;

/**
 * @author Michael
 *
 */
public class Board55 extends AbstractBoard {

	/**
	 * Constructor.
	 */
	public Board55() {
		this.gridHeight = 60;
		this.gridWidth = 60;
		this.boardMargin = 6;
		this.boardHeight = 5;
		this.boardWidth = 5;
		this.boardAbsHeight = 312;
		this.boardAbsWidth = 312;
		this.pieceNumberPerType = 3;

		this.board = new int[5][5];
		this.boardType = IBoard.BOARD55;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#clone()
	 */
	@Override
	public Object clone() {
		Board55 c = new Board55();
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				c.board[i][j] = board[i][j];
			}
		}
		c.black_count = black_count;
		c.red_count = red_count;

		return c;
	}

	@Override
	public boolean blackOver() {
		if (getBlack_count() == 1) return true;
		return false;
	}

	@Override
	public boolean redOver() {
		if (getRed_count() == 1) return true;
		return false;
	}
}
