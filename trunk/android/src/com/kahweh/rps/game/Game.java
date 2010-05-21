/**
 * 
 */
package com.kahweh.rps.game;

import android.util.Log;

import com.kahweh.rps.Config;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.state.IGameState;
import com.kahweh.rps.game.state.StateBlackConfBlackReady;
import com.kahweh.rps.game.state.StateBlackConfRedReady;
import com.kahweh.rps.game.state.StateBlackReady;
import com.kahweh.rps.game.state.StateBlackTurn;
import com.kahweh.rps.game.state.StateBlackTurnConflict;
import com.kahweh.rps.game.state.StateBlocked;
import com.kahweh.rps.game.state.StateFinished;
import com.kahweh.rps.game.state.StateIdle;
import com.kahweh.rps.game.state.StateNewCreate;
import com.kahweh.rps.game.state.StateRedConfBlackReady;
import com.kahweh.rps.game.state.StateRedConfRedReady;
import com.kahweh.rps.game.state.StateRedReady;
import com.kahweh.rps.game.state.StateRedTurn;
import com.kahweh.rps.game.state.StateRedTurnConflict;

/**
 * @author Michael
 *
 */
public class Game {
	private IPlayer black;
	private IPlayer red;

	private Board board;

	private IGameState stateIdle;
	private IGameState stateNewCreate;
	private IGameState stateRedReady;
	private IGameState stateBlackReady;
	private IGameState stateRedTurn;
	private IGameState stateBlackTurn;
	private IGameState stateBlocked;
	private IGameState stateFinished;
	private IGameState stateBlackConfBlackReady;
	private IGameState stateBlackConfRedReady;
	private IGameState stateRedConfBlackReady;
	private IGameState stateRedConfRedReady;
	private IGameState stateBlackTurnConflict;
	private IGameState stateRedTurnConflict;

	private IGameState state;

	private IPlayer winner;

	private ChessPiece redConfPiece;
	private ChessPiece blackConfPiece;

	public ChessPiece getRedConfPiece() {
		return redConfPiece;
	}

	public void setRedConfPiece(ChessPiece redConfPiece) {
		this.redConfPiece = redConfPiece;
	}

	public ChessPiece getBlackConfPiece() {
		return blackConfPiece;
	}

	public void setBlackConfPiece(ChessPiece blackConfPiece) {
		this.blackConfPiece = blackConfPiece;
	}

	public IPlayer getWinner() {
		return winner;
	}

	public void setWinner(IPlayer winner) {
		this.winner = winner;
	}

	public Game() {
		board = new  Board();

		stateNewCreate = new StateNewCreate(this);
		stateRedReady = new StateRedReady(this);
		stateBlackReady = new StateBlackReady(this);
		stateRedTurn = new StateRedTurn(this);
		stateBlackTurn = new StateBlackTurn(this);
		stateBlocked = new StateBlocked(this);
		stateFinished = new StateFinished(this);
		stateIdle = new StateIdle(this);
		stateBlackTurnConflict = new StateBlackTurnConflict(this);
		stateRedTurnConflict = new StateRedTurnConflict(this);
		stateBlackConfBlackReady = new StateBlackConfBlackReady(this);
		stateBlackConfRedReady = new StateBlackConfRedReady(this);
		stateRedConfRedReady = new StateRedConfRedReady(this);
		stateRedConfBlackReady = new StateRedConfBlackReady(this);

		state = stateIdle;
	}
	
	public IGameState getState() {
		return state;
	}

	public IGameState getStateBlackConfBlackReady() {
		return stateBlackConfBlackReady;
	}

	public IGameState getStateBlackConfRedReady() {
		return stateBlackConfRedReady;
	}

	public IGameState getStateRedConfBlackReady() {
		return stateRedConfBlackReady;
	}

	public IGameState getStateRedConfRedReady() {
		return stateRedConfRedReady;
	}

	public IGameState getStateBlackTurnConflict() {
		return stateBlackTurnConflict;
	}

	public IGameState getStateRedTurnConflict() {
		return stateRedTurnConflict;
	}

	public void setState(IGameState state) {
		this.state = state;
	}

	public IGameState getStateIdle() {
		return stateIdle;
	}

	public IGameState getStateNewCreate() {
		return stateNewCreate;
	}

	public IGameState getStateRedTurn() {
		return stateRedTurn;
	}

	public IGameState getStateBlackTurn() {
		return stateBlackTurn;
	}

	public IGameState getStateBlocked() {
		return stateBlocked;
	}

	public IGameState getStateFinished() {
		return stateFinished;
	}

	public IPlayer getBlack() {
		return black;
	}

	public IGameState getStateRedReady() {
		return stateRedReady;
	}

	public IGameState getStateBlackReady() {
		return stateBlackReady;
	}

	public void setBlack(IPlayer black) {
		this.black = black;
	}

	public IPlayer getRed() {
		return red;
	}

	public void setRed(IPlayer red) {
		this.red = red;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public synchronized boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		state.newGame(red, black);
		return true;
	}

	public void noticeConflict(ChessPiece r, ChessPiece b) {
		if (!r.isOpen()) board.setChessPiece(r.open());
		if (!b.isOpen()) board.setChessPiece(b.open());

		notifyBoardUpdate();

		setRedConfPiece(r);
		setBlackConfPiece(b);
		try {
			red.metConflict();
			black.metConflict();
		} catch (IllegalPlayerStateException e) {
			Log.e("Game", "Wrong player state...", e);
		}
	}

	public void notifyBoardUpdate() {
		red.boardUpdated();
		black.boardUpdated();
	}
	
	/**
	 * When two pieces are the same type, the game come to Conflict state, each player
	 * should invoke this function to choose a new gesture.
	 * 
	 * @param p
	 * @throws IllegalGameStateException
	 */
	public synchronized void makeChoice(ChessPiece p) throws IllegalGameStateException {
		state.makeChoice(p);
	}

	public synchronized void move(ChessPiece start, ChessPiece dest) throws IllegalGameStateException {
		if (start == null || dest == null) {
			throw new IllegalGameStateException();
		}
		
		if (dest.isUnknow()) {
			dest = board.getChessPiece(dest.getRow(), dest.getColumn());
		}

		if (Config.DEBUG) {
			Log.d("Game", "Moving from '" + start.toString() + "' to '" + dest.toString() + "'");
		}
		state.move(start, dest);
		getRed().boardUpdated();
		getBlack().boardUpdated();

		if (board.getBlack_count() == 2) {
			state.gameOver(getRed());
		} else if (board.getRed_count() == 2) {
			state.gameOver(getBlack());
		}
	}

	public boolean isRedTurn() {
		return state instanceof StateRedTurn;
	}

	public boolean isBlackTurn() {
		return state instanceof StateBlackTurn;
	}

	/**
	 * 
	 * 
	 * @param f
	 * @param t
	 * @throws IllegalGameStateException
	 */
	public synchronized void placeFlagAndTrap(ChessPiece f, ChessPiece t) throws IllegalGameStateException {
		state.placeTrapAndFlag(f, t);
	}

	public synchronized void concede(IPlayer loser) throws IllegalGameStateException {
		state.concede(loser);
	}

	public synchronized void quitGame(IPlayer badGuy) throws IllegalGameStateException {
		state.quitGame(badGuy);
	}

	public Board getBoard(IPlayer p) {
		if (p == red) {
			return board.cloneBoard(IPlayer.RED);
		}

		if (p == black) {
			return board.cloneBoard(IPlayer.BLACK);
		}

		return null;
	}
}
