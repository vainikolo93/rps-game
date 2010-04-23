/**
 * 
 */
package com.kahweh.rps.game.state;

import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;

/**
 * @author Michael
 *
 */
public class StateFinished extends AbstractState {
	
	public StateFinished(Game game) {
		this.game = game;
	}

	@Override
	public boolean newGame(IPlayer red, IPlayer black) {
		red.setColor(IPlayer.RED);
		black.setColor(IPlayer.BLACK);
		red.setGame(game);
		black.setGame(game);

		game.setRed(red);
		game.setBlack(black);
		game.setState(game.getStateNewCreate());
		red.chooseFlagAndTrap();
		black.chooseFlagAndTrap();
		return true;
	}

}
