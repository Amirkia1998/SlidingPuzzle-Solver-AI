import sac.graph.*;
import sac.State;
import sac.StateFunction;

public class Manhattan extends StateFunction {

    @Override
    public double calculate(State state) {
        SlidingPuzzle sp = (SlidingPuzzle) state;
        byte[][] board = sp.getBoard();
        int distanceSum = 0;
        byte n = SlidingPuzzle.n;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                byte current = board[i][j];

                if (current == 0)
                    continue;

                byte goalY = (byte) (current / n);
                byte goalX = (byte) (current % n);
                distanceSum += Math.abs(i - goalY) + Math.abs(j - goalX);
            }
        }

        return distanceSum;
    }

}
