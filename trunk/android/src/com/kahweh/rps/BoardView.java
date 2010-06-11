/**
 * 
 */
package com.kahweh.rps;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.kahweh.rps.game.ChessPiece;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.player.LocalPlayer;
import com.kahweh.rps.game.player.StateColorSet;
import com.kahweh.rps.game.player.StateFlagSet;
import com.kahweh.rps.game.player.StateMyTurn;

/**
 * @author Michael
 *
 */
public class BoardView extends View {
	private RockPaperScissors rps;
	private LocalPlayer player;
	
	private IBoard board;
	public ChessPiece getActivePiece() {
		return activePiece;
	}

	public void setActivePiece(ChessPiece activePiece) {
		this.activePiece = activePiece;
	}

	private ChessPiece activePiece;
	
	private Bitmap bbe;
	private Bitmap bbpc;
	private Bitmap bbpo;
	private Bitmap bbrc;
	private Bitmap bbro;
	private Bitmap bbsc;
	private Bitmap bbso;
	private Bitmap bfe;
	private Bitmap bfpo;
	private Bitmap bfro;
	private Bitmap bfso;
	private Bitmap bFlag;
	private Bitmap rbe;
	private Bitmap rbpc;
	private Bitmap rbpo;
	private Bitmap rbrc;
	private Bitmap rbro;
	private Bitmap rbsc;
	private Bitmap rbso;
	private Bitmap rfe;
	private Bitmap rfpo;
	private Bitmap rfro;
	private Bitmap rfso;
	private Bitmap rFlag;
	private Bitmap trap;
	private Bitmap arrow_up;
	private Bitmap arrow_down;
	private Bitmap arrow_left;
	private Bitmap arrow_right;

	private int boardType;
	
	public BoardView(RockPaperScissors context) {
		super(context);
		this.rps = context;

		arrow_up = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_up);
		arrow_down = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_down);
		arrow_left = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
		arrow_right = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);

//		SharedPreferences sp = rps.getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
//		final String[] boardSizes = getResources().getStringArray(R.array.preference_boardsize_des);
//		final String[] boardSizes_val = getResources().getStringArray(R.array.preference_boardsize_values);
//		setBackgroundResource(sp.getString(GameSettings.BOARD_SIZE, boardSizes_val[0]).equals(boardSizes_val[0])?R.drawable.board5_5_320_480:R.drawable.board320_480);

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				if (player == null) return false;

				if (MotionEvent.ACTION_DOWN == e.getAction()) {
					ChessPiece p = rps.getGame().getBoard().translatePosition(player.getColor(), e.getX(), e.getY());
					if (Config.DEBUG) {
						Log.d("BoardView", p.toString());
					}
					if (player.getState() instanceof StateColorSet) {
						//Set Flag
						try {
							player.setFlag(p);
						} catch (IllegalPlayerStateException e1) {
							Log.w(BoardView.class.getSimpleName(), "Wrong player state..", e1);
						}
					} else if (player.getState() instanceof StateFlagSet) {
						//Set Trap
						try {
							player.setTrap(p);
						} catch (IllegalPlayerStateException e1) {
							Log.w(BoardView.class.getSimpleName(), "Wrong player state..", e1);
						} catch (IllegalGameStateException e1) {
							Log.w(BoardView.class.getSimpleName(), "Wrong game state..", e1);
						}
					} else if (player.getState() instanceof StateMyTurn) {
						//Move chesspiece
						p = board.getChessPiece(p.getRow(), p.getColumn());
						if (activePiece == null) {
							//If activePiece is not not set, then try to set
							if (player.isBlack()) {
								if (p.isBlack() && p.isMovable()) {
									activePiece = p;
									BoardView.this.invalidate();
								} else {
									Toast.makeText(rps, rps.getString(R.string.toast_prompt_choose_black_piece), Toast.LENGTH_SHORT).show();
								}
							} else {
								if (p.isRed() && p.isMovable()) {
									activePiece = p;
									BoardView.this.invalidate();
								} else {
									Toast.makeText(rps, rps.getString(R.string.toast_prompt_choose_red_piece), Toast.LENGTH_SHORT).show();
								}
							}
						} else {
							//ActivePiece is set then do MOVE
							if (player.isBlack()) {
								if (p.isBlack()) {
									if (p.isMovable()) {
										activePiece = p;
										BoardView.this.invalidate();
									}
								} else {
									if ((Math.abs(p.getRow() - activePiece.getRow()) 
										+ Math.abs(p.getColumn() - activePiece.getColumn())) == 1) {
										player.move(activePiece, p);
									}
								}
							} else {
								//Red player
								if (p.isRed()) {
									if (p.isMovable()) {
										activePiece = p;
										BoardView.this.invalidate();
									}
								} else {
									if ((Math.abs(p.getRow() - activePiece.getRow()) 
											+ Math.abs(p.getColumn() - activePiece.getColumn())) == 1) {
											player.move(activePiece, p);
										}
								}
							}
						}
					}

					BoardView.this.invalidate();
//					Toast.makeText(rps, "Test" + p.getRow() + " " + p.getColumn(), Toast.LENGTH_SHORT).show();
					return true;
				}
				
				return false;
			}
		});
	}

	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
	}

	@Override
	protected void onDraw(Canvas canv) {
		if (board == null) return;
		int[][] b = board.getBoard();
		float x, y;
		int ii, jj;
		Paint paint = new Paint();
		for (int i = 0; i < b.length; i++) {
			y = i * board.getGridHeight() + board.getBoardMargin();
			for (int j=0; j < b[i].length; j++) {
				x = j * board.getGridWidth() + board.getBoardMargin();
				if (player.isBlack()) {
					ii = board.getBoardHeight() - i - 1;
					jj = board.getBoardWidth() - j - 1;
				} else {
					ii = i;
					jj = j;
				}
				switch (ChessPiece.toClosePiece(b[ii][jj])) {
				case ChessPiece.BLACK_FLAG:
					if (player.isBlack()) {
						canv.drawBitmap(bFlag, x, y, paint);
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bFlag, x, y, paint);
						} else {
							canv.drawBitmap(bfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.BLACK_PAPER:
					if (player.isBlack()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bbpo, x, y, paint);
						} else {
							canv.drawBitmap(bbpc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bfpo, x, y, paint);
						} else {
							canv.drawBitmap(bfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.BLACK_ROCK:
					if (player.isBlack()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bbro, x, y, paint);
						} else {
							canv.drawBitmap(bbrc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bfro, x, y, paint);
						} else {
							canv.drawBitmap(bfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.BLACK_SCISSORS:
					if (player.isBlack()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bbso, x, y, paint);
						} else {
							canv.drawBitmap(bbsc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(bfso, x, y, paint);
						} else {
							canv.drawBitmap(bfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.BLACK_TRAP:
					if (player.isBlack()) {
						canv.drawBitmap(trap, x, y, paint);
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(trap, x, y, paint);
						} else {
							canv.drawBitmap(bfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.RED_TRAP:
					if (player.isBlack()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(trap, x, y, paint);
						} else {
							canv.drawBitmap(rfe, x, y, paint);
						}
					} else {
						canv.drawBitmap(trap, x, y, paint);
					}
					break;
				case ChessPiece.BLACK_UNKNOW:
					if (player.isBlack()) {
						canv.drawBitmap(bbe, x, y, paint);
					} else {
						canv.drawBitmap(bfe, x, y, paint);
					}
					break;
				case ChessPiece.RED_FLAG:
					if (player.isBlack()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rFlag, x, y, paint);
						} else {
							canv.drawBitmap(rfe, x, y, paint);
						}
					} else {
						canv.drawBitmap(rFlag, x, y, paint);
					}
					break;
				case ChessPiece.RED_PAPER:
					if (player.isRed()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rbpo, x, y, paint);
						} else {
							canv.drawBitmap(rbpc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rfpo, x, y, paint);
						} else {
							canv.drawBitmap(rfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.RED_ROCK:
					if (player.isRed()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rbro, x, y, paint);
						} else {
							canv.drawBitmap(rbrc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rfro, x, y, paint);
						} else {
							canv.drawBitmap(rfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.RED_SCISSORS:
					if (player.isRed()) {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rbso, x, y, paint);
						} else {
							canv.drawBitmap(rbsc, x, y, paint);
						}
					} else {
						if (ChessPiece.isOpen(b[ii][jj])) {
							canv.drawBitmap(rfso, x, y, paint);
						} else {
							canv.drawBitmap(rfe, x, y, paint);
						}
					}
					break;
				case ChessPiece.RED_UNKNOW:
					if (player.isBlack()) {
						canv.drawBitmap(rfe, x, y, paint);
					} else {
						canv.drawBitmap(rbe, x, y, paint);
					}
					break;
				case ChessPiece.BLANK:
					break;
				}
				if (activePiece != null && 
						(player.isBlack() && !ChessPiece.isBlack(b[ii][jj]) 
						 || player.isRed() && !ChessPiece.isRed(b[ii][jj]))) {
					// Print the arrow prompt
					int row = player.isBlack()?(board.getBoardHeight() - activePiece.getRow() - 1)
							:activePiece.getRow();
					int column = player.isBlack()?(board.getBoardWidth() - activePiece.getColumn() - 1)
							:activePiece.getColumn();

					if (Math.abs(row - i)
						+ Math.abs(column - j) == 1) {
						if (player.isBlack()) {
							if (row < i) {
								canv.drawBitmap(arrow_down, x+8, y+8, paint);
							} else if (row > i) {
								canv.drawBitmap(arrow_up, x+8, y+8, paint);
							} else if (column < j) {
								canv.drawBitmap(arrow_right, x+5, y+12, paint);
							} else if (column > j) {
								canv.drawBitmap(arrow_left, x+5, y+8, paint);
							}
						} else {
							//Red Player
							if (row < i) {
								canv.drawBitmap(arrow_down, x+8, y+8, paint);
							} else if (row > i) {
								canv.drawBitmap(arrow_up, x+8, y+8, paint);
							} else if (column < j) {
								canv.drawBitmap(arrow_right, x+5, y+8, paint);
							} else if (column > j) {
								canv.drawBitmap(arrow_left, x+5, y+12, paint);
							}
						}
					}
				}
			}
		}
	}

	public LocalPlayer getPlayer() {
		return player;
	}

	public void setPlayer(LocalPlayer player) {
		this.player = player;
	}

	public void setBoard(IBoard board) {
		this.board = board;
	}

	public IBoard getBoard() {
		return board;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;

		if (boardType == IBoard.BOARD55) {
			setBackgroundResource(R.drawable.board5_5_320_480);
			bbe = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_e_l);
			bbpc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_p_c_l);
			bbpo = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_p_o_l);
			bbrc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_r_c_l);
			bbro = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_r_o_l);
			bbsc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_s_c_l);
			bbso = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_s_o_l);
			bfe = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_e_l);
			bfpo = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_p_o_l);
			bfro = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_r_o_l);
			bfso = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_s_o_l);
			bFlag = BitmapFactory.decodeResource(getResources(), R.drawable.b_flag_l);
			rbe = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_e_l);
			rbpc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_p_c_l);
			rbpo = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_p_o_l);
			rbrc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_r_c_l);
			rbro = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_r_o_l);
			rbsc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_s_c_l);
			rbso = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_s_o_l);
			rfe = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_e_l);
			rfpo = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_p_o_l);
			rfro = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_r_o_l);
			rfso = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_s_o_l);
			rFlag = BitmapFactory.decodeResource(getResources(), R.drawable.r_flag_l);
			trap = BitmapFactory.decodeResource(getResources(), R.drawable.trap_l);
		} else {
			//This game has two type of board, if is not 5*5 then must be 6*7
			setBackgroundResource(R.drawable.board320_480);
			bbe = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_e);
			bbpc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_p_c);
			bbpo = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_p_o);
			bbrc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_r_c);
			bbro = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_r_o);
			bbsc = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_s_c);
			bbso = BitmapFactory.decodeResource(getResources(), R.drawable.b_b_s_o);
			bfe = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_e);
			bfpo = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_p_o);
			bfro = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_r_o);
			bfso = BitmapFactory.decodeResource(getResources(), R.drawable.b_f_s_o);
			bFlag = BitmapFactory.decodeResource(getResources(), R.drawable.b_flag);
			rbe = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_e);
			rbpc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_p_c);
			rbpo = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_p_o);
			rbrc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_r_c);
			rbro = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_r_o);
			rbsc = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_s_c);
			rbso = BitmapFactory.decodeResource(getResources(), R.drawable.r_b_s_o);
			rfe = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_e);
			rfpo = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_p_o);
			rfro = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_r_o);
			rfso = BitmapFactory.decodeResource(getResources(), R.drawable.r_f_s_o);
			rFlag = BitmapFactory.decodeResource(getResources(), R.drawable.r_flag);
			trap = BitmapFactory.decodeResource(getResources(), R.drawable.trap);
		}
	}

	
}
