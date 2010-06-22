/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.GameActivity;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;

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

		if (player.getGame().getBoardType() == IBoard.BOARD55) {
			try {
				player.getGame().placeFlagAndTrap(p, null);
			} catch (IllegalGameStateException e) {
				e.printStackTrace();
				throw new IllegalPlayerStateException(e);
			}
    	} else {
			player.getRps().showDialog(GameActivity.DIALOG_TRAP_SELECT);
    	}
    }
}
