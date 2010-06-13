/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.RockPaperScissors;
import com.kahweh.rps.game.ChessPiece;

/**
 * @author Michael
 *
 */
public class StateFlagSet extends AbstractPlayerState {
	
	public StateFlagSet(LocalPlayer localPlayer) {
		this.player = localPlayer;
	}

	/* 
	 * @see com.kahweh.rps.game.player.AbstractPlayerState#setTrap(com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public void setTrap(ChessPiece p) throws IllegalPlayerStateException {
		//For 6*7 Board..
		player.setState(player.getStateTrapSet());
	}

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		//For 5*5 Board..
		if (player.isBlack()) {
			player.setState(player.getStateOpponentTurn());
		} else {
			player.setState(player.getStateMyTurn());
		}
	}
}
