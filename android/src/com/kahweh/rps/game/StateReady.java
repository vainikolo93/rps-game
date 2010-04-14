/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateReady implements IGameState {

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		throw new IllegalGameStateException("Now Ready..");
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}

}
