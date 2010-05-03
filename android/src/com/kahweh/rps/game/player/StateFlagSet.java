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

	@Override
	public void setTrap(ChessPiece p) throws IllegalPlayerStateException {
		player.setState(player.getStateTrapSet());
	}
}
