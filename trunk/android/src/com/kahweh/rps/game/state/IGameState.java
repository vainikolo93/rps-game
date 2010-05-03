/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;

/**
 * @author Michael
 *
 */
public interface IGameState {
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException;
	
	public boolean placeTrapAndFlag(ChessPiece flag, ChessPiece trap) throws IllegalGameStateException;
	
	public boolean move(ChessPiece start, ChessPiece dest) throws IllegalGameStateException;
	
	public boolean makeChoice(ChessPiece p) throws IllegalGameStateException;
	
	public void concede(IPlayer loser) throws IllegalGameStateException;

	public void quitGame(IPlayer badGuy);
}
