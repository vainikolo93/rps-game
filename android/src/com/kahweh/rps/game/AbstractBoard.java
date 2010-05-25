/**
 * 
 */
package com.kahweh.rps.game;

import java.util.Random;

/**
 * @author b448
 *
 */
public abstract class AbstractBoard implements IBoard {
	
	protected int gridWidth;
	protected int gridHeight;
	protected int boardMargin;
	protected int boardHeight;
	protected int boardWidth;
	protected int boardAbsHeight;
	protected int boardAbsWidth;

	protected int[][] board;

	protected Random rand = new Random(System.currentTimeMillis());
	
	protected int black_count;
	protected int red_count;
	
	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cleanBoard()
	 */
	@Override
	public void cleanBoard() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cloneBoard(int)
	 */
	@Override
	public IBoard cloneBoard(int color) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBlack_count()
	 */
	@Override
	public int getBlack_count() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoard()
	 */
	@Override
	public int[][] getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardAbsHeight()
	 */
	@Override
	public int getBoardAbsHeight() {
		return this.boardAbsHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardAbsWidth()
	 */
	@Override
	public int getBoardAbsWidth() {
		return this.boardAbsWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardHeight()
	 */
	@Override
	public int getBoardHeight() {
		return this.boardHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardMargin()
	 */
	@Override
	public int getBoardMargin() {
		return this.boardMargin;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardWidth()
	 */
	@Override
	public int getBoardWidth() {
		return this.boardWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getChessPiece(int, int)
	 */
	@Override
	public ChessPiece getChessPiece(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getGridHeight()
	 */
	@Override
	public int getGridHeight() {
		return this.gridHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getGridWidth()
	 */
	@Override
	public int getGridWidth() {
		return this.gridWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getNeighborChessPiece(com.kahweh.rps.game.ChessPiece, int)
	 */
	@Override
	public ChessPiece getNeighborChessPiece(ChessPiece p, int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getRed_count()
	 */
	@Override
	public int getRed_count() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#initBoard()
	 */
	@Override
	public boolean initBoard() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#move(com.kahweh.rps.game.ChessPiece, com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public void move(ChessPiece start, ChessPiece dest) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#openAll()
	 */
	@Override
	public void openAll() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#setChessPiece(com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public boolean setChessPiece(ChessPiece p) {
		// TODO Auto-generated method stub
		return false;
	}
}
