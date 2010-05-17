/**
 * 
 */
package com.kahweh.rps.game.player;

import android.util.Config;
import android.util.Log;

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
		if (Config.DEBUG) {
			Log.d("StateOpponentTurn", "convert to StateMyTurn");
		}
	}
}
