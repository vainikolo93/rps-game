/**
 * 
 */
package com.kahweh.rps.game.player;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.kahweh.rps.RockPaperScissors;
import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;

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
	private IPlayerState stateGameOver;
	
	private IPlayerState state;
	
	public LocalPlayer(RockPaperScissors rps) {
		this.setRps(rps);
		
		this.stateNewCreate = new StateNewCreate(this);
		this.stateColorSet = new StateColorSet(this);
		this.stateFlagSet = new StateFlagSet(this);
		this.stateTrapSet = new StateTrapSet();
		this.stateMyTurn = new StateMyTurn();
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

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getGameNumbers()
	 */
	@Override
	public long getGameNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getIcon()
	 */
	@Override
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getLoseNumbers()
	 */
	@Override
	public long getLoseNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#getWinNumbers()
	 */
	@Override
	public long getWinNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IPlayer#setGame(com.kahweh.rps.game.Game)
	 */
	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void metConflict() {
		// TODO Auto-generated method stub
	}

	@Override
	public void play() {
		Toast.makeText(rps, "Play.....", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void chooseFlagAndTrap() throws IllegalPlayerStateException {
		rps.showDialog(RockPaperScissors.DIALOG_FLAG_SELECT);
	}

	@Override
	public void notifyQuit() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
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
	public boolean setTrap(ChessPiece p) throws IllegalPlayerStateException {
		if (color == IPlayer.RED) {
			p.setType(ChessPiece.RED_TRAP);
		} else {
			p.setType(ChessPiece.BLACK_TRAP);
		}
		if (!Board.verifyFlagAndTrap(flag, p)) {
			rps.showDialog(RockPaperScissors.DIALOG_TRAP_SELECT);
			return false;
		}

		trap = p;
		state.setTrap(p);
		game.getBoard().setChessPiece(p);
		rps.getBoardView().invalidate();
		Toast.makeText(rps, "Flag and Trap OKay", Toast.LENGTH_SHORT).show();
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

}
