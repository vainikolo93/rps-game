/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateNewCreate implements IGameState {

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		throw new IllegalGameStateException("Game created..");
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		if (redTrap.getType() != ChessPiece.RED_TRAP ||
				redFlag.getType() != ChessPiece.RED_FLAG ||
				blackTrap.getType() != ChessPiece.BLACK_TRAP ||
				blackFlag.getType() != ChessPiece.BLACK_FLAG) {
			throw new IllegalArgumentException();
		}
		return true;
	}
}
