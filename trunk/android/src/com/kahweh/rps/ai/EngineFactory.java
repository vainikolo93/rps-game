/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.IBoard;

/**
 * @author Michael
 *
 */
public class EngineFactory {
	public static IEngine getEngine(IBoard board, int level, IEngine.DecisionMadeCallback callback) {
		IEngine eng = null;

		if (board.getBoardType() == IBoard.BOARD55) {
			eng = new Engine55(board, level, callback);
		} else if (board.getBoardType() == IBoard.BOARD67) {
			eng = new Engine67(board, level, callback);
		}

		return eng;
	}
}
