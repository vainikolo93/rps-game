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
	 * @see com.kahweh.rps.game.IBoard#setChessPiece(com.kahweh.rps.game.ChessPiece)
	 */
	public boolean setChessPiece(ChessPiece p) {
		if (p == null) return false;
		board[p.getRow()][p.getColumn()] = p.getType();
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getChessPiece(int, int)
	 */
	public ChessPiece getChessPiece(int row, int column) {
		return new ChessPiece(board[row][column], row, column);
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cleanBoard()
	 */
	public void cleanBoard() {
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (i == 0 || i == 1) {
					board[i][j] = ChessPiece.BLACK_UNKNOW;
				} else if (i == boardHeight - 1 || i == boardHeight -2) {
					board[i][j] = ChessPiece.RED_UNKNOW;
				} else {
					board[i][j] = ChessPiece.BLANK;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#initBoard()
	 */
	public boolean initBoard() {
		int rock = 4, paper = 4, scissors = 4;
		black_count = red_count = 14;
		//init the red pieces
		for (int i = 4; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == ChessPiece.RED_UNKNOW) {
					while (true) {
						int r = rand.nextInt(3);
						if (r == 0) {
							if (rock == 0) continue;
							rock--;
							board[i][j] = ChessPiece.RED_ROCK;
							break;
						}
						if (r == 1) {
							if (paper == 0) continue;
							paper--;
							board[i][j] = ChessPiece.RED_PAPER;
							break;
						}
						if (r == 2) {
							if (scissors == 0) continue;
							scissors--;
							board[i][j] = ChessPiece.RED_SCISSORS;
							break;
						}
					}
				}
			}
		}
		//init the black pieces
		rock = 4;
		paper = 4;
		scissors = 4;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == ChessPiece.BLACK_UNKNOW) {
					while (true) {
						int r = rand.nextInt(3);
						if (r == 0) {
							if (rock == 0) continue;
							rock--;
							board[i][j] = ChessPiece.BLACK_ROCK;
							break;
						}
						if (r == 1) {
							if (paper == 0) continue;
							paper--;
							board[i][j] = ChessPiece.BLACK_PAPER;
							break;
						}
						if (r == 2) {
							if (scissors == 0) continue;
							scissors--;
							board[i][j] = ChessPiece.BLACK_SCISSORS;
							break;
						}
					}
				}
			}
		}
		
		return true;
	}

	public static boolean verifyFlag(ChessPiece f) {
		if (f.isBlack()) {
			if (f.getType() != ChessPiece.BLACK_FLAG ||
					f.getRow() > 1 || f.getRow() < 0 || f.getColumn() < 0 || f.getColumn() > 6) {
				return false;
			}
		} else {
			if (f.getType() != ChessPiece.RED_FLAG ||
					f.getRow() > 5 || f.getRow() < 4 || f.getColumn() < 0 || f.getColumn() > 6) {
				return false;
			}
		}
		return true;
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

	public ChessPiece translatePosition(int color, float x, float y) {
		if (x > boardAbsWidth || x < 0) return null;
		if (y > boardAbsHeight || y < 0) return null;

		int ix = (int)x;
		int iy = (int)y;
		ix = (ix - boardMargin) / gridWidth;
		iy = (iy - boardMargin) / gridHeight;
		if (ix >= boardWidth) ix = boardWidth - 1;
		if (iy >= boardHeight) iy = boardHeight - 1;

		if (color == IPlayer.BLACK) {
			ix = boardWidth - ix - 1;
			iy = boardHeight - iy - 1;
		}

		ChessPiece p = new ChessPiece(ChessPiece.BLANK, iy, ix);
		return p;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#openAll()
	 */
	public void openAll() {
		for (int i=0; i<boardHeight; i++)
			for (int j=0; j<boardWidth; j++) {
				if (!ChessPiece.isBlank(board[i][j])) {
					board[i][j] = ChessPiece.open(board[i][j]);
				}
			}
	}
}
