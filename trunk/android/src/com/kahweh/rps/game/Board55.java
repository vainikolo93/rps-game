/**
 * 
 */
package com.kahweh.rps.game;

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
		
		this.board = new int[5][5];
	}

	@Override
	public ChessPiece translatePosition(int color, float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
