package loa;

import java.util.Iterator;
import java.util.ArrayList;

/** An automated Player.
 *  @author Patrick Lu*/
class MachinePlayer extends Player {

    /** The board. */
    private Board board;

    /** Side. */
    private Side sid;

    /** A MachinePlayer that plays.
     * @param side the side of player
     * @param game the game board
     * @param boards the gameboard */
    MachinePlayer(Side side, Game game, Board boards) {
        super(side, game);
        board = boards;
        sid = side;
    }

    /** Sets side.
        @param player the side*/
    void setside(Side player) {
        sid = player;
    }

    /** Gets sid.
        @return sid the side */
    Side getsid() {
        return sid;
    }

    /** Evaluation function for the board state.
     * @param game the game state
     * @return int the evaluation, the bigger the better */
    int eval(Board game) {
        if (game.piecesContiguous(side())) {
            return Integer.MAX_VALUE;
        }
        if (game.piecesContiguous(side().opponent())) {
            return Integer.MIN_VALUE;
        }
        ArrayList<int[]> human = new ArrayList<int[]>();
        ArrayList<int[]> computer = new ArrayList<int[]>();
        Piece[][] boarding = new Piece[8][8];
        boarding = game.getcontents();
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 8; j += 1) {
                if (boarding[i][j].side() == side()) {
                    int[] a = {i, j};
                    computer.add(a);
                } else if (boarding[i][j].side() == side().opponent()) {
                    int[] a = {i, j};
                    human.add(a);
                }
            }
        }
        int comp = distance(computer);
        int hum = distance(human);
        return hum - comp;
    }

    /** Basic sum of the distance between all pieces. Used for heuristic.
     * @param pieces an arraylist of pieces.
     * @return result an integer. */
    int distance(ArrayList<int[]> pieces) {
        double twopiecedist;
        int sum = 0;
        double x1;
        double y1;
        for (int i = 0; i < pieces.size(); i += 1) {
            for (int j = 0; j < pieces.size(); j += 1) {
                x1 = Math.pow(pieces.get(i)[0] - pieces.get(j)[0], 2);
                y1 = Math.pow(pieces.get(i)[1] - pieces.get(j)[1], 2);
                twopiecedist =
                    Math.sqrt(x1 + y1);
                sum += Math.round(twopiecedist);
            }
        }
        return sum;
    }

    /** Returns the move that gives the best situation.
     * @param side the side you are on.
     * @param depth the depth of the search.
     * @param alpha the max value.
     * @param beta the min value.
     * @param game the boardgame.
     * @return Move first move. */
    Move minmax(Side side, int depth, int alpha, int beta, Board game) {
        Move best = null;
        int bestmove = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;
        Board copy = new Board(game.getcontents(), game.getplayer());
        Iterator<Move> iter = copy.legalMoves();
        while (iter.hasNext()) {
            Move next = iter.next();
            copy.makeMove(next);
            current =
                alphabeta(side.opponent(), copy, alpha, beta, depth - 1);
            if (current > bestmove) {
                best = next;
                bestmove = current;
            }
            copy.retract();
        }
        return best;
    }

    /** Alpha Beta search for best value.
     * @param side side of the computer
     * @param game the current board state
     * @param alpha the max value
     * @param beta the min value
     * @param depth the depth of the search
     * @return int returns the max value */
    int alphabeta(Side side, Board game, int alpha, int beta, int depth) {
        if (board.gameOver() || depth <= 0) {
            return eval(board);
        }
        int score;
        Board copy = new Board(game.getcontents(), game.getplayer());
        Iterator<Move> iter = copy.legalMoves();
        ArrayList<Board> state = new ArrayList<Board>();
        while (iter.hasNext()) {
            copy.makeMove(iter.next());
            state.add(copy);
            copy.retract();
        }
        if (game.getplayer() == side()) {
            for (Board b : state) {
                score = alphabeta(side().opponent(), b, alpha, beta, depth - 1);
                if (score > alpha) {
                    alpha = score;
                }
                if (alpha >= beta) {
                    return alpha;
                }
            }
            return alpha;
        } else {
            for (Board b : state) {
                score =
                    alphabeta(side().opponent(), b, alpha, beta, depth - 1);
                if (score < beta) {
                    beta = score;
                }
                if (alpha >= beta) {
                    return beta;
                }
            }
            return beta;
        }
    }

    @Override
    Move makeMove() {
        return null;
    }

    /** Make the move for the AI.
     * @param game a board.
     * @param depth how deep you want to go.
     * @return move a move. */
    Move makeMove(Board game, int depth) {
        return minmax(side(), depth,
                      Integer.MIN_VALUE, Integer.MAX_VALUE, game);
    }
}
