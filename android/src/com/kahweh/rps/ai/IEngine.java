/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.MoveAction;

/**
 * @author Michael
 *
 */
public interface IEngine {
    public MoveAction getNextMove(int color)
        throws IllegalGameStateException;
}
