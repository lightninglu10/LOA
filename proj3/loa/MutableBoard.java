package loa;

import java.util.ArrayList;

import static loa.Piece.*;
import static loa.Side.*;

/** Represents the state of a game of Lines of Action, and allows making moves.
 *  @author Patrick Lu*/
class MutableBoard extends Board {

    /** Number of moves. */
    private int movenum;

    /** Previous state of board. */
    private Piece[][] previous;

    /** ArrayList of moves in order they occur. */
    private ArrayList<Move> moves = new ArrayList<Move>();

    /** Content of board. */
    private Piece[][] content;

    /** Side of player. */
    private Side play;

    /** Copy of board. */
    private Board copys;

    /** A MutableBoard whose initial contents are taken from
     *  INITIALCONTENTS and in which it is PLAYER's move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row-1][col-1]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     */
    MutableBoard(Piece[][] initialContents, Side player) {
        super(initialContents, player);
        content = new Piece[8][8];
        for (int x = 0; x < 8; x += 1) {
            for (int y = 0; y < 8; y += 1) {
                content[x][y] = this.getcontents()[x][y];
            }
        }
        play = this.getplayer();
    }

    /** Gets content.
     @return Piece[][] the game board*/
    Piece[][] getcontent() {
        return content;
    }

    /** A new board in the standard initial position. */
    MutableBoard() {
        super();
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    MutableBoard(Board board) {
        super(board);
    }
}
