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
public class StateBlackConfRedReady extends AbstractState {
	
	public StateBlackConfRedReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece b) throws IllegalGameStateException {
		if (b.isRed()) {
			throw new IllegalGameStateException("Now..  in Black Turn Conflict Red Ready..");
		} else {
			game.setBlackConfPiece(b);
			ChessPiece r = game.getRedConfPiece();
			if (b.compareTo(r) == 0) {
				game.setState(game.getStateBlackTurnConflict());
				if (Config.DEBUG) {
					Log.d("StateBlackConfRedReady", "convert to StateBlackTurnConflict");
				}
				game.noticeConflict(r, b);
				return true;
			}

			Board board = game.getBoard();
			board.move(b, r);

			game.setState(game.getStateRedTurn());
			if (Config.DEBUG) {
				Log.d("StateBlackConfRedReady", "convert to StateRedTurn");
			}
			game.getRed().play();
		}
		return true;
	}

}
