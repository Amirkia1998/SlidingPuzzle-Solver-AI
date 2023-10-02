import sac.graph.*;
import java.util.*;


public class SlidingPuzzle extends GraphStateImpl {
    //=================================================================== Properties
    public static final int n = 3;
    public static final int n2 = n*n;
    public static final int nShuffle = 1000;
    public static final int nPuzzles = 10;
    private byte[][] board = null;
    public static final Random random = new Random();
    // the starting point(0, 0) of the board is the top leftmost cell
    private byte zeroX; // X coordinate of zero (empty cell)
    private byte zeroY; // Y coordinate of zero (empty cell)
    private ArrayList<Move> legalMoves = new ArrayList<>();
    public enum Move {
        UP, RIGHT, DOWN, LEFT
    }
    //=================================================================== Constructors
    public SlidingPuzzle() {
        board = new byte[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = (byte) (i*SlidingPuzzle.n + j);

        /*
        * as the legal moves are decided using the current coordination of 0,
        * we must first update zeroX and zeroY and then update legal moves
        */
        this.updateZeroCoord();
        this.updatelegalMoves();
    }
    //----------------------------------------------------------------------------------------------
    public SlidingPuzzle(SlidingPuzzle parent) {
        this.board = new byte[n][n];

        System.arraycopy(parent.board, 0, this.board, 0, n);

        this.legalMoves.addAll(parent.legalMoves);
        this.zeroX = parent.zeroX;
        this.zeroY = parent.zeroY;
    }
    //==================================================================== Methods
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(256);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(board[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
    //----------------------------------------------------------------------------------------------
    public void shuffleBoard() {

        for (int i = 0; i < SlidingPuzzle.nShuffle; i++) {
            int bound = this.legalMoves.size();
            int randomNumber = random.nextInt(bound);
            Move move = this.legalMoves.get(randomNumber);

            switch (move) {
                case UP -> this.moveUp();
                case DOWN -> this.moveDown();
                case LEFT -> this.moveLeft();
                case RIGHT -> this.moveRight();
            }

        }

        System.out.println(String.format("\nBoard shuffled %d times succesfully!", SlidingPuzzle.nShuffle));
        System.out.println(String.format("zeroX=%d   zeroY=%d   legalMoves=%s\n\n", this.zeroX, this.zeroY, this.legalMoves));
    }
    //----------------------------------------------------------------------------------------------
    private static byte[] switchCells(byte[] array, byte i, byte j) {
        byte tmp;

        tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

        return array;
    }
    //----------------------------------------------------------------------------------------------
    public void updateZeroCoord() {
        myLabel:
        for (byte i = 0; i < n; i++) {
            for (byte j = 0; j < n; j++) {
                if (this.board[i][j] == 0) {
                    this.zeroY = i;
                    this.zeroX = j;
                    break myLabel;
                }
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    public void updatelegalMoves() {
        // first clear the current array
        if (!legalMoves.isEmpty()) {
            legalMoves.clear();
        }

        if (zeroX > 0) legalMoves.add(Move.LEFT);
        if (zeroX < n-1) legalMoves.add(Move.RIGHT);
        if (zeroY > 0) legalMoves.add(Move.UP);
        if (zeroY < n-1) legalMoves.add(Move.DOWN);
    }
    //----------------------------------------------------------------------------------------------
    public void moveUp() {
        try {
            this.checkMove(Move.UP);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        this.makeMove(zeroY, zeroX, zeroY-1, zeroX);

        this.zeroY--;
        try {
            this.checkBounds();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

        this.updatelegalMoves();
    }
    //----------------------------------------------------------------------------------------------
    public void moveRight() {
        try {
            this.checkMove(Move.RIGHT);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        this.makeMove(zeroY, zeroX, zeroY, zeroX+1);

        this.zeroX++;
        try {
            this.checkBounds();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

        this.updatelegalMoves();
    }
    //----------------------------------------------------------------------------------------------
    public void moveDown() {
        try {
            this.checkMove(Move.DOWN);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        this.makeMove(zeroY, zeroX, zeroY+1, zeroX);

        this.zeroY++;
        try {
            this.checkBounds();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }

        this.updatelegalMoves();
    }
    //----------------------------------------------------------------------------------------------
    public void moveLeft() {
        try {
            this.checkMove(Move.LEFT);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        this.makeMove(zeroY, zeroX, zeroY, zeroX-1);

        this.zeroX--;
        try {
            this.checkBounds();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }


        this.updatelegalMoves();
    }
    //----------------------------------------------------------------------------------------------
    private void makeMove(int y1, int x1, int y2, int x2) {
        byte tmp;

        tmp = this.board[y1][x1];
        this.board[y1][x1] = this.board[y2][x2];
        this.board[y2][x2] = tmp;
    }
    //----------------------------------------------------------------------------------------------
    public void checkBounds() throws IndexOutOfBoundsException {
        if (zeroX < 0 || zeroX > n-1 || zeroY < 0 || zeroY > n-1)
            throw new IndexOutOfBoundsException("zeroX or zeroY Coordinates are out of bounds.");
    }
    //----------------------------------------------------------------------------------------------
    public void checkMove(Move move) throws IllegalArgumentException {
        boolean isLegal = false;

        for (Move m : legalMoves) {
            if (m == move) {
                isLegal = true;
                break;
            }
        }

        if (!isLegal)
            throw new IllegalArgumentException(String.format("Move %s is not legal. Current State:\n%szeroX:%d zeroY:%d : legalMoves:%s\n"
                    , move.toString(), this, this.zeroX, this.zeroY, this.legalMoves));
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        return toString().hashCode();
//        byte[] flatSudoku = new byte[n2 * n2];
//        int k = 0;
//        for (int i = 0; i < n2; i++) {
//            for (int j = 0; j < n2; j++) {
//                flatSudoku[k++] = board[i][j];
//            }
//        }
//        return Arrays.hashCode(flatSudoku);
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> children = new ArrayList<>();

        // a copy of the legal moves created to avoid concurrent error which occurs
        // when we are iterating legalMoves using foreach
        //ArrayList<Move> tmpLegalMoves = new ArrayList<>(this.legalMoves);
        for (Move move: this.legalMoves) {
            SlidingPuzzle child = new SlidingPuzzle(this);

            switch (move) {
                case UP: {
                    child.moveUp();
                    child.setMoveName("U");
                    break;
                }
                case DOWN: {
                    child.moveDown();
                    child.setMoveName("D");
                    break;
                }
                case LEFT: {
                    child.moveLeft();
                    child.setMoveName("L");
                    break;
                }
                case RIGHT: {
                    child.moveRight();
                    child.setMoveName("R");
                    break;
                }
            }

            child.updateZeroCoord();
            child.updatelegalMoves();

            children.add(child);
        }

        return children;
    }
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean isSolution() {
        byte k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++, k++) {
                if (this.board[i][j] != k)
                    return false;
            }
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------
    public byte[][] getBoard() {
        return board;
    }





    //======================================================================== Main
    public static void main2(String[] args) {
        Stats statMPT = new Stats();   //Stats for Misplaced Tiles
        Stats statMAN = new Stats();   //Stats for Manhattan

        for (int i = 0; i < SlidingPuzzle.nPuzzles; i++) {
            SlidingPuzzle sp1 = new SlidingPuzzle();
            sp1.shuffleBoard();
            SlidingPuzzle sp2 = new SlidingPuzzle(sp1);

            // Using Misplaced Tiles Heuristic-----------------------
            SlidingPuzzle.setHFunction(new MisplacedTiles());
            GraphSearchAlgorithm algoMPT = new AStar(sp1);
            algoMPT.execute();

            ArrayList<GraphState> solutionsMPT = (ArrayList<GraphState>) algoMPT.getSolutions();
            GraphState solutionMPT = solutionsMPT.get(0);
            LinkedList<GraphState> solutionPathMPT = (LinkedList<GraphState>) solutionMPT.getPath();
            long pathLengthMPT = solutionPathMPT.size();

            statMPT.setN(statMPT.getN() + (long) 1);
            statMPT.setTotalOpenStates(statMPT.getTotalOpenStates() + (long) algoMPT.getOpenSet().size());
            statMPT.setTotalCloseStates(statMPT.getTotalCloseStates() + (long) algoMPT.getClosedStatesCount());
            statMPT.setTotalDuration(statMPT.getTotalDuration() + algoMPT.getDurationTime());
            statMPT.setPathLength(statMPT.getPathLength() + pathLengthMPT);

            // Using Manhattan Heuristic-----------------------------
            SlidingPuzzle.setHFunction(new Manhattan());
            GraphSearchAlgorithm algoMAN = new AStar(sp2);
            algoMAN.execute();

            ArrayList<GraphState> solutionsMAN = (ArrayList<GraphState>) algoMAN.getSolutions();
            GraphState solutionMAN = solutionsMAN.get(0);
            LinkedList<GraphState> solutionPathMAN = (LinkedList<GraphState>) solutionMAN.getPath();
            long pathLengthMAN = solutionPathMAN.size();

            statMAN.setN(statMAN.getN() + (long) 1);
            statMAN.setTotalOpenStates(statMAN.getTotalOpenStates() + (long) algoMAN.getOpenSet().size());
            statMAN.setTotalCloseStates(statMAN.getTotalCloseStates() + (long) algoMAN.getClosedStatesCount());
            statMAN.setTotalDuration(statMAN.getTotalDuration() + algoMAN.getDurationTime());
            statMAN.setPathLength(statMAN.getPathLength() + pathLengthMAN);

        }

        // Prints Stats-----------------------------------------------------------------
        System.out.println("Averages for Misplaced Tiles Heuristic:");
        System.out.print("Duration=" + statMPT.durationAVG() + "   ");
        System.out.print("Open States=" + statMPT.openStatesAVG() + "\n");
        System.out.print("Close States=" + statMPT.closeStatesAVG() + "   ");
        System.out.print("Path Length=" + statMPT.pathLengthAVG() + "\n");

        System.out.println();

        System.out.println("Averages for Manhattan Heuristic:");
        System.out.print("Duration=" + statMAN.durationAVG() + "   ");
        System.out.print("Open States=" + statMAN.openStatesAVG() + "\n");
        System.out.print("Close States=" + statMAN.closeStatesAVG() + "   ");
        System.out.print("Path Length=" + statMAN.pathLengthAVG() + "\n");

    }



    public static void main(String[] args) {

        SlidingPuzzle sp = new SlidingPuzzle();
        System.out.println(sp);
//        System.out.println(sp.isSolution());

//        sp.moveRight();

        sp.shuffleBoard();
        System.out.println(sp);
//        System.out.println(sp.isSolution());

//        SlidingPuzzle.setHFunction(new MisplacedTiles());
        SlidingPuzzle.setHFunction(new Manhattan());

//        GraphSearchAlgorithm algo = new BestFirstSearch(sp);
        GraphSearchAlgorithm algo = new AStar(sp);

        algo.execute();

        ArrayList<GraphState> solutions = (ArrayList<GraphState>) algo.getSolutions();
        GraphState solution = solutions.get(0);
        System.out.print("Path: " + solution.getMovesAlongPath());

        System.out.println();

        System.out.println("Time [ms]: " + algo.getDurationTime());
        System.out.println("Closed States: " + algo.getClosedStatesCount());
        System.out.println("Open States(now): " + algo.getOpenSet().size());

    }

}