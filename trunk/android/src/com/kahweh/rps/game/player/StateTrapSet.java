/**
 * 
 */
package com.kahweh.rps.game.player;

/**
 * @author Michael
 *
 */
public class StateTrapSet extends AbstractPlayerState {
	
	public StateTrapSet(LocalPlayer localPlayer) {
		this.player = localPlayer;
	}

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		if (player.isBlack()) {
			player.setState(player.getStateOpponentTurn());
		} else {
			player.setState(player.getStateMyTurn());
		}
	}
}
