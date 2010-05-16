/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 *
 */
public class StateMyTurn extends AbstractPlayerState {
	
	public StateMyTurn(LocalPlayer localPlayer) {
		this.player = localPlayer;
	}
	
	@Override
	public void move(ChessPiece start, ChessPiece dest) throws IllegalPlayerStateException {
		try {
			player.getGame().move(start, dest);
		} catch (IllegalGameStateException e) {
			throw new IllegalPlayerStateException(e);
		}

		player.setState(player.getStateOpponentTurn());
	}
	
	@Override
	public void timeOut() throws IllegalPlayerStateException {
		//TODO
	}
	
	@Override
	public void giveUp() throws IllegalPlayerStateException {
		//TODO
	}
}
