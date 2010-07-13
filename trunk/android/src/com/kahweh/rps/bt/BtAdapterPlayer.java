/**
 * 
 */
package com.kahweh.rps.bt;

import android.graphics.Bitmap;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;

/**
 * @author Michael
 *
 */
public class BtAdapterPlayer implements IPlayer {

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boardUpdated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chooseFlagAndTrap() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game getGame() {
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
	public boolean isBlack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void metConflict() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFinish(IPlayer winner) throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColor(int color) throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setFlag(ChessPiece p) throws IllegalPlayerStateException,
			IllegalGameStateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGame(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setTrap(ChessPiece p) throws IllegalPlayerStateException,
			IllegalGameStateException {
		// TODO Auto-generated method stub
		return false;
	}

}
