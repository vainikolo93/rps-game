/**
 * 
 */
package com.kahweh.rps.game;

/**
 * @author michael
 *
 */
public class MoveAction {
	private ChessPiece from;
	private ChessPiece to;

	public MoveAction() {}

	public MoveAction(ChessPiece from, ChessPiece to) {
		this.from = from;
		this.to = to;
	}

	public ChessPiece getFrom() {
		return from;
	}

	public void setFrom(ChessPiece from) {
		this.from = from;
	}

	public ChessPiece getTo() {
		return to;
	}

	public void setTo(ChessPiece to) {
		this.to = to;
	}

	public boolean isValid() {
		if (from == null || to == null) return false;

		return true;
	}
}
