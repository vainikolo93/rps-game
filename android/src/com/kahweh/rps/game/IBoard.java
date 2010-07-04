package com.kahweh.rps.game;

public interface IBoard {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public static final int BOARD55 = 0;
	public static final int BOARD67 = 1;

	public abstract int getBlack_count();

	public abstract int getRed_count();

	public abstract int[][] getBoard();

	public abstract boolean setChessPiece(ChessPiece p);

	public abstract ChessPiece getChessPiece(int row, int column);

	public abstract void cleanBoard();

	public abstract boolean initBoard();

	public abstract void move(ChessPiece start, ChessPiece dest);

	public abstract IBoard cloneBoard(int color) throws CloneNotSupportedException;

	public abstract ChessPiece getNeighborChessPiece(ChessPiece p, int pos);

	public abstract ChessPiece translatePosition(int color, float x, float y);

	/**
	 * This function used to open all the closed pieces when game was over..  
	 */
	public abstract void openAll();

	public abstract int getGridWidth();
	public abstract int getGridHeight();
	public abstract int getBoardMargin();
	public abstract int getBoardHeight();
	public abstract int getBoardWidth();
	public abstract int getBoardAbsHeight();
	public abstract int getBoardAbsWidth();
	/**
	 * This function is used to get the number of each type pieces.
	 * For example, the 5*5 board will have 3 pieces per type. And
	 * the 6*7 board have 4 pieces per type.
	 * 
	 * @return
	 */
	public abstract int getPieceNumber();

	public boolean verifyFlag(ChessPiece f);
	public boolean verifyTrap(ChessPiece t);
	
	public int getBoardType();

	boolean verifyMove(ChessPiece s, ChessPiece d);

	public abstract boolean blackOver();

	public abstract boolean redOver();
}