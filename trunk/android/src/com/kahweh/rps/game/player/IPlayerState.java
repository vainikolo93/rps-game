/**
 * 
 */
package com.kahweh.rps.game.player;

import com.kahweh.rps.game.ChessPiece;

/**
 * @author Michael
 *
 */
public interface IPlayerState {

	public void setFlag(ChessPiece p) throws IllegalPlayerStateException;

	public void setTrap(ChessPiece p) throws IllegalPlayerStateException;

	public void move(ChessPiece start, ChessPiece dest)
			throws IllegalPlayerStateException;

	public void timeOut() throws IllegalPlayerStateException;

	public void giveUp() throws IllegalPlayerStateException;

	public void colorSet() throws IllegalPlayerStateException;

	public void queryColor() throws IllegalPlayerStateException;

	public void boardInitialized() throws IllegalPlayerStateException;
	
	public void play() throws  IllegalPlayerStateException;

	public void metConflict() throws  IllegalPlayerStateException;
}
