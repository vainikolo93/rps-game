/**
 * 
 */
package com.kahweh.rps.game.player;

import android.util.Log;

import com.kahweh.rps.Config;
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
		player.setState(player.getStateOpponentTurn());
		if (Config.DEBUG) {
			Log.d("StateMyTurn", "convert to StateOpponentTurn");
		}

		try {
			player.getGame().move(start, dest);
		} catch (IllegalGameStateException e) {
			throw new IllegalPlayerStateException(e);
		}

	}

	@Override
	public void play() throws  IllegalPlayerStateException {}
	
	@Override
	public void timeOut() throws IllegalPlayerStateException {
		//TODO
	}

	@Override
	public void giveUp() throws IllegalPlayerStateException {
		//TODO
	}
}
