/**
 * 
 */
package com.kahweh.rps.game;

import java.util.Random;

import com.kahweh.rps.game.player.IPlayer;

/**
 * @author b448
 *
 */
public abstract class AbstractBoard implements IBoard {
	
	protected int gridWidth;
	protected int gridHeight;
	protected int boardMargin;
	protected int boardHeight;
	protected int boardWidth;
	protected int boardAbsHeight;
	protected int boardAbsWidth;
	protected int pieceNumberPerType;

	protected int[][] board;

	protected Random rand = new Random(System.currentTimeMillis());
	
	protected int black_count;
	protected int red_count;
	
	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cleanBoard()
	 */
	public void cleanBoard() {
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (i == 0 || i == 1) {
					board[i][j] = ChessPiece.BLACK_UNKNOW;
				} else if (i == boardHeight - 1 || i == boardHeight -2) {
					board[i][j] = ChessPiece.RED_UNKNOW;
				} else {
					board[i][j] = ChessPiece.BLANK;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#cloneBoard(int)
	 */
	@Override
	public IBoard cloneBoard(int color) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBlack_count()
	 */
	@Override
	public int getBlack_count() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoard()
	 */
	@Override
	public int[][] getBoard() {
		// TODO Auto-generated method stub
		return this.board;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardAbsHeight()
	 */
	@Override
	public int getBoardAbsHeight() {
		return this.boardAbsHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardAbsWidth()
	 */
	@Override
	public int getBoardAbsWidth() {
		return this.boardAbsWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardHeight()
	 */
	@Override
	public int getBoardHeight() {
		return this.boardHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardMargin()
	 */
	@Override
	public int getBoardMargin() {
		return this.boardMargin;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getBoardWidth()
	 */
	@Override
	public int getBoardWidth() {
		return this.boardWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getChessPiece(int, int)
	 */
	@Override
	public ChessPiece getChessPiece(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getGridHeight()
	 */
	@Override
	public int getGridHeight() {
		return this.gridHeight;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getGridWidth()
	 */
	@Override
	public int getGridWidth() {
		return this.gridWidth;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getNeighborChessPiece(com.kahweh.rps.game.ChessPiece, int)
	 */
	@Override
	public ChessPiece getNeighborChessPiece(ChessPiece p, int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#getRed_count()
	 */
	@Override
	public int getRed_count() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#move(com.kahweh.rps.game.ChessPiece, com.kahweh.rps.game.ChessPiece)
	 */
	@Override
	public void move(ChessPiece start, ChessPiece dest) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.kahweh.rps.game.IBoard#openAll()
	 */
	@Override
	public void openAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public ChessPiece translatePosition(int color, float x, float y) {
		if (x > boardAbsWidth || x < 0) return null;
		if (y > boardAbsHeight || y < 0) return null;

		int ix = (int)x;
		int iy = (int)y;
		ix = (ix - boardMargin) / gridWidth;
		iy = (iy - boardMargin) / gridHeight;
		if (ix >= boardWidth) ix = boardWidth - 1;
		if (iy >= boardHeight) iy = boardHeight - 1;

		if (color == IPlayer.BLACK) {
			ix = boardWidth - ix - 1;
			iy = boardHeight - iy - 1;
		}

		ChessPiece p = new ChessPiece(ChessPiece.BLANK, iy, ix);
		return p;
	}

	@Override
	public boolean verifyFlag(ChessPiece f) {
		if (f.isBlack()) {
			if (f.getType() != ChessPiece.BLACK_FLAG ||
					f.getRow() > 1 || 
					f.getRow() < 0 || 
					f.getColumn() < 0 || 
					f.getColumn() >= getBoardWidth()) {
				return false;
			}
		} else {
			if (f.getType() != ChessPiece.RED_FLAG ||
					f.getRow() >= getBoardHeight() || 
					f.getRow() < getBoardHeight()-2 || 
					f.getColumn() < 0 || 
					f.getColumn() >= getBoardWidth()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean setChessPiece(ChessPiece p) {
		if (p == null) return false;
		if (p.getRow() < 0 || 
				p.getRow() >= getBoardHeight() || 
				p.getColumn() < 0 || 
				p.getColumn() >= getBoardWidth()) {
			return false;
		}
		board[p.getRow()][p.getColumn()] = p.getType();
		return true;
	}

	@Override
	public boolean initBoard() {
		int rock = pieceNumberPerType;
		int paper = pieceNumberPerType;
		int scissors = pieceNumberPerType;

		black_count = red_count = boardWidth * 2;

		//init the red pieces
		for (int i = boardHeight-2; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == ChessPiece.RED_UNKNOW) {
					while (true) {
						int r = rand.nextInt(3);
						if (r == 0) {
							if (rock == 0) continue;
							rock--;
							board[i][j] = ChessPiece.RED_ROCK;
							break;
						}
						if (r == 1) {
							if (paper == 0) continue;
							paper--;
							board[i][j] = ChessPiece.RED_PAPER;
							break;
						}
						if (r == 2) {
							if (scissors == 0) continue;
							scissors--;
							board[i][j] = ChessPiece.RED_SCISSORS;
							break;
						}
					}
				}
			}
		}

		//init the black pieces
		rock = pieceNumberPerType;
		paper = pieceNumberPerType;
		scissors = pieceNumberPerType;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (board[i][j] == ChessPiece.BLACK_UNKNOW) {
					while (true) {
						int r = rand.nextInt(3);
						if (r == 0) {
							if (rock == 0) continue;
							rock--;
							board[i][j] = ChessPiece.BLACK_ROCK;
							break;
						}
						if (r == 1) {
							if (paper == 0) continue;
							paper--;
							board[i][j] = ChessPiece.BLACK_PAPER;
							break;
						}
						if (r == 2) {
							if (scissors == 0) continue;
							scissors--;
							board[i][j] = ChessPiece.BLACK_SCISSORS;
							break;
						}
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public int getPieceNumber() {
		return pieceNumberPerType;
	}
}