/**
 * 
 */
package com.kahweh.rps.game;

import java.util.HashSet;
import java.util.Set;

/**
 * If the 8th bit of type is 1, then the chess is open.
 * 
 * @author Michael
 *
 */
public class ChessPiece implements Comparable<ChessPiece> {
	public static final int BLANK = 0;
	public static final int BLACK_ROCK = 9; //1001
	public static final int BLACK_PAPER = 10; //1010
	public static final int BLACK_SCISSORS = 11; //1011
	public static final int BLACK_TRAP = 12; //1100
	public static final int BLACK_FLAG = 13; //1101
	public static final int BLACK_UNKNOW = 14; //1110
	public static final int RED_ROCK = 1; //0001
	public static final int RED_PAPER = 2; //0010
	public static final int RED_SCISSORS = 3; //0011
	public static final int RED_TRAP = 4; //0100
	public static final int RED_FLAG = 5; //0101
	public static final int RED_UNKNOW = 6; //0110

	public static Set<Integer> PIECE_SET = new HashSet<Integer>(){{
		add(BLANK);
		add(BLACK_ROCK);
		add(BLACK_PAPER);
		add(BLACK_SCISSORS);
		add(BLACK_TRAP);
		add(BLACK_FLAG);
		add(RED_ROCK);
		add(RED_PAPER);
		add(RED_SCISSORS);
		add(RED_TRAP);
		add(RED_FLAG);
		add(BLACK_UNKNOW);
		add(RED_UNKNOW);
		add((int)(BLACK_ROCK | 128));
		add((int)(BLACK_PAPER | 128));
		add((int)(BLACK_SCISSORS | 128));
		add((int)(RED_ROCK | 128));
		add((int)(RED_PAPER | 128));
		add((int)(RED_SCISSORS | 128));
		add((int)(BLACK_TRAP | 128));
		add((int)(RED_TRAP | 128));
	}};

	private int type;
	private int row;
	private int column;
	
	public ChessPiece(int type, int row, int column) {
		this.type = type;
		this.row = row;
		this.column = column;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public static boolean sameColor(ChessPiece p1, ChessPiece p2) {
		if (p1.getType() == p2.getType()) return true;
		if (p1.getType() == ChessPiece.BLANK || p2.getType() == ChessPiece.BLANK) return false;
		return  ((p1.getType() & 8) == (p2.getType() & 8)); 
	}
	
	@Override
	public int compareTo(ChessPiece p) {
		int thisPiece = type & 7;
		int thatPiece = p.getType() & 7;
		if (thisPiece == thatPiece) return 0;

		switch (thisPiece) {
		case 0:
			//Blank
			if (thatPiece == 1) return -1;
			if (thatPiece == 2) return -1;
			if (thatPiece == 3) return -1;
		case 1:
			//Rock
			if (thatPiece == 0) return 1;
			if (thatPiece == 2) return -1;
			if (thatPiece == 3) return 1;
			if (thatPiece == 4) return -1;
			if (thatPiece == 5) return 1;
			break;
		case 2:
			//Paper
			if (thatPiece == 0) return 1;
			if (thatPiece == 1) return 1;
			if (thatPiece == 3) return -1;
			if (thatPiece == 4) return -1;
			if (thatPiece == 5) return 1;
			break;
		case 3:
			//Scissors
			if (thatPiece == 0) return 1;
			if (thatPiece == 1) return -1;
			if (thatPiece == 2) return 1;
			if (thatPiece == 4) return -1;
			if (thatPiece == 5) return 1;
			break;
		case 4:
			//Trap
			if (thatPiece == 1) return 1;
			if (thatPiece == 2) return 1;
			if (thatPiece == 3) return 1;
			break;
		case 5:
			//Flag
			if (thatPiece == 1) return -1;
			if (thatPiece == 2) return -1;
			if (thatPiece == 3) return -1;
			break;
		}

		return -1;
	}
	
	public boolean isMovable() {
		int i = type & 7;
		return (i > 0 && i < 4);
	}
	
	public boolean isOpen() {
		int i = type & 128;
		return (i > 0);
	}
	
	public static boolean isOpen(int p) {
		p = p & 128;
		return (p > 0);
	}

	/**
	 * Make this chesspiece open.
	 */
	public ChessPiece open() {
		return new ChessPiece(type | 128, row, column);
	}

	public boolean equals(Object o) {
		if (!(o instanceof ChessPiece)) {
			return false;
		}
		
		ChessPiece p = (ChessPiece)o;
		return (this.getType() == p.getType());
	}
	
	public boolean isRed() {
		return (type != BLANK && (type & 8) == 0);
	}
	
	public boolean isBlack() {
		return ((type & 8) > 0);
	}

	public boolean isBlank() {
		return (type == ChessPiece.BLANK);
	}
	
	public static boolean verifyPiece(ChessPiece p) {
		if (!PIECE_SET.contains(p.getType())) return false;
		if (p.getRow() < 0 || p.getRow() >= Board.BOARD_HEIGHT) return false;
		if (p.getColumn() < 0 || p.getColumn() >=  Board.BOARD_WIDTH) return false;
		return true;
	}
	
	public boolean isUnknow() {
		return ((type | 6) == 6);
	}
	
	/**
	 * This function is used to convert a piece type to close status. If 
	 * a Piece is already a close piece, then do nothing.
	 * 
	 * @param p
	 * @return
	 */
	public static int toClosePiece(int p) {
		return p & 15;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (isBlank()) {
			sb.append("Blank ");
		} else {
			sb.append(isRed()?"Red ":"Black ");
		}
		sb.append(type + ": ");
		sb.append(row + " " + column);
		return sb.toString();
	}
}
