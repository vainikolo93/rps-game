/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class ChessPiece {
	public static final byte BLACK_ROCK = 0;
	public static final byte BLACK_PAPER = 1;
	public static final byte BLACK_SCISSORS = 2;
	public static final byte RED_ROCK = 3;
	public static final byte RED_PAPER = 4;
	public static final byte RED_SCISSORS = 5;
	public static final byte BLACK_TRAP = 6;
	public static final byte BLACK_FLAG = 7;
	public static final byte RED_TRAP = 8;
	public static final byte RED_FLAG = 9;

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
}
