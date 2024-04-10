package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Graph {

    private final Map<Position, List<Position>> graph = new HashMap<>();
    private final Maze maze;
    private Position start;
    private Position end;
    private static final Logger logger = LogManager.getLogger();
    

    public Graph(Maze maze) {
        this.maze = maze;
        constructGraph();
    }

    private void constructGraph() {
        logger.info("CONSTRUCTING GRAPH");
        List<Position> neighbors = new ArrayList<>();
        for (int i = 0; i < maze.getSizeX(); i++) {
            for (int j = 0; j < maze.getSizeY(); j++) {
                Position currentPos = new Position(i, j);
                if (!maze.isWall(currentPos)) {
                    neighbors = findNeighbors(currentPos);
                    graph.put(currentPos, neighbors);

                    if (i == maze.getStart().x() && j == maze.getStart().y()){
                        this.start = currentPos;
                    }
                    else if (i == maze.getEnd().x() && j == maze.getEnd().y()){
                        this.end = currentPos;
                    }
                }
            }
        }
    }

    public Map<Position, List<Position>> getGraph() {
        return graph;
    }

    private List<Position> findNeighbors(Position node) {
        List<Position> neighbors = new ArrayList<>();
        int x = node.x();
        int y = node.y();
        
        if (x>0){
            Position leftPos = new Position(x - 1, y); // Left
            if (!maze.isWall(leftPos)) {
                neighbors.add(leftPos);
            }
        }
        if (x < maze.getSizeX()-1){
            Position rightPos = new Position(x + 1, y);  // Right
            if (!maze.isWall(rightPos)) {
                neighbors.add(rightPos);
            }
        }
        if (y>0){
            Position upPos = new Position(x, y - 1);  // Up
            if (!maze.isWall(upPos)) {
                neighbors.add(upPos);
            }
        }
        if (y < maze.getSizeY()-1){
            Position downPos = new Position(x, y + 1); // Down
            if (!maze.isWall(downPos)) {
                neighbors.add(downPos);
            }
        }
        return neighbors;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }
}
