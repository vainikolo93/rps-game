/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

import android.graphics.Bitmap;

/**
 * @author Michael
 *
 */
public interface IPlayer {
	public static final int IDLE = -1;
	public static final int BLACK = 1;
	public static final int RED = 0;
	
	public String getName();
	public Bitmap getIcon();
	public String getEmail();

	public long getGameNumbers();
	public long getWinNumbers();
	public long getLoseNumbers();

	public void setColor(int color) throws IllegalPlayerStateException;

	public void setGame(Game game);
	
	public Game getGame();
	
	public void metConflict() throws IllegalPlayerStateException;

	public void chooseFlagAndTrap() throws IllegalPlayerStateException;
	
	public void play();

	public void notifyQuit();
	
	public int getColor();
	
	public boolean setFlag(ChessPiece p) throws IllegalPlayerStateException, IllegalGameStateException;

	public boolean setTrap(ChessPiece p) throws IllegalPlayerStateException, IllegalGameStateException;
	
	public boolean isBlack();
	
	public boolean isRed();
	
	public void boardUpdated();
	
	public void boardInitialized() throws IllegalPlayerStateException;
	
	public void notifyFinish(IPlayer winner) throws IllegalPlayerStateException;
}
