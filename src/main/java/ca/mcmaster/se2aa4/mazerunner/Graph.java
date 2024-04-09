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
                    // fix all this!!! NODE HAS A NEIGHBOR METHOD!!!
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
        Position currentPos = new Position(x, y);
        if (x>0){
            currentPos = new Position(x - 1, y); // Left
            if (!maze.isWall(currentPos)) {
                neighbors.add(new Node(x - 1, y));
            }
        }
        if (x < maze.getSizeX()-1){
            currentPos = new Position(x + 1, y);  // Right
            if (!maze.isWall(currentPos)) {
                neighbors.add(new Node(x + 1, y));
            }
        }
        if (y>0){
            currentPos = new Position(x, y - 1);  // Up
            if (!maze.isWall(currentPos)) {
                neighbors.add(new Node(x, y - 1));
            }
        }
        if (y < maze.getSizeY()-1){
            currentPos = new Position(x, y + 1); // Down
            if (!maze.isWall(currentPos)) {
                neighbors.add(new Node(x, y + 1));
            }
        }
        System.out.println(x);
        System.out.println(y);

        for (Node i : neighbors) {
            System.out.println(i.getX());
            System.out.println(i.getY());
            System.out.println();
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
