/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.Config;
import com.kahweh.rps.game.Board67;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateRedConfRedReady extends AbstractState {
	
	public StateRedConfRedReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece b) throws IllegalGameStateException {
		if (b.isRed()) {
			throw new IllegalGameStateException("Now..  in Red Turn Conflict Red Ready..");
		} else {
			game.setBlackConfPiece(b);
			ChessPiece r = game.getRedConfPiece();
			
			game.getBoard().setChessPiece(r);
			game.getBoard().setChessPiece(b);

			game.notifyBoardUpdate();
			
			game.setState(game.getStateRedTurn());
			if (Config.DEBUG) {
				Log.d("StateRedConfRedReady", "convert to StateRedTurn again...");
			}

			game.move(r, b);
		}
		return true;
	}

}
