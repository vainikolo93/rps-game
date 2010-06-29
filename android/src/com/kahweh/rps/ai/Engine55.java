/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.ai.IEngine.DecisionMadeCallback;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class Engine55 extends AbstractEngine {
	public Engine55(IBoard b, int dLevel) {
		super(b, dLevel);
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.ai.IEngine#getNextMove(com.kahweh.rps.game.IBoard, int, com.kahweh.rps.ai.IEngine.DecisionMadeCallback)
	 */
	@Override
	public void getNextMove(int color, DecisionMadeCallback callback) {
		this.decisionCallback = callback;
		
	}
}
