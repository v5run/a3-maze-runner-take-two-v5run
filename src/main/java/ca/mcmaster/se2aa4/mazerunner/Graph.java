package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Graph {

    private final Map<Node, List<Node>> graph = new HashMap<>();
    private final Maze maze;
    private Node start;
    private Node end;
    

    public Graph(Maze maze) {
        this.maze = maze;
        constructGraph();
    }

    private void constructGraph() {
        for (int i = 0; i < maze.getSizeX(); i++) {
            for (int j = 0; j < maze.getSizeY(); j++) {
                Position currentPos = new Position(i, j);
                if (!maze.isWall(currentPos)) {
                    Node newNode = new Node(i, j);
                    List<Node> neighbors = findNeighbors(newNode);
                    graph.put(newNode, neighbors);

                    if (i == maze.getStart().x() && j == maze.getStart().y()){
                        start = newNode;
                    }
                    else if (i == maze.getEnd().x() && j == maze.getEnd().y()){
                        end = newNode;
                    }
                }
            }
        }
    }

    public Map<Node, List<Node>> getGraph() {
        return graph;
    }

    private List<Node> findNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();
        Position currentPos = new Position(x - 1, y); // Left
        if (!maze.isWall(currentPos)) {
            neighbors.add(new Node(x - 1, y));
        }
        currentPos = new Position(x + 1, y);  // Right
        if (!maze.isWall(currentPos)) {
            neighbors.add(new Node(x + 1, y));
        }
        currentPos = new Position(x, y - 1);  // Up
        if (!maze.isWall(currentPos)) {
            neighbors.add(new Node(x, y - 1));
        }
        currentPos = new Position(x, y + 1); // Down
        if (!maze.isWall(currentPos)) {
            neighbors.add(new Node(x, y + 1));
        }
        return neighbors;
    }

    public Node getStart(){
        return start;
    }

    public Node getEnd(){
        return end;
    }
}
