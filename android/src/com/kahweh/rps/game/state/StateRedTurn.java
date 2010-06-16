/**
 * 
 */
package com.kahweh.rps.game.state;

import android.util.Log;

import com.kahweh.rps.Config;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;

/**
 * @author Michael
 *
 */
public class StateRedTurn extends AbstractState {
	
	public StateRedTurn(Game g) {
		this.game = g;
	}

	@Override
	public boolean move(ChessPiece start, ChessPiece dest)
			throws IllegalGameStateException {
		if (!game.getBoard().verifyMove(start, dest)) {
			throw new IllegalGameStateException("The original and dest pieces are illegal..");
		}
		//If start is not red
		if (start.isBlack()) {
			throw new IllegalGameStateException("The original piece are not red..");
		}
		//If the start and dest pieces are same color, will throw an exception
		if (ChessPiece.sameColor(start, dest)) {
			throw new IllegalGameStateException("The original and dest piece are same color..");
		}
		//If the start piece is not movable, throw an exception
		if (!start.isMovable()) {
			throw new IllegalGameStateException("The original ChessPiece is not movable..");
		}
		//If the dest piece is Black Flag the the red WIN...
		if (dest.getType() == ChessPiece.BLACK_FLAG) {
			gameOver(game.getRed());
			return true;
		}

		//If the chesspiece is the same type
		if (start.compareTo(dest) == 0) {
			game.setState(game.getStateRedTurnConflict());
			if (Config.DEBUG) {
				Log.d("StateRedTurn", "convert to StateRedTurnConflict");
			}
			game.noticeConflict(start, dest);
			return true;
		}

		//Move Piece
		IBoard board = game.getBoard();
		board.move(start, dest);

		game.setState(game.getStateBlackTurn());
		if (Config.DEBUG) {
			Log.d("StateRedTurn", "convert to StateBlackTurn");
		}
		game.getBlack().play();
		return true;
	}

	@Override
	public void concede(IPlayer loser) throws IllegalGameStateException {
		if (loser != game.getRed()) {
			throw new IllegalGameStateException();
		}

		game.setWinner(game.getBlack());
		game.setState(game.getStateFinished());
	}

}
