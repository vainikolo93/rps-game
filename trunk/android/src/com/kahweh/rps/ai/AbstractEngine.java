/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.MoveAction;

/**
 * @author Michael
 *
 */
public abstract class AbstractEngine implements IEngine {
	protected IBoard board;
	protected int difficultyLevel;

	protected AbstractEngine(IBoard board, int dLevel) {
		this.board = board;
		this.difficultyLevel = dLevel;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.ai.IEngine#getNextMove(com.kahweh.rps.game.IBoard, int, com.kahweh.rps.ai.IEngine.DecisionMadeCallback)
	 */
	@Override
	public MoveAction getNextMove(int color)
	        throws IllegalGameStateException {
		throw new IllegalGameStateException("Method of Abstract class..");
	}

	public IBoard getBoard() {
		return board;
	}

	public void setBoard(IBoard board) {
		this.board = board;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
}
