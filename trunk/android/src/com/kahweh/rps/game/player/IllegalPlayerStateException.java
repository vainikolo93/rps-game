/**
 * 
 */
package com.kahweh.rps.game.player;

/**
 * @author Michael
 *
 */
public class IllegalPlayerStateException extends Exception {

	public IllegalPlayerStateException() {
		super();
	}

	public IllegalPlayerStateException(String msg) {
		super(msg);
	}
	
	public IllegalPlayerStateException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public IllegalPlayerStateException(Throwable cause) {
		super(cause);
	}
}
