/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.Config;
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
			if (Config.DEBUG) {
				Log.d("StateRedTurnConflict", "convert to StateRedConfBlackReady");
			}
		} else {
			game.setRedConfPiece(p);
			game.setState(game.getStateRedConfRedReady());
			if (Config.DEBUG) {
				Log.d("StateRedTurnConflict", "convert to StateRedConfRedReady");
			}
		}
		return true;
	}

}
