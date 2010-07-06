/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.IBoard;

/**
 * @author Michael
 *
 */
public class Engine55 extends AbstractEngine {
	public Engine55(IBoard b, int dLevel, IEngine.DecisionMadeCallback callback) {
		super(b, dLevel, callback);
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.ai.IEngine#getNextMove(com.kahweh.rps.game.IBoard, int, com.kahweh.rps.ai.IEngine.DecisionMadeCallback)
	 */
	@Override
	public void getNextMove(int color, DecisionMadeCallback callback) {
		this.decisionCallback = callback;
		
	}
}
