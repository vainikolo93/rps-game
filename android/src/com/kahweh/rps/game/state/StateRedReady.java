/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateRedReady extends AbstractState {
	
	public StateRedReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean placeTrapAndFlag(ChessPiece f, ChessPiece t)
			throws IllegalGameStateException {
		if (f.isRed() || t.isRed()) {
			throw new IllegalArgumentException("Now.. Red is ready..");
		}
		if (!Board.verifyFlagAndTrap(f, t)) {
			throw new IllegalArgumentException();
		}

		Board b = game.getBoard();
		b.setChessPiece(f);
		b.setChessPiece(t);
		game.setState(game.getStateRedTurn());

		return true;
	}
}
