/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.Config;
import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateBlackConfBlackReady extends AbstractState {
	
	public StateBlackConfBlackReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece r) throws IllegalGameStateException {
		if (r.isBlack()) {
			Log.e("StateBlackConfBlackReady", "Game State Error, shold be a red piece..");
			throw new IllegalGameStateException("Now..  in Black Turn Conflict Black Ready..");
		} else {
			game.setRedConfPiece(r);
			ChessPiece b = game.getBlackConfPiece();

			game.getBoard().setChessPiece(r);
			game.getBoard().setChessPiece(b);

			game.notifyBoardUpdate();
			
			game.setState(game.getStateBlackTurn());
			if (Config.DEBUG) {
				Log.d("StateBlackConfBlackReady", "convert to StateBlackTurn again...");
			}

			game.move(b, r);
		}
		return true;
	}

}
