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
public class StateRedConfBlackReady extends AbstractState {
	
	public StateRedConfBlackReady(Game g) {
		this.game = g;
	}

	@Override
	public boolean makeChoice(ChessPiece r) throws IllegalGameStateException {
		if (r.isBlack()) {
			Log.e("StateRedConfBlackReady", "Game State Error, shold be a red piece..");
			throw new IllegalGameStateException("Now..  in Red Turn Conflict Black Ready..");
		} else {
			game.setRedConfPiece(r);
			ChessPiece b = game.getBlackConfPiece();
			
			game.getBoard().setChessPiece(r);
			game.getBoard().setChessPiece(b);

			game.notifyBoardUpdate();
			
			game.setState(game.getStateRedTurn());
			if (Config.DEBUG) {
				Log.d("StateRedConfBlackReady", "convert to StateRedTurn again...");
			}

			game.move(r, b);

//			if (r.compareTo(b) == 0) {
//				game.setState(game.getStateRedTurnConflict());
//				if (Config.DEBUG) {
//					Log.d("StateRedConfBlackReady", "convert to StateRedTurnConflict");
//				}
//				game.noticeConflict(r, b);
//				return true;
//			}
//
//			Board board = game.getBoard();
//			board.move(r, b);
//
//			game.setState(game.getStateBlackTurn());
//			if (Config.DEBUG) {
//				Log.d("StateRedConfBlackReady", "convert to StateBlackTurn");
//			}
//			game.getBlack().play();
		}
		return true;
	}

}
