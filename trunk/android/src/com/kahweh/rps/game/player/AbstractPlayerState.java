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
	
	public void setFlag(ChessPiece p) throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	public void setTrap(ChessPiece p) throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	public void move(ChessPiece start, ChessPiece dest)
	throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	public void timeOut() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	public void giveUp() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	public void colorSet()  throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	public void queryColor() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}

	public void boardInitialized() throws IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	public void play() throws  IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
	
	public void metConflict() throws  IllegalPlayerStateException {
		throw new IllegalPlayerStateException();
	}
}
