package loa;

import ucb.util.CommandArgs;
import static loa.Side.*;

/** Main class of the Lines of Action program.
 * @author Patrick Lu
 */
public class Main {

    /** The main Lines of Action.  ARGS are as described in the
     *  project 3 handout:
     *      [ --white ] [ --ai=N ] [ --seed=S ] [ --time=LIM ] \
     *      [ --debug=D ] [ --display ]
     */
    public static void main(String... args) {
        int N = 1;
        Long seed = (long) 0;
        double time = 0;
        int debug;
        Side player;
        CommandArgs options =
            new CommandArgs("--white{0,1} --ai=(0|1|2){0,1}"
                            + " --seed=(\\d+(\\.\\d+)?){0,1}"
                            + " --time=(\\d+(\\.\\d+)?){0,1}"
                            + " --debug=(\\d+){0,1}"
                            + " --display{0,1}", args);
        if (!options.ok()) {
            usage();
        }
        if (options.containsKey("--ai")) {
            N = options.getInt("--ai");
        }
        if (options.containsKey("--seed")) {
            seed = options.getLong("--seed");
        }
        if (options.containsKey("--time")) {
            time = options.getDouble("--time");
        }
        if (options.containsKey("--debug")) {
            debug = options.getInt("--debug");
        }
        if (options.containsKey("--white")) {
            player = WHITE;
        } else {
            player = BLACK;
        }
        int times = (int) Math.round(time * SECONDS);
        Game game = new Game(N, player, seed, times);
        game.play();
        System.exit(0);
    }




    /** Print brief description of the command-line format. */
    static void usage() {
        System.out.println("This is how you type commands, all optional"
                           + "[ --white ] [ --ai=N ] [ --seed=S ]"
                           + "[ --time=LIM ]"
                           + " [ --debug=D ] [ --display ]");
        System.exit(1);
    }

    /** Multiply to get seconds. */
    private static final int SECONDS = 60;

}
