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
public class StateColorSet extends AbstractPlayerState {

	public StateColorSet(LocalPlayer localPlayer) {
		this.player = localPlayer;
	}

	@Override
	public void setFlag(ChessPiece p) throws IllegalPlayerStateException {
		player.setState(player.getStateFlagSet());
	}
	
}
