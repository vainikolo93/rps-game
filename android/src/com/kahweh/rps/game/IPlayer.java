/**
 * 
 */
package com.kahweh.rps.game;

import android.graphics.Bitmap;

/**
 * @author Michael
 *
 */
public interface IPlayer {
	public static final int IDLE = -1;
	public static final int BLACK = 0;
	public static final int RED = 1;
	
	public String getName();
	public Bitmap getIcon();
	public String getEmail();

	public long getGameNumbers();
	public long getWinNumbers();
	public long getLoseNumbers();

	public void setColor(int color);

	public void setGame(Game game);
	
	public void metConflict();

	public void chooseFlagAndTrap();
	
	public void play();

	public void notifyQuit();
}
