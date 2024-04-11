package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Baseline {

    private long startTime, endTime;
    private static final Logger logger = LogManager.getLogger();


    public Baseline(){}

    public void start(long ms){
        this.startTime = ms;
    }
    public void end(long ms){
        this.endTime = ms;
    }

    public Long runtime(){
        long dT = endTime - startTime;
        return dT;
    }

    public Path ExploreMaze(String baselineMethod, Maze maze) throws Exception {
        MazeSolver solver = null;
        switch (baselineMethod) {
            case "righthand" -> {
                logger.debug("Baseline RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Baseline Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            case "bfs" -> {
                logger.debug("BFS algorithm chosen.");
                GraphSolver graphSolver = new BFS(); // causes problem bc GraphSolver != MazeSolver, technical debt
                logger.info("Computing -baseline path");
                start(System.currentTimeMillis());
                Path path = graphSolver.solve(maze.getGraph());
                end(System.currentTimeMillis());
                System.out.println("-baseline algorithm time: " + runtime() + "ms");
                return path;
            }
            default -> {
                throw new Exception("Baseline method '" + baselineMethod + "' not supported.");
            }
        }
        logger.info("Computing -baseline path");
        start(System.currentTimeMillis());
        Path path = solver.solve(maze);
        end(System.currentTimeMillis());
        System.out.println("-baseline algorithm time: " + runtime() + "ms");
        return path;
    }

    public void SpeedUp(Path baseline, Path method) {
        double SpeedUp = (double) baseline.getPathSize() / method.getPathSize();
        String SpeedUpRounded = String.format("%.2f",SpeedUp);
        System.out.println("Speedup: path_baseline/path_method = " + SpeedUpRounded);
    }
}