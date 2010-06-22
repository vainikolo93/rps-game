/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.HomeActivity;


/**
 * @author Michael
 *
 */
public class StateNewCreate extends AbstractPlayerState {

	public StateNewCreate(LocalPlayer localPlayer) {
		this.player = localPlayer;
	}

	@Override
	public void colorSet()  throws IllegalPlayerStateException {
		player.setState(player.getStateColorSet());
	}
}
