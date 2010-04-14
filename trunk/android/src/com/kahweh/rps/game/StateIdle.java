/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class StateIdle implements IGameState {
	private Game game;
	
	public StateIdle(Game game) {
		this.game = game;
	}

	@Override
	public boolean newGame(IPlayer red, IPlayer black) {
		game.setRed(red);
		game.setBlack(black);
		game.setState(game.getStateNewCreate());
		return true;
	}

	@Override
	public boolean setTrapAndFlag(ChessPiece redTrap, ChessPiece redFlag,
			ChessPiece blackTrap, ChessPiece blackFlag)
			throws IllegalGameStateException {
		throw new IllegalGameStateException();
	}
}
