package loa;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import static loa.Side.*;
import static loa.Piece.*;

/** Represents one game of Lines of Action.
 *  @author Patrick Lu*/
class Game {

    /** HashMap to hold the moves. */
    private HashMap<String, Move> allmoves;

    /** Number of humans. */
    private int human;

    /** Side of first player. */
    private Side you;

    /** Seed value. */
    private long seeds;

    /** Time per turn. */
    private int times;

    /** Last move. */
    private String move;

    /** Entered the thing. */
    private boolean entered;

    /** Test board. */
    private static Piece[][] test = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, WP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, BP,  BP,  BP,  BP,  EMP,  EMP,  EMP }
    };

    /** Test board that Black should win. */
 /** The standard initial configuration for Lines of Action. */
    static final Piece[][] SINITIAL_PIECES = {
        { WP, BP, BP, BP, BP, BP, BP, WP },
        { WP, EMP, EMP, EMP, EMP, EMP, EMP, BP },
        { WP, EMP, EMP, EMP, EMP, EMP, EMP, BP },
        { WP, EMP, EMP, EMP, EMP, EMP, EMP, BP },
        { WP, EMP, EMP, EMP, EMP, EMP, EMP, BP },
        { WP, EMP, EMP, BP, EMP, EMP, EMP, BP },
        { WP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP, WP, WP, WP, EMP, BP, EMP, EMP }
    };

    /** Tells if the AI is in the game. */
    private boolean ai;

    /** A new Game between NUMHUMAN humans and 2-NUMHUMAN AIs.  SIDE0
     *  indicates which side the first player (known as ``you'') is
     *  playing.  SEED is a random seed for random-number generation.
     *  TIME is the time limit each side has to make its moves (in seconds).
     *  A TIME value of <=0 means there is no time limit.  A SEED value <= 0
     *  means to use a randomly seeded generator.
     */
    Game(int numHuman, Side side0, long seed, int time) {
        if (seed <= 0) {
            _randomSource = new Random();
        } else {
            _randomSource = new Random(seed);
        }
        human = numHuman;
        you = side0;
        times = time;
        _board = new Board(Board.getinitial(), you);
        allmoves = new HashMap<String, Move>();
        ai = false;
        entered = false;
    }

    /** Return the current board. */
    Board getBoard() {
        return _board;
    }

    /** Return a move from the terminal.  Processes any intervening commands
     *  as well. */
    String getMove() {
        return move;
    }

    /** Play this game, printing any transcript and other results. */
    public void play() {
        reader(_board);
    }

    /** Prompts user for inputs and reads them to HumanPlayer.
        This is for the user to play the game.
        @param game the board. */
    void reader(Board game) {
        Pattern match = Pattern.compile("\\G([a-h][1-8]\\-[a-h][1-8])");
        Pattern shows = Pattern.compile("\\Gs|t-black|t-white|q|p");
        Scanner input = new Scanner(new InputStreamReader(System.in));
        Iterator<Move> iter = game.legalMoves();
        Game ga = new Game(human, you, 0, times);
        MachinePlayer machine = new MachinePlayer(you.opponent(), ga, game);
        if (human != 2) {
            if (you == WHITE) {
                game.setplayer(BLACK);
                game.whitewatch().stop();
                game.blackwatch().start();
            }
        }
        while (!game.gameOver() && timeRemaining(WHITE, game) > 0
               && timeRemaining(BLACK, game) > 0) {
            if ((game.getplayer() == you || !ai) && !entered) {
                System.out.print("Enter your move >");
                System.out.flush();
                move = "not a valid move"; String show = "";
                if (input.hasNext(match)) {
                    move = input.findInLine(match);
                } else if (input.hasNext(shows)) {
                    show = input.findInLine(shows);
                }
                if (!move.equals("not a valid move")) {
                    humanmove(move, game);
                }
                if (show.equals("s")) {
                    System.out.println(game.toString());
                } else if (show.equals("t-black")) {
                    System.out.println(timeRemaining(BLACK, game));
                } else if (show.equals("t-white")) {
                    System.out.println(timeRemaining(WHITE, game));
                } else if (show.equals("q")) {
                    System.exit(0);
                } else if (show.equals("p")) {
                    ai = true;
                    if (human == 2) {
                        entered = true;
                    }
                    if (game.getplayer() == you) {
                        game.setplayer(you.opponent());
                        if (you == WHITE) {
                            game.whitewatch().stop();
                            game.blackwatch().start();
                        } else {
                            game.blackwatch().stop();
                            game.whitewatch().start();
                        }
                    }
                }
            } else if (ai) {
                aimoves(machine, game);
                continue;
            }
            input.nextLine();
        }
        winner(game);
    }

    /** AI Moves.
     * @param machine MachinePlayer.
     * @param game board. */
    void aimoves(MachinePlayer machine, Board game) {
        if (human == 2) {
            Move ai2 = machine.makeMove(game, 2);
            if (machine.getsid() == WHITE && game.movesMade() == 0) {
                machine.setside(BLACK);
            }
            game.makeMove(ai2);
            System.out.println(
                machine.getsid().toString().substring(0, 1).toUpperCase() + "::"
                + ai2.toString());
            machine.setside(machine.getsid().opponent());
        } else {
            Move ai1 = machine.makeMove(game, 2);
            game.makeMove(ai1);
            if (machine.side() == WHITE) {
                System.out.println("W::" + ai1.toString());
            } else if (machine.side() == BLACK) {
                System.out.println("B::" + ai1.toString());
            }
        }
    }

    /** Declares the winner.
     *@param game the board */
    void winner(Board game) {
        if (game.piecesContiguous(BLACK) || timeRemaining(WHITE, game) <= 0) {
            System.out.println("Black wins.");
        } else if (game.piecesContiguous(WHITE)
                   || timeRemaining(BLACK, game) <= 0) {
            System.out.println("White wins.");
        }
        if (game.piecesContiguous(BLACK) && game.piecesContiguous(WHITE)) {
            String a = game.getplayer().opponent().toString();
            System.out.println(a.substring(0, 1).toUpperCase() + a.substring(1)
                               + "wins.");
        }
    }

    /** Reads the input from the human and moves the piece.
     * @param moves the string moves
     * @param game the board. */
    void humanmove(String moves, Board game) {
        int col0; int col1; int row0; int row1; Move one;
        col0 = Board.col(move.substring(0, 2));
        row0 = Board.row(move.substring(0, 2));
        row1 = Board.row(move.substring(3, 5));
        col1 = Board.col(move.substring(3, 5));
        if (!allmoves.containsKey(moves)) {
            one = Move.create(col0, row0, col1, row1);
            allmoves.put(moves, one);
        } else {
            one = allmoves.get(moves);
        }
        if (!game.isLegal(one)) {
            System.out.println("Your move is invalid, choose another.");
        }
        game.makeMove(one);
    }

    /** Return time remaining for SIDE (in seconds).
        @param game the game board
        @param side the side of the player */
    int timeRemaining(Side side, Board game) {
        if (times == 0) {
            return 1;
        }
        if (side == BLACK) {
            long watch = game.blackwatch().getAccum();
            float seconds = watch / div;
            seconds = (float) times - seconds;
            return Math.round(seconds);
        } else {
            long watch = game.whitewatch().getAccum();
            float seconds = watch / div;
            seconds = (float) times - seconds;
            return Math.round(seconds);
        }
    }

    /** Return the random number generator for this game. */
    Random getRandomSource() {
        return _randomSource;
    }

    /** The official game board. */
    private Board _board;

    /** A source of random numbers, primed to deliver the same sequence in
     *  any Game with the same seed value. */
    private Random _randomSource;

    /** Divide to get seconds. */
    private final int div = 1000;
}
