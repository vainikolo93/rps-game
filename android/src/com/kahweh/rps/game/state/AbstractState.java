/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public abstract class AbstractState implements IGameState {
	protected Game game;

	@Override
	public boolean makeChoice(ChessPiece p) throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

	@Override
	public boolean move(ChessPiece start, ChessPiece dest)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

	@Override
	public boolean newGame(IPlayer red, IPlayer black)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

	@Override
	public boolean placeTrapAndFlag(ChessPiece flag, ChessPiece trap)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

	@Override
	public void concede(IPlayer loser) throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}
	
	@Override
	public void quitGame(IPlayer badGuy) {
		game.setState(game.getStateIdle());
		if (badGuy == game.getRed()) {
			game.getBlack().notifyQuit();
		} else {
			game.getRed().notifyQuit();
		}
	}
}
