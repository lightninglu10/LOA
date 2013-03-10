package loa;

import ucb.util.Stopwatch;
import java.util.Iterator;
import java.util.ArrayList;

import static loa.Piece.*;
import static loa.Side.*;

/** Represents the state of a game of Lines of Action. A Board is immutable.
 *  Its MutableBoard subclass allows moves to be made.
 *  @author Patrick Lu
 */
class Board {

    /** A Stopwatch for black. */
    private Stopwatch bwatch;

    /** A stopwatch for white. */
    private Stopwatch wwatch;

    /** Arraylist of moves. */
    private ArrayList<Move> moves;

    /** Number of moves. */
    private int movenum;

    /** Initial content of board. */
    private Piece[][] contents;

    /** Previous state of board. */
    private Piece[][] previous;

    /** Side of player. */
    private Side side;

    /** Copied Board.*/
    private Board copy;

    /** A Board whose initial contents are taken from
     *  INITIALCONTENTS and in which it is PLAYER's move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row-1][col-1]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     */
    Board(Piece[][] initialContents, Side player) {
        assert player != null && initialContents.length == 8;
        contents = new Piece[8][8];
        contents = initialContents;
        side = player;
        moves = new ArrayList<Move>();
        bwatch = new Stopwatch();
        wwatch = new Stopwatch();
        if (player == WHITE) {
            wwatch.start();
        } else {
            bwatch.start();
        }
        movenum = 0;
    }

    /** Sets the Board to the mutable.
        @param board the board */
    void setboard(MutableBoard board) {
        contents = board.getcontent();
    }

    /** Gets the pieces from Board.
     * @return Piece[][] the game board */
    Piece[][] getcontents() {
        return contents;
    }

    /** Gets the player.
     * @return side side of player */
    Side getplayer() {
        return side;
    }

    /** Sets the player.
     * @param player set side to player */
    void setplayer(Side player) {
        side = player;
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BLACK);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        Piece[][] cont = new Piece[8][8];
        for (int x = 0; x < 8; x += 1) {
            for (int y = 0; y < 8; y += 1) {
                cont[x][y] = board.contents[x][y];
            }
        }
        copy = new Board(cont, board.getplayer());
    }

    /** Gets the copied board.
     * @return Board a board. */
    Board getcopy() {
        return copy;
    }

    /** Return the contents of column C, row R, where 1 <= C,R <= 8,
     *  where column 1 corresponds to column 'a' in the standard
     *  notation. */
    Piece get(int c, int r) {
        assert 1 <= c && c <= 8 && 1 <= r && r <= 8;
        return contents[r - 1][c - 1];
    }

    /** Return the contents of the square SQ.  SQ must be the
     *  standard printed designation of a square (having the form cr,
     *  where c is a letter from a-h and r is a digit from 1-8). */
    Piece get(String sq) {
        return get(col(sq), row(sq));
    }

    /** Return the column number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int col(String sq) {
        String result = sq.substring(0, 1);
        if (result.equals("a")) {
            return 0;
        }
        if (result.equals("b")) {
            return 1;
        }
        if (result.equals("c")) {
            return 2;
        }
        if (result.equals("d")) {
            return 3;
        }
        if (result.equals("e")) {
            return 4;
        }
        if (result.equals("f")) {
            return 5;
        }
        if (result.equals("g")) {
            return 6;
        } else {
            return 7;
        }
    }

    /** Return the row number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int row(String sq) {
        return 8 - Integer.parseInt(sq.substring(1, 2));
    }

    /** Return the Side that is currently next to move. */
    Side turn() {
        return side;
    }

    /** Return true iff MOVE is legal for the player currently on move. */
    boolean isLegal(Move move) {
        int diagcount; int diagcount2; int[] move1 = new int[2];
        int[] move2 = new int[2]; int[] move3 = new int[2];
        int[] move4 = new int[2]; int[] move5 = new int[2];
        int[] move6 = new int[2]; int[] move7 = new int[2];
        int[] move8 = new int[2]; int rowcount; int colcount;
        int currentcol = move.getCol0(); int currentrow = move.getRow0();
        int currentcols = move.getCol1(); int currentrows = move.getRow1();
        if ((contents[currentrow][currentcol] == BP && side == BLACK)
            || (contents[currentrow][currentcol] == WP && side == WHITE)) {
            if (diagonal(currentcol, currentrow, 0) == 0
                || diagonal12(currentcol, currentrow, 0) == 0) {
                diagcount = diagonal(currentcol, currentrow, 0)
                    + diagonal12(currentcol, currentrow, 0);
            } else {
                diagcount = diagonal(currentcol, currentrow, 0)
                    + diagonal12(currentcol, currentrow, 0) - 1;
            }
            if (diagonal2(currentcol, currentrow, 0) == 0
                || diagonal22(currentcol, currentrow, 0) == 0) {
                diagcount2 = diagonal2(currentcol, currentrow, 0)
                    + diagonal22(currentcol, currentrow, 0);
            } else {
                diagcount2 = diagonal2(currentcol, currentrow, 0)
                    + diagonal22(currentcol, currentrow, 0) - 1;
            }
            rowcount = row(currentrow);
            colcount = column(move.getCol0());
            move1 = diag(move.getCol0(), move.getRow0(), diagcount);
            move2 = diag2(move.getCol0(), move.getRow0(), diagcount);
            move3 = diag3(move.getCol0(), move.getRow0(), diagcount2);
            move4 = diag4(move.getCol0(), move.getRow0(), diagcount2);
            move5 = up(move.getCol0(), move.getRow0(), colcount);
            move6 = down(move.getCol0(), move.getRow0(), colcount);
            move7 = left(move.getCol0(), move.getRow0(), rowcount);
            move8 = right(move.getCol0(), move.getRow0(), rowcount);
            if ((currentcols >= 0 && currentrows >= 0)
                && ((move1[0] == currentrows && move1[1] == currentcols)
                    || (move2[0] == currentrows && move2[1] == currentcols)
                    || (move3[0] == currentrows && move3[1] == currentcols)
                    || (move4[0] == currentrows && move4[1] == currentcols)
                    || (move5[0] == currentrows && move5[1] == currentcols)
                    || (move6[0] == currentrows && move6[1] == currentcols)
                    || (move7[0] == currentrows && move7[1] == currentcols)
                    || (move8[0] == currentrows && move8[1] == currentcols))) {
                return true;
            }
        }
        return false;
    }

     /** Return a sequence of all legal from this position. */
    Iterator<Move> legalMoves() {
        Move move1, move2, move3, move4, move5, move6, move7, move8;
        int rows, col, diags, diags2;
        int[] row1 = new int[2]; int[] row2 = new int[2];
        int[] col1 = new int[2]; int[] col2 = new int[2];
        int[] d1 = new int[2]; int[] d2 = new int[2];
        int[] d3 = new int[2]; int[] d4 = new int[2];
        ArrayList<Move> legals = new ArrayList<Move>();
        for (int x = 0; x < 8; x += 1) {
            for (int i = 0; i < 8; i += 1) {
                rows = row(x); col = column(i);
                diags = diagonal(i, x, 0) + diagonal12(i, x, 0) - 1;
                diags2 = diagonal2(i, x, 0) + diagonal22(i, x, 0) - 1;
                row1 = left(i, x, rows); row2 = right(i, x, rows);
                col1 = up(i, x, col); col2 = down(i, x, col);
                d1 = diag(i, x, diags); d2 = diag2(i, x, diags);
                d3 = diag3(i, x, diags2); d4 = diag4(i, x, diags2);
                move1 = Move.create(i, x, row1[1], row1[0]);
                move2 = Move.create(i, x, row2[1], row2[0]);
                move3 = Move.create(i, x, col1[1], col1[0]);
                move4 = Move.create(i, x, col2[1], col2[0]);
                move5 = Move.create(i, x, d1[1], d1[0]);
                move6 = Move.create(i, x, d2[1], d2[0]);
                move7 = Move.create(i, x, d3[1], d3[0]);
                move8 = Move.create(i, x, d4[1], d4[0]);
                Move[] movez =
                {move1, move2, move3, move4, move5, move6, move7, move8};
                for (int z = 0; z < movez.length; z += 1) {
                    if (isLegal(movez[z])) {
                        legals.add(movez[z]);
                    }
                }
            }
        }
        return legals.iterator();
    }

     /** Returns the downwards move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] down(int col, int row, int nummoves) {
        if (nummoves == 0 && row < 8 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        } else if (row > 7 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return down(col, row + 1, nummoves - 1);
        }
    }

     /** Returns the up move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] up(int col, int row, int nummoves) {
        if (nummoves == 0 && row >= 0 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (row < 0 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return up(col, row - 1, nummoves - 1);
        }
    }

     /** Returns the right move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] right(int col, int row, int nummoves) {
        if (nummoves == 0 && col < 8 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (col > 7 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return right(col + 1, row, nummoves - 1);
        }
    }

    /** Returns the left move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] left(int col, int row, int nummoves) {
        if (nummoves == 0 && col >= 0 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (col < 0 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return left(col - 1, row, nummoves - 1);
        }
    }

     /** Returns the diagonal move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] diag(int col, int row, int nummoves) {
        if (nummoves == 0 && col >= 0 && row < 8 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (row > 7 || col < 0 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return diag(col - 1, row + 1, nummoves - 1);
        }
    }

    /** Returns the diagonal move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] diag2(int col, int row, int nummoves) {
        if (nummoves == 0 && col < 8 && row >= 0 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (row < 0 || col > 7 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return diag2(col + 1, row - 1, nummoves - 1);
        }
    }

    /** Returns the diagonal move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] diag3(int col, int row, int nummoves) {
        if (nummoves == 0 && col >= 0 && row >= 0 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (row < 0 || col < 0 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return diag3(col - 1, row - 1, nummoves - 1);
        }
    }

    /** Returns the diagonal move.
     * @param row row of this
     * @param col col of this
     * @param nummoves number of chips in the row
     * @return int[] pair of row, col */
    int[] diag4(int col, int row, int nummoves) {
        if (nummoves == 0 && col < 8 && row < 8 && !((contents[row][col] == WP
                                         && side == WHITE)
            || (contents[row][col] == BP && side == BLACK))) {
            int[] a = {row, col};
            return a;
        }
        if (row > 7 || col > 7 || (contents[row][col] == WP && side == BLACK)
            || (contents[row][col] == BP && side == WHITE)) {
            int[] a = {-1, -1};
            return a;
        } else {
            return diag4(col + 1, row + 1, nummoves - 1);
        }
    }

    /** Checks the number of chips in the diagnoal of This.
     * @param row row of this
     * @param col col of this
     * @param chips number of chips in the row
     * @return int the number of chips in the diagonal */
    int diagonal(int col, int row, int chips) {
        if (contents[row][col] != EMP) {
            chips += 1;
        }
        if (row == 7 || col == 0) {
            return chips;
        } else {
            return diagonal(col - 1, row + 1, chips);
        }
    }

    /** Checks the number of chips in the diagnoal of This.
     * @param row row of this
     * @param col col of this
     * @param chips number of chips in the row
     * @return int the number of chips in the diagonal */
    int diagonal12(int col, int row, int chips) {
        if (contents[row][col] != EMP) {
            chips += 1;
        }
        if (col == 7 || row == 0) {
            return chips;
        } else {
            return diagonal12(col + 1, row - 1, chips);
        }
    }


    /** Checks the number of chips in the diagnoal of This.
     * @param row row of this
     * @param col col of this
     * @param chips number of chips in row
     * @return int the number of chips in the diagonal */
    int diagonal2(int col, int row, int chips) {
        if (contents[row][col] != EMP) {
            chips += 1;
        }
        if (col == 7 || row == 7) {
            return chips;
        } else {
            return diagonal2(col + 1, row + 1, chips);
        }
    }

/** Checks the number of chips in the diagnoal of This.
     * @param row row of this
     * @param col col of this
     * @param chips number of chips in row
     * @return int the number of chips in the diagonal */
    int diagonal22(int col, int row, int chips) {
        if (contents[row][col] != EMP) {
            chips += 1;
        }
        if (row == 0 || col == 0) {
            return chips;
        } else {
            return diagonal22(col - 1, row - 1, chips);
        }
    }

    /** Checks the number of chips in the column of This.
     * @param col col of this
     * @return int the number of chips in the column */
    int column(int col) {
        int chips = 0;
        for (int row = 0; row < 8; row += 1) {
            if (contents[row][col] != EMP) {
                chips += 1;
            }
        }
        return chips;
    }

    /** Checks the number of chips in the row of This.
     * @param rows row of this
     * @return int the number of chips in the row */
    int row(int rows) {
        int chips = 0;
        for (int col = 0; col < 8; col += 1) {
            if (contents[rows][col] != EMP) {
                chips += 1;
            }
        }
        return chips;
    }

    /** Return true iff the game is currently over.  A game is over if
     *  either player has all his pieces continguous. */
    boolean gameOver() {
        return piecesContiguous(BLACK) || piecesContiguous(WHITE);
    }

    /** Return true iff PLAYER's pieces are continguous. */
    boolean piecesContiguous(Side player) {
        int pieces = 0; int[] hold = new int[2];
        for (int x = 0; x < 8; x += 1) {
            for (int y = 0; y < 8; y += 1) {
                if ((player == BLACK && contents[x][y] == BP)
                    || (player == WHITE && contents[x][y] == WP)) {
                    pieces += 1;
                    hold[0] = x;
                    hold[1] = y;
                }
            }
        }
        boolean[][] map = new boolean[8][8];
        int result = check(player, hold[0], hold[1], map);
        return result == pieces;
    }

    /** Helps to check if contiguous.
     * @param player side of player
     * @param x x value
     * @param y y value
     * @param map a boolean map to tell if pieces have been reached.
     * @return int an integer */
    int check(Side player, int x, int y, boolean[][] map) {
        int pieces = 1;
        map[x][y] = true;
        for (int i = -1; i <= 1; i += 1) {
            for (int j = -1; j <= 1; j += 1) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int changex = x + i;
                int changey = y + j;
                if (changex > 7 || changex < 0 || changey > 7 || changey < 0) {
                    continue;
                }
                if ((player == BLACK && contents[changex][changey] != BP)
                    || (player == WHITE && contents[changex][changey] != WP)) {
                    continue;
                }
                if (map[changex][changey]) {
                    continue;
                }
                pieces += check(player, changex, changey, map);
            }
        }
        return pieces;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return movenum;
    }

    /** Returns move #K used to reach the current position, where
     *  0 <= K < movesMade().  Does not include retracted moves. */
    Move getMove(int k) {
        return moves.get(k);
    }

    /** Get the moves in an arraylist.
     * @return moves */
    ArrayList<Move> move() {
        return moves;
    }

    /** Return the black watch.
     *@return black watch */
    Stopwatch blackwatch() {
        return bwatch;
    }

    /** Return the white watch.
     * @return white watch */
    Stopwatch whitewatch() {
        return wwatch;
    }

    /** Assuming isLegal(MOVE), make MOVE. */
    void makeMove(Move move) {
        if (isLegal(move)) {
            previous = new Piece[8][8];
            for (int x = 0; x < 8; x += 1) {
                for (int y = 0; y < 8; y += 1) {
                    previous[x][y] = contents[x][y];
                }
            }
            movenum += 1;
            contents[move.getRow0()][move.getCol0()] = EMP;
            if (side == BLACK) {
                contents[move.getRow1()][move.getCol1()] = BP;
            } else {
                contents[move.getRow1()][move.getCol1()] = WP;
            }
            if (side == WHITE) {
                wwatch.stop();
                bwatch.start();
            } else {
                bwatch.stop();
                wwatch.start();
            }
            moves.add(move);
            side = side.opponent();
        }
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        for (int x = 0; x < 8; x += 1) {
            for (int y = 0; y < 8; y += 1) {
                contents[x][y] = previous[x][y];
            }
        }
        if (side == WHITE && wwatch.isRunning()) {
            wwatch.stop();
            bwatch.start();
        } else if (side == BLACK && bwatch.isRunning()) {
            bwatch.stop();
            wwatch.start();
        }
        if (side == WHITE) {
            side = BLACK;
        } else {
            side = WHITE;
        }
        movenum -= 1;
        moves.remove(moves.size() - 1);

    }

    @Override
    public String toString() {
        String board = "";
        String movez = "Moves: " + movenum;
        String nextmove = "Next move: " + side.toString();
        String equals = "===";
        String result = "";
        for (Piece[] row : contents) {
            for (Piece piece : row) {
                board += " ";
                if (piece == EMP) {
                    board += "-";
                }
                if (piece == BP) {
                    board += "b";
                }
                if (piece == WP) {
                    board += "w";
                }
            }
            board += "\n";
        }
        result += (equals + "\n");
        result += board;
        result += (nextmove);
        result += ("\n" + movez);
        result += ("\n" + equals);
        return result;
    }

    /** Gets initial pieces.
        @return piece[][] the initial board*/
    static Piece[][] getinitial() {
        return INITIAL_PIECES;
    }

    /** The standard initial configuration for Lines of Action. */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

}
