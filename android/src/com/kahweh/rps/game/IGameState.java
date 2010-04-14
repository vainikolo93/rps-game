/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public interface IGameState {
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException;
	
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag, 
			ChessPiece blackTrap, ChessPiece blackFlag) throws IllegalGameStateException;
}
