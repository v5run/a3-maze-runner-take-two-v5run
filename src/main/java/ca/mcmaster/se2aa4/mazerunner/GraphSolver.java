package ca.mcmaster.se2aa4.mazerunner;

public interface GraphSolver {
    /**
     * Solve maze and return path through maze.
     *
     * @param graph Maze to solve
     * @return Path that solves the provided maze
     */
    Path solve(Graph graph);
}
