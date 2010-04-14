/**
 * 
 */
package com.kahweh.rps.ai;

import android.graphics.Bitmap;

import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;

/**
 * @author Michael
 *
 */
public class Michael implements IPlayer {
	private int color;

	private Game game;

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGameNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLoseNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getWinNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

}
