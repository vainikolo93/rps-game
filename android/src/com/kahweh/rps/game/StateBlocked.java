/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateBlocked implements IGameState {

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		throw new IllegalGameStateException("Now game blocked..");
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

}
