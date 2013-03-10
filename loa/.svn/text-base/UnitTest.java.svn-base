package loa;

import ucb.junit.textui;
import org.junit.Test;
import static org.junit.Assert.*;
import static loa.Piece.*;
import static loa.Side.*;
import java.util.ArrayList;
import java.util.Iterator;

/** The suite of all JUnit tests for the loa package.
 *  @author Patrick Lu
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    @Test
    public void winnertest() {
        Piece[][] test = {
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, WP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { EMP, BP,  BP,  BP,  BP,  EMP,  EMP,  EMP }
        };
        Piece[][] test2 = {{EMP, EMP, EMP, EMP, BP, BP, EMP, EMP},
                           {EMP, EMP, EMP, WP, EMP, BP, EMP, WP},
                           {WP, BP, EMP, EMP, EMP, BP, EMP, EMP},
                           {EMP, EMP, EMP, EMP, WP, EMP, WP, WP},
                           {EMP, EMP, EMP, EMP, EMP, WP, EMP, EMP},
                           {EMP, WP, EMP, BP, EMP, WP, EMP, EMP},
                           {EMP, BP, EMP, EMP, EMP, EMP, EMP, BP},
                           {EMP, WP, EMP, EMP, BP, EMP, EMP, EMP}};
        Board b = new Board(test, WHITE);
        Board a = new Board(Board.INITIAL_PIECES, WHITE);
        assertEquals("Game didn't end", true, b.piecesContiguous(BLACK));
        assertEquals("Game should be going", false, b.piecesContiguous(WHITE));
        assertEquals("Game should be going", false, a.piecesContiguous(BLACK));
        assertEquals("Game should be going", false, a.piecesContiguous(WHITE));
        Board c = new Board(test2, BLACK);
        assertEquals("Wrong move for the diagonal", 1, b.diagonal22(1, 6, 0));
        assertEquals("Wrong move for the diagonal", 1, b.diagonal2(1, 6, 0));
    }

    @Test
    public void boardtest() {
        Board a = new Board(Board.INITIAL_PIECES, WHITE);
        Move move = Move.create(0, 2, 2, 2);
        Iterator<Move> iter = a.legalMoves();
        assertEquals("Move should be legal", true, a.isLegal(move));
        a.makeMove(move);
        ArrayList<Move> res = new ArrayList<Move>();
        while (iter.hasNext()) {
            res.add(iter.next());
        }
        assertEquals("Not enough moves.", 36, res.size());
        assertEquals("Move didn't go", WP, a.getcontents()[2][2]);
        assertEquals("Move count is wrong", 1, a.movesMade());
        a.retract();
        assertEquals("Move count didn't retract", 0, a.movesMade());
        boolean match = true;
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 8; j += 1) {
                if (Board.INITIAL_PIECES[i][j] != a.getcontents()[i][j]) {
                    match = false;
                    break;
                }
            }
        }
        assertEquals("Board didn't retract", true, match);
        assertEquals("Col count wrong", 6, a.column(0));
        assertEquals("Row count wrong", 6, a.row(0));
        assertEquals("diag count wrong", 1, a.diagonal22(1, 0, 0));
    }
}


