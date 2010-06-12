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
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBlack_count()
	 */
	public int getBlack_count() {
		return black_count;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getRed_count()
	 */
	public int getRed_count() {
		return red_count;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoard()
	 */
	public int[][] getBoard() {
		return board;
	}
	
	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getChessPiece(int, int)
	 */
	public ChessPiece getChessPiece(int row, int column) {
		return new ChessPiece(board[row][column], row, column);
	}

	public static boolean verifyTrap(ChessPiece t) {
		if (t.isBlack()) {
			if (t.getType() != ChessPiece.BLACK_TRAP ||
					t.getRow() > 1 || t.getRow() < 0 || t.getColumn() < 0 || t.getColumn() > 6) {
				return false;
			}
		} else {
			if (t.getType() != ChessPiece.RED_TRAP ||
					t.getRow() > 5 || t.getRow() < 4 || t.getColumn() < 0 || t.getColumn() > 6) {
				return false;
			}
		}
		return true;
	}

	public static boolean verifyFlagAndTrap(ChessPiece f, ChessPiece t) {
		if (f.isBlack()) {
			if (t.getType() != ChessPiece.BLACK_TRAP ||
					f.getType() != ChessPiece.BLACK_FLAG ||
					f.getRow() == t.getRow() && f.getColumn() == t.getColumn() ||
					f.getRow() > 1 || f.getRow() < 0 || f.getColumn() < 0 || f.getColumn() > 6 ||
					t.getRow() > 1 || t.getRow() < 0 || t.getColumn() < 0 || t.getColumn() > 6) {
				return false;
			}
		} else {
			if (t.getType() != ChessPiece.RED_TRAP ||
					f.getType() != ChessPiece.RED_FLAG ||
					f.getRow() == t.getRow() && f.getColumn() == t.getColumn() ||
					f.getRow() > 5 || f.getRow() < 4 || f.getColumn() < 0 || f.getColumn() > 6 ||
					t.getRow() > 5 || t.getRow() < 4 || t.getColumn() < 0 || t.getColumn() > 6) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#move(com.kahweh.rps.game.ChessPiece, com.kahweh.rps.game.ChessPiece)
	 */
	public void move(ChessPiece start, ChessPiece dest) {
		if (start.compareTo(dest) > 0) {
			if (dest.isBlack()) {
				black_count--;
			} else if (dest.isRed()) {
				red_count--;
			}
			if (!dest.isBlank()) {
				start = start.open();
			}
			dest.setType(start.getType());
			start.setType(ChessPiece.BLANK);
			setChessPiece(dest);
			setChessPiece(start);
		} else {
			if (start.isBlack()) {
				black_count--;
			} else {
				red_count--;
			}
			start.setType(ChessPiece.BLANK);
			dest = dest.open();
			setChessPiece(start);
			setChessPiece(dest);
		}
	}

	public static boolean verifyMove(IBoard board, ChessPiece s, ChessPiece d) {
		if (!ChessPiece.verifyPiece(s, board) || !ChessPiece.verifyPiece(d, board)) return false;
		if (ChessPiece.sameColor(s, d)) return false;
		if (Math.abs(s.getRow() - d.getRow()) 
				+ Math.abs(s.getColumn() - d.getColumn()) != 1) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cloneBoard(int)
	 */
	public IBoard cloneBoard(int color) {
		IBoard b = (IBoard)this.clone();
		if (color == IPlayer.RED) {
			for (int i = 0; i < boardHeight; i++) {
				for (int j = 0; j < boardWidth; j++) {
					ChessPiece p = new ChessPiece(board[i][j], i, j);
					if (p.isBlack() && !p.isOpen()) {
						p.setType(ChessPiece.BLACK_UNKNOW);
						b.setChessPiece(p);
					}
				}
			}
		} else {
			for (int i = 0; i < boardHeight; i++) {
				for (int j = 0; j < boardWidth; j++) {
					ChessPiece p = new ChessPiece(board[i][j], i, j);
					if (p.isRed() && !p.isOpen()) {
						p.setType(ChessPiece.RED_UNKNOW);
						b.setChessPiece(p);
					}
				}
			}
		}
		return b;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getNeighborChessPiece(com.kahweh.rps.game.ChessPiece, int)
	 */
	public ChessPiece getNeighborChessPiece(ChessPiece p, int pos) {
		ChessPiece n = null;
		switch (pos) {
		case IBoard.UP:
			if (p.getRow() - 1 < 0) return null;
			n = getChessPiece(p.getRow() - 1, p.getColumn());
			break;
		case IBoard.DOWN:
			if (p.getRow() + 1 >= boardHeight) return null;
			n = getChessPiece(p.getRow() + 1, p.getColumn());
			break;
		case IBoard.LEFT:
			if (p.getColumn() - 1 < 0) return null;
			n = getChessPiece(p.getRow(), p.getColumn() - 1);
			break;
		case IBoard.RIGHT:
			if (p.getColumn() + 1 >= boardWidth) return null;
			n = getChessPiece(p.getRow(), p.getColumn() + 1);
			break;
		}

		return n;
	}
	
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

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#openAll()
	 */
	public void openAll() {
		for (int i=0; i<boardHeight; i++) {
			for (int j=0; j<boardWidth; j++) {
				if (!ChessPiece.isBlank(board[i][j])) {
					board[i][j] = ChessPiece.open(board[i][j]);
				}
			}
		}
	}
}
