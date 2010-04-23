package com.kahweh.rps.game.state;

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
				game.noticeConflict(r, b);
				game.setState(game.getStateBlackTurnConflict());
				return true;
			}

			Board board = game.getBoard();
			board.move(b, r);

			game.setState(game.getStateRedTurn());
			game.getRed().play();
		}
		return true;
	}

}
