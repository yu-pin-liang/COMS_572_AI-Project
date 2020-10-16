package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * An object of this class holds data about a game of checkers. It knows what
 * kind of piece is on each square of the checkerboard. Note that RED moves "up"
 * the board (i.e. row number decreases) while BLACK moves "down" the board
 * (i.e. row number increases). Methods are provided to return lists of
 * available legal moves.
 */
public class CheckersData {

	/*
	 * The following constants represent the possible contents of a square on the
	 * board. The constants RED and BLACK also represent players in the game.
	 */

	static final int EMPTY = 0, RED = 1, RED_KING = 2, BLACK = 3, BLACK_KING = 4;

	int[][] board; // board[r][c] is the contents of row r, column c.

	/**
	 * Constructor. Create the board and set it up for a new game.
	 */
	CheckersData() {
		board = new int[8][8];
		setUpGame();
	}

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	
	//public int[][] state (){
	//	return board;
	//}
	
	
	public CheckersData(CheckersData old) {
		this.board = old.board;
		
	}
	
	public int howmany (int player) {
		int howmany  = 0;
		if (player == 1) {
			for (int i =0;i<board.length;i++) {
				for (int j=0; j<board[1].length;j++) {
					if (board[i][j]==1) {
						howmany ++;
					}
				}
					
			}
		}
		
		if (player == 3) {
			for (int i =0;i<board.length;i++) {
				for (int j=0; j<board[1].length;j++) {
					if (board[i][j]==1) {
						howmany ++;
					}
				}
					
			}
		}
		return howmany;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < board.length; i++) {
			int[] row = board[i];
			sb.append(8 - i).append(" ");
			for (int n : row) {
				if (n == 0) {
					sb.append(" ");
				} else if (n == 1) {
					sb.append(ANSI_RED + "R" + ANSI_RESET);
				} else if (n == 2) {
					sb.append(ANSI_RED + "K" + ANSI_RESET);
				} else if (n == 3) {
					sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
				} else if (n == 4) {
					sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
				}
				sb.append(" ");
			}
			sb.append(System.lineSeparator());
		}
		sb.append("  a b c d e f g h");

		return sb.toString();
	}

	/**
	 * Set up the board with checkers in position for the beginning of a game. Note
	 * that checkers can only be found in squares that satisfy row % 2 == col % 2.
	 * At the start of the game, all such squares in the first three rows contain
	 * black squares and all such squares in the last three rows contain red
	 * squares.
	 */
	void setUpGame() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {

				if ((i == 0 || i == 2) && (j % 2 != 0)) {
					board[i][j] = 3;
				}

				else if ((i == 1) && (j % 2 == 0)) {
					board[i][j] = 3;
				}

				else if (i == 3 || i == 4) {
					board[i][j] = 0;

				}

				else if ((i == 5 || i == 7) && j % 2 == 0) {
					board[i][j] = 1;
				}

				else if (i == 6 && j % 2 != 0) {
					board[i][j] = 1;
				}

			}
		}
	}

	/**
	 * Return the contents of the square in the specified row and column.
	 */
	int pieceAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Make the specified move. It is assumed that move is non-null and that the
	 * move it represents is legal.
	 * 
	 * @return true if the piece becomes a king, otherwise false
	 */
	boolean makeMove(CheckersMove move) {
		return makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
	}

	/**
	 * Make the move from (fromRow,fromCol) to (toRow,toCol). It is assumed that
	 * this move is legal. If the move is a jump, the jumped piece is removed from
	 * the board. If a piece moves to the last row on the opponent's side of the
	 * board, the piece becomes a king.
	 *
	 * @param fromRow row index of the from square
	 * @param fromCol column index of the from square
	 * @param toRow   row index of the to square
	 * @param toCol   column index of the to square
	 * @return true if the piece becomes a king, otherwise false
	 */
	
	boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
		// Todo: update the board for the given move.
		// You need to take care of the following situations:
		// 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
		// 2. if this move is a jump, remove the captured piece
		// 3. if the piece moves into the kings row on the opponent's side of the board,
		// crowned it as a king
		CheckersMove jumpable = new CheckersMove(fromRow, fromCol, toRow, toCol);
		boolean king = false;
		if (jumpable.isJump()) {
			board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = 0;

			int from = board[fromRow][fromCol];
			board[fromRow][fromCol] = 0;
			board[toRow][toCol] = from;

			if (toRow == 0) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 2;
				king = true;
			}

			else if (toRow == 7) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 4;
				king = true;
			}
		}

		else {

			int from = board[fromRow][fromCol];
			board[fromRow][fromCol] = 0;
			board[toRow][toCol] = from;

			if (toRow == 0) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 2;
				king = true;
			}

			else if (toRow == 7) {
				board[fromRow][fromCol] = 0;
				board[toRow][toCol] = 4;
				king = true;
			}

		}

		return king;
	}

	/**
	 * Return an array containing all the legal CheckersMoves for the specified
	 * player on the current board. If the player has no legal moves, null is
	 * returned. The value of player should be one of the constants RED or BLACK; if
	 * not, null is returned. If the returned value is non-null, it consists
	 * entirely of jump moves or entirely of regular moves, since if the player can
	 * jump, only jumps are legal moves.
	 *
	 * @param player color of the player, RED or BLACK
	 */
	CheckersMove[] getLegalMoves(int player) {
        // Return an array containing all the legal CheckersMoves
        // for the specfied player on the current board.  If the player
        // has no legal moves, null is returned.  The value of player
        // should be one of the constants RED or BLACK; if not, null
        // is returned.  If the returned value is non-null, it consists
        // entirely of jump moves or entirely of regular moves, since
        // if the player can jump, only jumps are legal moves.

    if (player != RED && player != BLACK)
       return null;

    int playerKing;  // The constant representing a King belonging to player.
    if (player == RED)
       playerKing = RED_KING;
    else
       playerKing = BLACK_KING;

    Vector moves = new Vector();  // Moves will be stored in this vector.
    
    /*  First, check for any possible jumps.  Look at each square on the board.
        If that square contains one of the player's pieces, look at a possible
        jump in each of the four directions from that square.  If there is 
        a legal jump in that direction, put it in the moves vector.
    */

    for (int row = 0; row < 8; row++) {
       for (int col = 0; col < 8; col++) {
          if (board[row][col] == player || board[row][col] == playerKing) {
             if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.addElement(new CheckersMove(row, col, row+2, col+2));
             if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.addElement(new CheckersMove(row, col, row-2, col+2));
             if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.addElement(new CheckersMove(row, col, row+2, col-2));
             if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.addElement(new CheckersMove(row, col, row-2, col-2));
          }
       }
    }
    
    /*  If any jump moves were found, then the user must jump, so we don't 
        add any regular moves.  However, if no jumps were found, check for
        any legal regualar moves.  Look at each square on the board.
        If that square contains one of the player's pieces, look at a possible
        move in each of the four directions from that square.  If there is 
        a legal move in that direction, put it in the moves vector.
    */
    
    if (moves.size() == 0) {
       for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
             if (board[row][col] == player || board[row][col] == playerKing) {
                if (canMove(player,row,col,row+1,col+1))
                   moves.addElement(new CheckersMove(row,col,row+1,col+1));
                if (canMove(player,row,col,row-1,col+1))
                   moves.addElement(new CheckersMove(row,col,row-1,col+1));
                if (canMove(player,row,col,row+1,col-1))
                   moves.addElement(new CheckersMove(row,col,row+1,col-1));
                if (canMove(player,row,col,row-1,col-1))
                   moves.addElement(new CheckersMove(row,col,row-1,col-1));
             }
          }
       }
    }
    
    /* If no legal moves have been found, return null.  Otherwise, create
       an array just big enough to hold all the legal moves, copy the
       legal moves from the vector into the array, and return the array. */
    
    if (moves.size() == 0)
       return null;
    else {
       CheckersMove[] moveArray = new CheckersMove[moves.size()];
       for (int i = 0; i < moves.size(); i++)
          moveArray[i] = (CheckersMove)moves.elementAt(i);
       return moveArray;
    }
	}

	/**
	 * Return a list of the legal jumps that the specified player can make starting
	 * from the specified row and column. If no such jumps are possible, null is
	 * returned. The logic is similar to the logic of the getLegalMoves() method.
	 *
	 * @param player The player of the current jump, either RED or BLACK.
	 * @param row    row index of the start square.
	 * @param col    col index of the start square.
	 */
	CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {

		
	      if (player != RED && player != BLACK)
	          return null;
	       int playerKing;  // The constant representing a King belonging to player.
	       if (player == RED)
	          playerKing = RED_KING;
	       else
	          playerKing = BLACK_KING;
	       Vector moves = new Vector();  // The legal jumps will be stored in this vector.
	       if (board[row][col] == player || board[row][col] == playerKing) {
	          if (canJump(player, row, col, row+1, col+1, row+2, col+2))
	             moves.addElement(new CheckersMove(row, col, row+2, col+2));
	          if (canJump(player, row, col, row-1, col+1, row-2, col+2))
	             moves.addElement(new CheckersMove(row, col, row-2, col+2));
	          if (canJump(player, row, col, row+1, col-1, row+2, col-2))
	             moves.addElement(new CheckersMove(row, col, row+2, col-2));
	          if (canJump(player, row, col, row-1, col-1, row-2, col-2))
	             moves.addElement(new CheckersMove(row, col, row-2, col-2));
	       }
	       if (moves.size() == 0)
	          return null;
	       else {
	          CheckersMove[] moveArray = new CheckersMove[moves.size()];
	          for (int i = 0; i < moves.size(); i++)
	             moveArray[i] = (CheckersMove)moves.elementAt(i);
	          return moveArray;
	       }
	
	}
	
	
	private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
        // This is called by the two previous methods to check whether the
        // player can legally jump from (r1,c1) to (r3,c3).  It is assumed
        // that the player has a piece at (r1,c1), that (r3,c3) is a position
        // that is 2 rows and 2 columns distant from (r1,c1) and that 
        // (r2,c2) is the square between (r1,c1) and (r3,c3).
        
   if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
      return false;  // (r3,c3) is off the board.
      
   if (board[r3][c3] != EMPTY)
      return false;  // (r3,c3) already contains a piece.
      
   if (player == RED) {
      if (board[r1][c1] == RED && r3 > r1)
         return false;  // Regular red piece can only move  up.
      if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
         return false;  // There is no black piece to jump.
      return true;  // The jump is legal.
   }
   else {
      if (board[r1][c1] == BLACK && r3 < r1)
         return false;  // Regular black piece can only move downn.
      if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
         return false;  // There is no red piece to jump.
      return true;  // The jump is legal.
   }

}  // end canJump()


private boolean canMove(int player, int r1, int c1, int r2, int c2) {
      // This is called by the getLegalMoves() method to determine whether
      // the player can legally move from (r1,c1) to (r2,c2).  It is
      // assumed that (r1,r2) contains one of the player's pieces and
      // that (r2,c2) is a neighboring square.
      
   if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
      return false;  // (r2,c2) is off the board.
      
   if (board[r2][c2] != EMPTY)
      return false;  // (r2,c2) already contains a piece.

   if (player == RED) {
      if (board[r1][c1] == RED && r2 > r1)
          return false;  // Regualr red piece can only move down.
       return true;  // The move is legal.
   }
   else {
      if (board[r1][c1] == BLACK && r2 < r1)
          return false;  // Regular black piece can only move up.
       return true;  // The move is legal.
   }
   
}  // end canMove()

}