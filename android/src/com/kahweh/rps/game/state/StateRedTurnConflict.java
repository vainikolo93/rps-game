/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateRedTurnConflict extends AbstractState {
	
	public StateRedTurnConflict(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece p) throws IllegalGameStateException {
		if (p.isBlack()) {
			game.setBlackConfPiece(p);
			game.setState(game.getStateRedConfBlackReady());
		} else {
			game.setRedConfPiece(p);
			game.setState(game.getStateRedConfRedReady());
		}
		return true;
	}

}
