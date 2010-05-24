/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.game.Board67;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

/**
 * @author Michael
 *
 */
public class StateBlackReady extends AbstractState {
	
	public StateBlackReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean placeTrapAndFlag(ChessPiece f, ChessPiece t)
			throws IllegalGameStateException {
		if (f.isBlack() || t.isBlack()) {
			throw new IllegalArgumentException("Now.. Red is ready..");
		}
		if (!Board67.verifyFlagAndTrap(f, t)) {
			throw new IllegalArgumentException();
		}

		IBoard b = game.getBoard();
		b.setChessPiece(f);
		b.setChessPiece(t);
		b.initBoard();
		try {
			game.getRed().boardInitialized();
			game.getBlack().boardInitialized();
		} catch (IllegalPlayerStateException e) {
			Log.w(StateRedReady.class.getSimpleName(), "Wrong player state..", e);
			throw new IllegalArgumentException(e);
		}
		game.setState(game.getStateRedTurn());
		game.getRed().play();
		return true;
	}
}
