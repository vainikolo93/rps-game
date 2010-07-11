/**
 * 
 */
package com.kahweh.rps.ai;

import java.util.Random;

import com.kahweh.rps.game.IBoard;

/**
 * @author Michael
 *
 */
public class EngineFactory {
	public static IEngine getEngine(IBoard board, int level, IEngine.DecisionMadeCallback callback, int randomRatio) {
		Random rand = null;

		if (randomRatio > 0) {
			rand = new Random(System.currentTimeMillis());
			if (rand.nextInt(randomRatio) == 0) {
				return new RandomEngine(board, level, callback);
			}
		}

		if (board.getBoardType() == IBoard.BOARD55) {
			return new Engine55(board, level, callback);
		} else if (board.getBoardType() == IBoard.BOARD67) {
			return new Engine67(board, level, callback);
		}
		
		return null;
	}
}
