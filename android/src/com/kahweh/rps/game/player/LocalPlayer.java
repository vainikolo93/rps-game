/**
 * 
 */
package com.kahweh.rps.game.player;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.kahweh.rps.Config;
import com.kahweh.rps.GameActivity;
import com.kahweh.rps.R;
import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;

/**
 * @author Michael
 * 
 */
public class LocalPlayer implements IPlayer {
	private static String TAG = "com.kahweh.rps.game.player.LocalPlayer";
	
	private int color;
	private Game game;
	private GameActivity rps;

	private ChessPiece flag;

	private IPlayerState stateNewCreate;
	private IPlayerState stateColorSet;
	private IPlayerState stateFlagSet;
	private IPlayerState stateTrapSet;
	private IPlayerState stateMyTurn;
	private IPlayerState stateOpponentTurn;
	private IPlayerState stateGameOver;
	private IPlayerState stateConflict;

	private ChessPiece activePiece;

	public IPlayerState getStateConflict() {
		return stateConflict;
	}

	private IPlayerState state;

	public LocalPlayer(GameActivity rps) {
		this.setRps(rps);

		this.stateNewCreate = new StateNewCreate(this);
		this.stateColorSet = new StateColorSet(this);
		this.stateFlagSet = new StateFlagSet(this);
		this.stateTrapSet = new StateTrapSet(this);
		this.stateMyTurn = new StateMyTurn(this);
		this.stateOpponentTurn = new StateOpponentTurn(this);
		this.stateGameOver = new StateGameOver(this);
		this.stateConflict = new StateConflict(this);

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

	public ChessPiece getActivePiece() {
		return activePiece;
	}

	public void setActivePiece(ChessPiece activePiece) {
		this.activePiece = activePiece;
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
	public void metConflict() throws IllegalPlayerStateException {
		// TODO Auto-generated method stub
		if (Config.DEBUG) {
			Log.d(TAG, "Met confilct...");
		}
		state.metConflict();
	}

	@Override
	public void play() {
		try {
			state.play();
		} catch (IllegalPlayerStateException e) {
			Log.e(TAG, "Wrong..", e);
		}
	}

	@Override
	public void chooseFlagAndTrap() throws IllegalPlayerStateException {
		rps.showDialog(GameActivity.DIALOG_FLAG_SELECT);
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
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	@Override
	public boolean setFlag(ChessPiece p) throws IllegalPlayerStateException, IllegalGameStateException {
		if (color == IPlayer.RED) {
			p.setType(ChessPiece.RED_FLAG);
		} else {
			p.setType(ChessPiece.BLACK_FLAG);
		}
		if (!game.getBoard().verifyFlag(p)) {
			rps.showDialog(GameActivity.DIALOG_FLAG_SELECT);
			return false;
		}

		flag = p;
		game.getBoard().setChessPiece(p);
		rps.getBoardView().invalidate();
		state.setFlag(p);

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

		if ((p.getRow() == flag.getRow() && p.getColumn() == flag.getColumn())
				|| !game.getBoard().verifyTrap(p)) {
			rps.showDialog(GameActivity.DIALOG_TRAP_SELECT);
			return false;
		}

		state.setTrap(p);
		game.placeFlagAndTrap(flag, p);
		return true;
	}

	public void setRps(GameActivity rps) {
		this.rps = rps;
	}

	public GameActivity getRps() {
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
			activePiece = null;
			rps.getBoardView().invalidate();
		} catch (IllegalPlayerStateException e) {
			Toast.makeText(rps, "Wrong Player State...", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * The Game obj use this function to set the localplayer's choice of gesture.
	 * the values of pieceType: 
	 *    0 for Rock
	 *    1 for Paper
	 *    2 for Scissors
	 * 
	 * @param pieceType
	 */
	public void setDrawChoice(int pieceType) {
		switch (pieceType) {
		case 0:
			//Rock
			if (isRed()) {
				game.getRedConfPiece().setType(ChessPiece.RED_ROCK);
			} else {
				game.getBlackConfPiece().setType(ChessPiece.BLACK_ROCK);
			}
			break;
		case 1:
			//Paper
			if (isRed()) {
				game.getRedConfPiece().setType(ChessPiece.RED_PAPER);
			} else {
				game.getBlackConfPiece().setType(ChessPiece.BLACK_PAPER);
			}
			break;
		case 2:
			//Scissors
			if (isRed()) {
				game.getRedConfPiece().setType(ChessPiece.RED_SCISSORS);
			} else {
				game.getBlackConfPiece().setType(ChessPiece.BLACK_SCISSORS);
			}
			break;
		}
	}

	public void drawChoiceMade() throws IllegalGameStateException {
		if (isRed()) {
			game.makeChoice(game.getRedConfPiece().open());
		} else {
			game.makeChoice(game.getBlackConfPiece().open());
		}
	}

	@Override
	public void notifyFinish(IPlayer winner) throws IllegalPlayerStateException {
		state.finish(winner);
		rps.showFinishDialog(this == winner?true:false);
	}

	/**
	 * This function is used to handle the user click on the BoardView.
	 * 
	 * @param x
	 * @param y
	 */
	public void rawClick(float x, float y) {
		ChessPiece p = rps.getGame().getBoard().translatePosition(color, x, y);

		if (Config.DEBUG) {
			Log.d(TAG, "User touched: " + p.toString());
		}

		if (state instanceof StateColorSet) {
			//Set Flag
			try {
				setFlag(p);
			} catch (IllegalPlayerStateException e) {
				Log.w(TAG, "Wrong player state..", e);
			} catch (IllegalGameStateException e) {
				Log.w(TAG, "Wrong game state..", e);
			}
		} else if (state instanceof StateFlagSet) {
			//Set Trap
			try {
				setTrap(p);
			} catch (IllegalPlayerStateException e) {
				Log.w(TAG, "Wrong player state..", e);
			} catch (IllegalGameStateException e) {
				Log.w(TAG, "Wrong game state..", e);
			}
		} else if (state instanceof StateMyTurn) {
			//Move chesspiece
			p = game.getBoard().getChessPiece(p.getRow(), p.getColumn());
			if (activePiece == null) {
				//If activePiece is not not set, then try to set
				if (isBlack()) {
					if (p.isBlack() && p.isMovable()) {
						activePiece = p;
						rps.getBoardView().invalidate();
					} else {
						Toast.makeText(rps, rps.getString(R.string.toast_prompt_choose_black_piece), Toast.LENGTH_SHORT).show();
					}
				} else {
					if (p.isRed() && p.isMovable()) {
						activePiece = p;
						rps.getBoardView().invalidate();
					} else {
						Toast.makeText(rps, rps.getString(R.string.toast_prompt_choose_red_piece), Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				//ActivePiece is set then do MOVE
				if (isBlack()) {
					if (p.isBlack()) {
						if (p.isMovable()) {
							activePiece = p;
							rps.getBoardView().invalidate();
						}
					} else {
						if ((Math.abs(p.getRow() - activePiece.getRow()) 
							+ Math.abs(p.getColumn() - activePiece.getColumn())) == 1) {
							move(activePiece, p);
						}
					}
				} else {
					//Red player
					if (p.isRed()) {
						if (p.isMovable()) {
							activePiece = p;
							rps.getBoardView().invalidate();
						}
					} else {
						if ((Math.abs(p.getRow() - activePiece.getRow()) 
								+ Math.abs(p.getColumn() - activePiece.getColumn())) == 1) {
								move(activePiece, p);
							}
					}
				}
			}
		}

		rps.getBoardView().invalidate();
//		Toast.makeText(rps, "Test" + p.getRow() + " " + p.getColumn(), Toast.LENGTH_SHORT).show();
	}
}
