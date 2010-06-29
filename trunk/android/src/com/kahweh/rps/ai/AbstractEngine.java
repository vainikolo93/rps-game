/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public abstract class AbstractEngine implements IEngine {
	protected IBoard board;
	protected int difficultyLevel;
	DecisionMadeCallback decisionCallback;

	public DecisionMadeCallback getDecisionCallback() {
		return decisionCallback;
	}

	public void setDecisionCallback(DecisionMadeCallback decisionCallback) {
		this.decisionCallback = decisionCallback;
	}

	protected AbstractEngine(IBoard board, int dLevel) {
		this.board = board;
		this.difficultyLevel = dLevel;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.ai.IEngine#getNextMove(com.kahweh.rps.game.IBoard, int, com.kahweh.rps.ai.IEngine.DecisionMadeCallback)
	 */
	@Override
	public void getNextMove(int color, DecisionMadeCallback callback)
	        throws IllegalGameStateException {
		this.decisionCallback = callback;
		
		
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
