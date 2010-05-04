/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.game.ChessPiece;


/**
 * @author Michael
 *
 */
public abstract class AbstractPlayerState implements IPlayerState {
	protected LocalPlayer player;
	
	@Override
	public void setFlag(ChessPiece p) throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	@Override
	public void setTrap(ChessPiece p) throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	@Override
	public void move(ChessPiece start, ChessPiece dest)
	throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	@Override
	public void timeOut() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	@Override
	public void giveUp() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	@Override
	public void colorSet()  throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	@Override
	public void queryColor() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
}
