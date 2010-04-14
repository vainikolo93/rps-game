/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateFinished implements IGameState {

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		throw new IllegalGameStateException("Game finished..");
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

}
