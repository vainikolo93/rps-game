/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateRedTurn implements IGameState {

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		throw new IllegalGameStateException("Now.. Red Turn..");
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}
}
