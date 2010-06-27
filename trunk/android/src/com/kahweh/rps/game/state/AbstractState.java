/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

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
		IPlayer winner;
		if (loser.isBlack()) {
			winner = game.getRed();
		} else {
			winner = game.getBlack();
		}
		game.setWinner(winner);

		game.setState(game.getStateFinished());
		try {
			game.getRed().notifyFinish(winner);
			game.getBlack().notifyFinish(winner);
		} catch (IllegalPlayerStateException e) {
			Log.e("AbstractState", "Wrong game state..", e);
		}
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
	
	@Override
	public void gameOver(IPlayer winner) {
		game.setState(game.getStateFinished());
		game.setWinner(winner);
		game.getBoard().openAll();
		
		try {
			game.getRed().notifyFinish(winner);
			game.getBlack().notifyFinish(winner);
		} catch (IllegalPlayerStateException e) {
			Log.e("AbstractState", "Wrong player state..", e);
		}
	}
	
	public void renew() {
		game.setState(game.getStateIdle());
	}
}
