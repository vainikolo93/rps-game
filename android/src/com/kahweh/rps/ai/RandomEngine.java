/**
 * 
 */
package com.kahweh.rps.ai;

import java.util.Random;

import android.util.Log;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.MoveAction;
import com.kahweh.rps.game.player.IPlayer;

/**
 * This is engine is used to generate a random move.
 * 
 * @author michael
 *
 */
public class RandomEngine extends AbstractEngine {
	private static String TAG = "com.kahweh.rps.ai.RandomEngine";
	
	private Random rand;
	
	protected RandomEngine(IBoard board, int dLevel) {
		super(board, dLevel);
		
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public MoveAction getNextMove(int color)
	        throws IllegalGameStateException {
		ChessPiece ori = null, des = null;
		ChessPiece t1 = null, t2 = null;

		for (int i = 0; i < board.getBoardHeight(); i++) {
			for (int j = 0; j < board.getBoardWidth(); j++) {
				t1 = board.getChessPiece(i, j);
				if (color == IPlayer.BLACK && t1.isBlack() && t1.isMovable()) {
					//Black player and get a movable black piece
					for (int k = 0; k < 4; k++) {
						t2 = board.getNeighborChessPiece(t1, k);
						if (t2 != null && (t2.isBlank() || t2.isRed())) {
							if (ori == null || rand.nextInt(2) == 0) {
								ori = t1;
								des = t2;
							}
						}
					}
				} else if (color == IPlayer.RED && t1.isRed() && t1.isMovable()){
					//Red player and get a movable red piece
					for (int k = 0; k < 4; k++) {
						t2 = board.getNeighborChessPiece(t1, k);
						if (t2 != null && (t2.isBlank() || t2.isBlack())) {
							if (ori == null || rand.nextInt(2) == 0) {
								ori = t1;
								des = t2;
							}
						}
					}
				}
			}
		}

		try {
			Thread.sleep(rand.nextInt(3000));
		} catch (InterruptedException e) {
			Log.w(TAG, "Interrupted..", e);
		}

		return new MoveAction(ori, des);
	}
}
