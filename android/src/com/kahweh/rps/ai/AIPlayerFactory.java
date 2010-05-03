/**
 * 
 */
package com.kahweh.rps.ai;

import com.kahweh.rps.game.player.IPlayer;

/**
 * @author Michael
 *
 */
public class AIPlayerFactory {
	public static IPlayer getAIPlayer() {
		return new Michael();
	}
}
