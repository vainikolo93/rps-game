/**
 * 
 */
package com.kahweh.rps;

import android.graphics.Bitmap;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;

/**
 * @author Michael
 *
 */
public class LocalPlayer implements IPlayer {

	private int color;
	private Game game;
	private RockPaperScissors rps;
	
	public LocalPlayer(RockPaperScissors rps) {
		this.rps = rps;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getGameNumbers()
	 */
	@Override
	public long getGameNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getIcon()
	 */
	@Override
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getLoseNumbers()
	 */
	@Override
	public long getLoseNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getWinNumbers()
	 */
	@Override
	public long getWinNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#setColor(int)
	 */
	@Override
	public void setColor(int color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#setGame(com.kahweh.rps.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void metConflict() {
		// TODO Auto-generated method stub
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
	}

	@Override
	public void chooseFlagAndTrap() {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyQuit() {
		// TODO Auto-generated method stub
		
	}

}
