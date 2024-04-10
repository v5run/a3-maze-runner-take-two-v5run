package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static Path pathBaseLine;
    private static Path path;

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            if (cmd.hasOption("baseline")) {
                logger.info("Baseline chosen");
                String filePath = cmd.getOptionValue('i');
                long startTime = System.currentTimeMillis();
                Maze maze = new Maze(filePath);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("Time spent loading Maze: " + elapsedTime + " ms");
                String baselineMethod = cmd.getOptionValue("baseline");
                pathBaseLine = baselineExploreMaze(baselineMethod,maze);
            }
            String filePath = cmd.getOptionValue('i');
            Maze maze = new Maze(filePath);
            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                logger.info("BFS algo chosen");
                String method = cmd.getOptionValue("method", "bfs");
                if (cmd.hasOption("baseline")) {
                    long startTime = System.currentTimeMillis();
                    path = solveMaze(method, maze);
                    long endTime = System.currentTimeMillis();
                    long dT = endTime - startTime;
                    System.out.println("-method algorithm time: " + dT + "ms");
                    System.out.println(path.getFactorizedForm());
                    BaselineSpeedUp();
                } else {
                path = solveMaze(method, maze);
                System.out.println(path.getFactorizedForm());
            } }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }
        logger.info("End of MazeRunner");
    }

    private static void BaselineSpeedUp() {
        double SpeedUp = (double) pathBaseLine.getPathSize() / path.getPathSize();
        String SpeedUpRounded = String.format("%.2f",SpeedUp);
        System.out.println("The speedup is: " + SpeedUpRounded);
    }

    private static Path baselineExploreMaze(String baselineMethod, Maze maze) throws Exception {
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
                long startTime = System.currentTimeMillis();
                Path path = graphSolver.solve(maze.getGraph());
                long endTime = System.currentTimeMillis();
                long dT = endTime - startTime;
                System.out.println("-baseline algorithm time: " + dT + "ms");
                return path;
            }
            default -> {
                throw new Exception("Baseline method '" + baselineMethod + "' not supported.");
            }
        }
        logger.info("Computing -baseline path");
        long startTime = System.currentTimeMillis();
        Path path = solver.solve(maze);
        long endTime = System.currentTimeMillis();
        long dT = endTime - startTime;
        System.out.println("-baseline algorithm time: " + dT + "ms");
        return path;
    }


    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = null;
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            case "bfs" -> {
                logger.debug("BFS algorithm chosen.");
                logger.info("Computing graph path");
                GraphSolver graphSolver = new BFS(); // causes problem bc GraphSolver != MazeSolver, technical debt
                return graphSolver.solve(maze.getGraph());
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");
        return solver.solve(maze);
    }

    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline",true,"Comparison of path computation of two given paths"));

        return options;
    }
}
