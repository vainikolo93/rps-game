/**
 * 
 */
package com.kahweh.rps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.kahweh.rps.game.Board;
import com.kahweh.rps.game.ChessPiece;
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
	
	private Board board;
	private ChessPiece activePiece;
	
	private final Bitmap bbe;
	private final Bitmap bbpc;
	private final Bitmap bbpo;
	private final Bitmap bbrc;
	private final Bitmap bbro;
	private final Bitmap bbsc;
	private final Bitmap bbso;
	private final Bitmap bfe;
	private final Bitmap bfpo;
	private final Bitmap bfro;
	private final Bitmap bfso;
	private final Bitmap bFlag;
	private final Bitmap rbe;
	private final Bitmap rbpc;
	private final Bitmap rbpo;
	private final Bitmap rbrc;
	private final Bitmap rbro;
	private final Bitmap rbsc;
	private final Bitmap rbso;
	private final Bitmap rfe;
	private final Bitmap rfpo;
	private final Bitmap rfro;
	private final Bitmap rfso;
	private final Bitmap rFlag;
	private final Bitmap trap;

	public BoardView(RockPaperScissors context) {
		super(context);
		this.rps = context;

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
		
		setBackgroundResource(R.drawable.board320_480);

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				if (player == null) return false;

				if (MotionEvent.ACTION_DOWN == e.getAction()) {
					ChessPiece p = Board.translatePosition(player.getColor(), e.getX(), e.getY());

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
							//ActivePiece is not null
							if (player.isBlack()) {
								if (p.isBlack()) {
									if (p.isMovable()) {
										activePiece = p;
									}
								} else {
									if ((Math.abs(p.getRow() - activePiece.getRow()) 
										+ Math.abs(p.getColumn() - activePiece.getColumn())) == 1) {
										player.move(activePiece, p);
									}
								}
							} else {
								if (p.isRed()) {
									if (p.isMovable()) {
										activePiece = p;
									}
								} else {
									
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
		byte[][] b = board.getBoard();
		float x, y;
		int ii, jj;
		Paint paint = new Paint();
		for (int i = 0; i < b.length; i++) {
			y = i * 50 + 3;
			for (int j=0; j < b[i].length; j++) {
				x = j * 44 + 3;
				if (player.isBlack()) {
					ii = Board.BOARD_HEIGHT - i - 1;
					jj = Board.BOARD_WIDTH - j - 1;
				} else {
					ii = i;
					jj = j;
				}
				switch (ChessPiece.toClosePiece(b[ii][jj])) {
				case ChessPiece.BLACK_FLAG:
					if (player.isBlack()) {
						canv.drawBitmap(bFlag, x, y, paint);
					} else {
						canv.drawBitmap(bfe, x, y, paint);
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
						canv.drawBitmap(bfe, x, y, paint);
					}
					break;
				case ChessPiece.RED_TRAP:
					if (player.isBlack()) {
						canv.drawBitmap(rfe, x, y, paint);
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
						canv.drawBitmap(rfe, x, y, paint);
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

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

}
