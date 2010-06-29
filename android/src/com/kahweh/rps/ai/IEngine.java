/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public interface IEngine {
    public interface DecisionMadeCallback {
    	public void move(ChessPiece from, ChessPiece to);
    }

    public void getNextMove(int color, DecisionMadeCallback callback)
        throws IllegalGameStateException;
}
