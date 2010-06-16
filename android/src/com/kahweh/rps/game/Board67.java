package com.kahweh.rps.game;

import java.util.Random;

import com.kahweh.rps.game.player.IPlayer;

public class Board67 extends AbstractBoard {

	/**
	 * Constructor.
	 */
	public Board67() {
		this.gridHeight = 50;
		this.gridWidth = 44;
		this.boardMargin = 3;
		this.boardHeight = 6;
		this.boardWidth = 7;
		this.boardAbsHeight = 306;
		this.boardAbsWidth = 314;
		this.pieceNumberPerType = 4;
		
		this.board = new int[6][7];
		this.boardType = IBoard.BOARD67;
	}

//	public static boolean verifyTrap(ChessPiece t) {
//		if (t.isBlack()) {
//			if (t.getType() != ChessPiece.BLACK_TRAP ||
//					t.getRow() > 1 || t.getRow() < 0 || t.getColumn() < 0 || t.getColumn() > 6) {
//				return false;
//			}
//		} else {
//			if (t.getType() != ChessPiece.RED_TRAP ||
//					t.getRow() > 5 || t.getRow() < 4 || t.getColumn() < 0 || t.getColumn() > 6) {
//				return false;
//			}
//		}
//		return true;
//	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#clone()
	 */
	@Override
	public Object clone() {
		Board67 c = new Board67();
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				c.board[i][j] = board[i][j];
			}
		}
		c.black_count = black_count;
		c.red_count = red_count;

		return c;
	}
}
