/**
 * 
 */
package com.kahweh.rps.game.player;

import android.util.Log;

import com.kahweh.rps.Config;
import com.kahweh.rps.HomeActivity;

/**
 * @author b448
 *
 */
public class StateConflict extends AbstractPlayerState {

	public StateConflict(LocalPlayer local) {
		this.player = local;
	}

	@Override
	public void play() throws  IllegalPlayerStateException {
		player.setState(player.getStateMyTurn());

		if (Config.DEBUG) {
			Log.d("StateConflict", "convert to StateMyTurn");
		}
	}

	@Override
	public void metConflict() throws IllegalPlayerStateException {
		if (Config.DEBUG) {
			Log.d("StateConflict", "still is StateConflict..");
		}
		player.getRps().showConfSelectDialog();
	}
}
