/**
 * 
 */
package com.kahweh.rps.game.player;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.kahweh.rps.RockPaperScissors;
import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 * 
 */
public class LocalPlayer implements IPlayer {

	private int color;
	private Game game;
	private RockPaperScissors rps;

	private ChessPiece flag, trap;

	private IPlayerState stateNewCreate;
	private IPlayerState stateColorSet;
	private IPlayerState stateFlagSet;
	private IPlayerState stateTrapSet;
	private IPlayerState stateMyTurn;
	private IPlayerState stateOpponentTurn;
	private IPlayerState stateGameOver;

	private IPlayerState state;

	public LocalPlayer(RockPaperScissors rps) {
		this.setRps(rps);

		this.stateNewCreate = new StateNewCreate(this);
		this.stateColorSet = new StateColorSet(this);
		this.stateFlagSet = new StateFlagSet(this);
		this.stateTrapSet = new StateTrapSet(this);
		this.stateMyTurn = new StateMyTurn(this);
		this.stateOpponentTurn = new StateOpponentTurn(this);
		this.stateGameOver = new StateGameOver();

		this.state = this.stateNewCreate;
	}

	public IPlayerState getState() {
		return state;
	}

	public void setState(IPlayerState state) {
		this.state = state;
	}

	public IPlayerState getStateNewCreate() {
		return stateNewCreate;
	}

	public IPlayerState getStateColorSet() {
		return stateColorSet;
	}

	public IPlayerState getStateFlagSet() {
		return stateFlagSet;
	}

	public IPlayerState getStateTrapSet() {
		return stateTrapSet;
	}

	public IPlayerState getStateMyTurn() {
		return stateMyTurn;
	}

	public IPlayerState getStateGameOver() {
		return stateGameOver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getGameNumbers()
	 */
	@Override
	public long getGameNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getIcon()
	 */
	@Override
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getLoseNumbers()
	 */
	@Override
	public long getLoseNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#getWinNumbers()
	 */
	@Override
	public long getWinNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#setGame(com.kahweh.rps.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		return this.game;
	}

	@Override
	public void metConflict() {
		// TODO Auto-generated method stub
		Toast.makeText(rps, "Conflict...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void play() {
		try {
			state.play();
		} catch (IllegalPlayerStateException e) {
			Log.e(LocalPlayer.class.getSimpleName(), "Wrong..", e);
		}
		Toast.makeText(rps, "Your Turn...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void chooseFlagAndTrap() throws IllegalPlayerStateException {
		rps.showDialog(RockPaperScissors.DIALOG_FLAG_SELECT);
	}

	@Override
	public void notifyQuit() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kahweh.rps.game.IPlayer#setColor(int)
	 */
	@Override
	public void setColor(int color) throws IllegalPlayerStateException {
		this.color = color;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	@Override
	public boolean setFlag(ChessPiece p) throws IllegalPlayerStateException {
		if (color == IPlayer.RED) {
			p.setType(ChessPiece.RED_FLAG);
		} else {
			p.setType(ChessPiece.BLACK_FLAG);
		}
		if (!Board.verifyFlag(p)) {
			rps.showDialog(RockPaperScissors.DIALOG_FLAG_SELECT);
			return false;
		}

		flag = p;
		state.setFlag(p);
		game.getBoard().setChessPiece(p);
		rps.getBoardView().invalidate();
		rps.showDialog(RockPaperScissors.DIALOG_TRAP_SELECT);
		return true;
	}

	@Override
	public boolean setTrap(ChessPiece p) throws IllegalPlayerStateException,
			IllegalGameStateException {
		if (color == IPlayer.RED) {
			p.setType(ChessPiece.RED_TRAP);
		} else {
			p.setType(ChessPiece.BLACK_TRAP);
		}
		if (!Board.verifyFlagAndTrap(flag, p)) {
			rps.showDialog(RockPaperScissors.DIALOG_TRAP_SELECT);
			return false;
		}

		state.setTrap(p);
		game.placeFlagAndTrap(flag, p);
		return true;
	}

	public void setRps(RockPaperScissors rps) {
		this.rps = rps;
	}

	public RockPaperScissors getRps() {
		return rps;
	}

	public void colorSet() throws IllegalPlayerStateException {
		state.colorSet();
	}

	@Override
	public boolean isBlack() {
		return color == IPlayer.BLACK;
	}

	@Override
	public boolean isRed() {
		return color == IPlayer.RED;
	}

	@Override
	public void boardUpdated() {
		rps.getBoardView().invalidate();
	}

	@Override
	public void boardInitialized() throws IllegalPlayerStateException {
		state.boardInitialized();
		rps.getBoardView().invalidate();
		Toast.makeText(rps, "Board Initialized..", Toast.LENGTH_SHORT).show();
	}

	public void setStateOpponentTurn(IPlayerState stateOpponentTurn) {
		this.stateOpponentTurn = stateOpponentTurn;
	}

	public IPlayerState getStateOpponentTurn() {
		return stateOpponentTurn;
	}

	public boolean move(ChessPiece start, ChessPiece dest) {
		try {
			state.move(start, dest);
			rps.getBoardView().setActivePiece(null);
			rps.getBoardView().invalidate();
		} catch (IllegalPlayerStateException e) {
			Toast.makeText(rps, "Wrong Player State...", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}

		return true;
	}
};