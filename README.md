# Sliding Puzzle Solver

This project is an implementation of a solver for the classic Sliding Puzzle game using the Java programming language. The solver employs two different heuristics, Misplaced Tiles and Manhattan Distance, to find the shortest path to solve the puzzle from a scrambled initial state. Additionally, the project utilizes the SAC (Search Algorithms Collection) library for the graph search algorithms.

## Project Files

### SlidingPuzzle.java

This file contains the main class `SlidingPuzzle`, which represents the puzzle state and handles puzzle logic. It includes methods for shuffling the puzzle, performing legal moves, checking bounds, generating children states, and more. The `main` method in this file is used to run the solver with various puzzles and heuristics, collecting statistics about the search.

### Manhattan.java

`Manhattan.java` is a state function class for calculating the Manhattan distance heuristic. It extends the SAC `StateFunction` class and overrides the `calculate` method to compute the heuristic value for a given puzzle state based on the sum of distances between tiles and their goal positions.

### MisplacedTiles.java

`MisplacedTiles.java` is another state function class, but it calculates the Misplaced Tiles heuristic. Similar to `Manhattan.java`, it extends the SAC `StateFunction` class and calculates the heuristic value by counting the number of tiles that are not in their goal positions.

### Stats.java

`Stats.java` contains the `Stats` class used to collect and calculate statistics about the solver's performance, including the average duration, open states, closed states, and path length. This class is employed in the `main` method of `SlidingPuzzle.java` to print the statistics.

## SAC Library

This project utilizes the SAC (Search Algorithms Collection) library for implementing graph search algorithms. The SAC library offers a collection of classes and functions for various search algorithms, making it easier to implement and experiment with different search strategies. You can find more information about the SAC library and its source code on its GitHub page:

[SAC GitHub Repository](https://github.com/pklesk/sac)

## How to Run

To run the Sliding Puzzle solver, you can execute the `main` method in the `SlidingPuzzle.java` file. This will initiate the solver with different puzzles and heuristics, collecting and displaying statistics for each run.

## Acknowledgments

- SAC Library: [SAC on GitHub](https://github.com/pklesk/sac)

Please note that this README provides an overview of the project structure and its usage. For detailed code implementation and algorithm explanations, refer to the individual Java files in the project directory.

Feel free to reach out if you have any questions or need further assistance with this project.
