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
	public static IEngine getEngine(IBoard board, int level, int randomRatio) {
		Random rand = null;

		if (randomRatio > 0) {
			rand = new Random(System.currentTimeMillis());
			if (rand.nextInt(randomRatio) == 0) {
				return new RandomEngine(board, level);
			}
		}

		if (board.getBoardType() == IBoard.BOARD55) {
			return new Engine55(board, level);
		} else if (board.getBoardType() == IBoard.BOARD67) {
			return new Engine67(board, level);
		}
		
		return null;
	}
	
	public static IEngine getRandomEngine(IBoard board, int level) {
		return new RandomEngine(board, level);
	}
}
