/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;

/**
 * @author Michael
 *
 */
public class StateBlackReady extends AbstractState {
	
	public StateBlackReady(Game g) {
		this.game = g;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IGameState#newGame(com.kahweh.rps.game.IPlayer, com.kahweh.rps.game.IPlayer)
	 */
	@Override
	public boolean newGame(IPlayer red, IPlayer black)
			throws IllegalGameStateException {
		throw new IllegalArgumentException("now.. Black ready..");
	}

	@Override
	public boolean placeTrapAndFlag(ChessPiece f, ChessPiece t)
			throws IllegalGameStateException {
		if (f.isBlack() || t.isBlack()) {
			throw new IllegalArgumentException("Now.. Red is ready..");
		}
		if (!Board.verifyFlagAndTrap(f, t)) {
			throw new IllegalArgumentException();
		}

		Board b = game.getBoard();
		b.setChessPiece(f);
		b.setChessPiece(t);
		b.initBoard();
		game.getRed().boardUpdated();
		game.getBlack().boardUpdated();
		game.setState(game.getStateRedTurn());
		game.getRed().play();
		return true;
	}
}
