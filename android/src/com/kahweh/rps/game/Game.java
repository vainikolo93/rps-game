/**
 * 
 */
package com.kahweh.rps.game;

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
	private IGameState stateReady;
	private IGameState stateRedTurn;
	private IGameState stateBlackTurn;
	private IGameState stateBlocked;
	private IGameState stateFinished;

	private IGameState state;

	public IGameState getState() {
		return state;
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

	public IGameState getStateReady() {
		return stateReady;
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

	public Game() {
		stateNewCreate = new StateNewCreate();
		stateReady = new StateReady();
		stateRedTurn = new StateRedTurn();
		stateBlackTurn = new StateBlackTurn();
		stateBlocked = new StateBlocked();
		stateFinished = new StateFinished();
		stateIdle = new StateIdle(this);
		
		state = stateIdle;
	}

	public IPlayer getBlack() {
		return black;
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
	
	public boolean newGame(IPlayer red, IPlayer black) {
		return true;
	}
}
