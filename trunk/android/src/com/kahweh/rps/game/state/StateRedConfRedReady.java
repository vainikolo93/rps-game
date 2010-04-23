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
			if (r.compareTo(b) == 0) {
				game.noticeConflict(r, b);
				game.setState(game.getStateRedTurnConflict());
				return true;
			}

			Board board = game.getBoard();
			board.move(r, b);

			game.setState(game.getStateBlackTurn());
			game.getBlack().play();
		}
		return true;
	}

}
