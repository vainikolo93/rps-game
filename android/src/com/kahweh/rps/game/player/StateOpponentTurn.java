/**
 * 
 */
package com.kahweh.rps.game.player;

/**
 * @author Michael
 *
 */
public class StateOpponentTurn extends AbstractPlayerState {

	public StateOpponentTurn(LocalPlayer local) {
		this.player = local;
	}

	@Override
	public void play() throws  IllegalPlayerStateException {
		player.setState(player.getStateMyTurn());
	}
}
