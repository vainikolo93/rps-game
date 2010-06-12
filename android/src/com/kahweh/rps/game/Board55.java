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

		this.board = new int[5][5];
	}
}
