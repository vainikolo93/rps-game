/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

/**
 * @author Michael
 *
 */
public class StateIdle extends AbstractState {
	
	public StateIdle(Game game) {
		this.game = game;
	}

	@Override
	public boolean newGame(IPlayer red, IPlayer black) throws IllegalGameStateException {
		try {
			red.setColor(IPlayer.RED);
			black.setColor(IPlayer.BLACK);
		} catch (IllegalPlayerStateException e) {
			throw new IllegalGameStateException("Wrong player state..", e);
		}
		red.setGame(game);
		black.setGame(game);

		game.setRed(red);
		game.setBlack(black);
		Board b = game.getBoard();
		b.cleanBoard();
		game.setState(game.getStateNewCreate());
		try {
			red.chooseFlagAndTrap();
			black.chooseFlagAndTrap();
		} catch (IllegalPlayerStateException e) {
			throw new IllegalGameStateException("Wrong player state..", e);
		}
		return true;
	}
}
