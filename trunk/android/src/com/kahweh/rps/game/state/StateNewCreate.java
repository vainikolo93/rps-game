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
public class StateNewCreate extends AbstractState {
	
	public StateNewCreate(Game game) {
		this.game = game;
	}

	@Override
	public boolean placeTrapAndFlag(ChessPiece f, ChessPiece t)
			throws IllegalGameStateException {
		if (!Board.verifyFlagAndTrap(f, t)) {
			throw new IllegalArgumentException();
		}

		Board b = game.getBoard();
		b.setChessPiece(f);
		b.setChessPiece(t);
		game.setWinner(null);
		if (f.isBlack()) {
			game.setState(game.getStateBlackReady());
		} else {
			game.setState(game.getStateRedReady());
		}
		return true;
	}

}
