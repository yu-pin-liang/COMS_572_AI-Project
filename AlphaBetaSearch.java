package project;

public class AlphaBetaSearch {
	private CheckersData board;
	private CheckersData future;


	// An instance of this class will be created in the Checkers.Board
	// It would be better to keep the default constructor.

	public void setCheckersData(CheckersData board) {
		this.board = board;
		
	}

	// Todo: You can implement your helper methods here


	public int maxValue(int[][] state, int player, int alpha, int beta, int depth) {

		future = new CheckersData (board);
		if (depth == 0) {

			return (future.howmany(3) - future.howmany(1));
		}

		else {

			int value = -99999;

			for (int i = 0; i < future.getLegalMoves(3).length; i++) {
				future.makeMove(future.getLegalMoves(3)[i]);

				value = Math.max(value, minValue(future.board, player, alpha, beta, depth - 1));

				if (value >= beta)
					return value;
				alpha = Math.max(alpha, value);
			}
			return value;
		}
	}

	public int minValue(int[][] state, int player, int alpha, int beta, int depth) {

		if (depth == 0) {
			return (future.howmany(3) - future.howmany(1));
		}

		int value = 99999;
		for (int i = 0; i < future.getLegalMoves(1).length; i++) {

			future.makeMove(future.getLegalMoves(1)[i]);

			value = Math.min(value, maxValue(future.board, player, alpha, beta, depth - 1));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}

	/**
	 * You need to implement the Alpha-Beta pruning algorithm here to find the best
	 * move at current stage. The input parameter legalMoves contains all the
	 * possible moves. It contains four integers: fromRow, fromCol, toRow, toCol
	 * which represents a move from (fromRow, fromCol) to (toRow, toCol). It also
	 * provides a utility method `isJump` to see whether this move is a jump or a
	 * simple move.
	 *
	 * pick a good one to move
	 *
	 * @param legalMoves All the legal moves for the agent at current step.
	 */
	public CheckersMove makeMove(CheckersMove[] legalMoves) {
		CheckersMove result = null;
	    int score = -999999;
		
		for (int i=0; i<legalMoves.length;i++) {
			//future.makeMove(legalMoves[i]);
			int value = minValue(future.board,1,-99999,99999,3);
			if (value> score) {
				result = legalMoves[i];
				score = value;
			}
		}

		//return result;
		return legalMoves[0];
	}
}
