/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateBlackTurn extends AbstractState {
	
	public StateBlackTurn(Game g) {
		this.game = g;
	}

	@Override
	public boolean move(ChessPiece start, ChessPiece dest)
			throws IllegalGameStateException {
		if (!Board.verifyMove(start, dest)) {
			throw new IllegalGameStateException("The original and dest pieces are illegal..");
		}
		//If start is not black
		if (start.isRed()) {
			throw new IllegalGameStateException("The original piece are not black..");
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
		if (dest.getType() == ChessPiece.RED_FLAG) {
			game.setWinner(game.getBlack());
			game.setState(game.getStateFinished());
		}
		//If the chesspiece is the same type
		if (start.compareTo(dest) == 0) {
			//TODO
			game.noticeConflict(start, dest);
			game.setState(game.getStateRedTurnConflict());
		}

		//Move Piece
		Board board = game.getBoard();
		board.move(start, dest);
		
		game.setState(game.getStateRedTurn());
		game.getRed().play();
		return true;
	}

	@Override
	public void concede(IPlayer loser) throws IllegalGameStateException {
		if (loser != game.getBlack()) {
			throw new IllegalGameStateException();
		}

		game.setWinner(game.getRed());
		game.setState(game.getStateFinished());
	}
}
