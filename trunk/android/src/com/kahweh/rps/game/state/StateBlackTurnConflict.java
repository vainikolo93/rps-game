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
public class StateBlackTurnConflict extends AbstractState {
	
	public StateBlackTurnConflict(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece p) throws IllegalGameStateException {
		if (p.isBlack()) {
			game.setBlackConfPiece(p);
			game.setState(game.getStateBlackConfBlackReady());
		} else {
			game.setRedConfPiece(p);
			game.setState(game.getStateBlackConfRedReady());
		}
		return true;
	}

}
