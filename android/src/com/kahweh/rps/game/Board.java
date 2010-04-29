package com.kahweh.rps.game;

import java.util.Random;

public class Board {
	public static final int BOARD_HEIGHT = 6;
	public static final int BOARD_WIDTH = 7;
	private byte[][] board = new byte[BOARD_HEIGHT][BOARD_WIDTH];
	private Random rand = new Random(System.currentTimeMillis());

	private int black_count;
	private int red_count;

	public int getBlack_count() {
		return black_count;
	}

	public int getRed_count() {
		return red_count;
	}

	public Board() {}

	public byte[][] getBoard() {
		return board;
	}
	
	public boolean setChessPiece(ChessPiece p) {
		if (p == null) return false;
		board[p.getRow()][p.getColumn()] = p.getType();
		return true;
	}
	
	public ChessPiece getChessPiece(int row, int column) {
		return new ChessPiece(board[row][column], row, column);
	}

	public void cleanBoard() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = ChessPiece.BLANK;
			}
		}
	}

	public boolean initBoard() {
		int rock = 4, paper = 4, scissors = 4;
		black_count = red_count = 14;
		//init the red pieces
		for (int i = 4; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (board[i][j] == ChessPiece.BLANK) {
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
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (board[i][j] == ChessPiece.BLANK) {
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

	public void move(ChessPiece start, ChessPiece dest) {
		if (start.compareTo(dest) > 0) {
			if (dest.isBlack()) {
				black_count--;
			} else {
				red_count--;
			}
			start = start.open();
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

	public static boolean verifyMove(ChessPiece s, ChessPiece d) {
		if (!ChessPiece.verifyPiece(s) || !ChessPiece.verifyPiece(d)) return false;
		if (ChessPiece.sameColor(s, d)) return false;
		if (Math.abs(s.getRow() - d.getRow()) 
				+ Math.abs(s.getColumn() - d.getColumn()) != 1) return false;
		return true;
	}

	public Board cloneBoard(int color) {
		Board b = (Board)this.clone();
		if (color == IPlayer.RED) {
			for (int i = 0; i < BOARD_HEIGHT; i++) {
				for (int j = 0; j < BOARD_WIDTH; j++) {
					ChessPiece p = new ChessPiece(board[i][j], i, j);
					if (p.isBlack() && !p.isOpen()) {
						p.setType(ChessPiece.BLACK_UNKNOW);
						b.setChessPiece(p);
					}
				}
			}
		} else {
			for (int i = 0; i < BOARD_HEIGHT; i++) {
				for (int j = 0; j < BOARD_WIDTH; j++) {
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

	@Override
	public Object clone() {
		Board c = new Board();
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				c.board[i][j] = board[i][j];
			}
		}
		c.black_count = black_count;
		c.red_count = red_count;

		return c;
	}
	
}
