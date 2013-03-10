package loa;

import java.util.TreeMap;


/** A move in Lines of Action.
 *  @author Patrick Lu */
class Move {

    /* Implementation note: We create moves by means of static "factory
     * methods" all named create, which in turn use the single (private)
     * constructor.  Factory methods have certain advantages over constructors:
     * they allow you to produce results having an arbitrary subtype of Move,
     * and they don't require that you produce a new object each time.  This
     * second advantage is useful when you are trying to speed up the creation
     * of Moves for use in automated searching for moves.  You can (if you
     * want) create just one instance of the Move representing 1-5, for example
     * and return it whenever that move is requested. */

    /** HashMap to hold the moves. */
    private static TreeMap<String, Move> allmoves = new TreeMap<String, Move>();

    /** Return a move of the piece at COLUMN0, ROW0 to COLUMN1, ROW1. */
    static Move create(int column0, int row0, int column1, int row1) {
        String key = getkey(column0, row0, column1, row1);
        if (!allmoves.containsKey(key)) {
            allmoves.put(key, new Move(column0, row0, column1, row1));
        }
        return allmoves.get(key);
    }

    /** Return the string version of the move.
     * @param column0 col0
     * @param row0 row0
     * @param column1 where you're going
     * @param row1 where you're going
     * @return string */
    static String getkey(int column0, int row0, int column1, int row1) {
        String c1key; String c2key; String result;
        if (column0 == 0) {
            c1key = "a";
        } else if (column0 == 1) {
            c1key = "b";
        } else if (column0 == 2) {
            c1key = "c";
        } else if (column0 == 3) {
            c1key = "d";
        } else if (column0 == 4) {
            c1key = "e";
        } else if (column0 == 5) {
            c1key = "f";
        } else if (column0 == 6) {
            c1key = "g";
        } else {
            c1key = "h";
        }
        if (column1 == 0) {
            c2key = "a";
        } else if (column1 == 1) {
            c2key = "b";
        } else if (column1 == 2) {
            c2key = "c";
        } else if (column1 == 3) {
            c2key = "d";
        } else if (column1 == 4) {
            c2key = "e";
        } else if (column1 == 5) {
            c2key = "f";
        } else if (column1 == 6) {
            c2key = "g";
        } else {
            c2key = "h";
        }
        result = c1key + Integer.toString(8 - row0) + "-" + c2key
            + Integer.toString(8 - row1);
        return result;
    }

    /** A new Move of the piece at COL0, ROW0 to COL1, ROW1. */
    private Move(int col0, int row0, int col1, int row1) {
        _col0 = col0;
        _row0 = row0;
        _col1 = col1;
        _row1 = row1;
    }

    /** Return the column at which this move starts, as an index in 1--8. */
    int getCol0() {
        return _col0;
    }

    /** Return the row at which this move starts, as an index in 1--8. */
    int getRow0() {
        return _row0;
    }

    /** Return the column at which this move ends, as an index in 1--8. */
    int getCol1() {
        return _col1;
    }

    /** Return the row at which this move ends, as an index in 1--8. */
    int getRow1() {
        return _row1;
    }

    /** Return the length of this move (number of squares moved). */
    int length() {
        return Math.max(Math.abs(_row1 - _row0), Math.abs(_col1 - _col0));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) {
            return false;
        }
        Move move2 = (Move) obj;
        return this.hashCode() == move2.hashCode();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return getkey(_col0, _row0, _col1, _row1);
    }

    /** Column and row numbers of starting and ending points. */
    private int _col0, _row0, _col1, _row1;

}
