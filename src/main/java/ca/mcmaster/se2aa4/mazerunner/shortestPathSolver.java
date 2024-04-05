package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class shortestPathSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Maze maze) {
        Path path = new Path();
        Position currentPos = maze.getStart();

        // logic goes here

        return path;
    }
}
