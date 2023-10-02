import sac.graph.*;
import sac.State;
import sac.StateFunction;

public class MisplacedTiles extends StateFunction {

    @Override
    public double calculate(State state) {
        SlidingPuzzle sp = (SlidingPuzzle) state;
        byte[][] board;

        board = sp.getBoard();

        byte counter = 0;
        byte k = 0;
        for (int i = 0; i < SlidingPuzzle.n; i++)
            for (int j = 0; j < SlidingPuzzle.n; j++, k++) {
                byte current = board[i][j];

                if (current == 0)
                    continue;

                if (current != k)
                    counter++;
            }

        return counter;
    }
}
