/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author Michael
 *
 */
public class IllegalGameStateException extends Exception {
	public IllegalGameStateException() {
		super();
	}
	
	public IllegalGameStateException(String msg) {
		super(msg);
	}
	
	public IllegalGameStateException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public IllegalGameStateException(Throwable cause) {
		super(cause);
	}
}
