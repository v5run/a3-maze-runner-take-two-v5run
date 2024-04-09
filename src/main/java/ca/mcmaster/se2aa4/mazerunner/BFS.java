package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BFS implements GraphSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Graph graph) {

        Node start = graph.getStart();
        Node end = graph.getEnd();

        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();

        // try to fix getGraph ?
        for (Node node : graph.getGraph().keySet()) {
            distance.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }

        distance.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == end) {
                break; // Found the shortest path to the end node
            }

            for (Node neighbor : graph.getGraph().get(current)) {
                int newDistance = distance.get(current) + 1; 
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        // rn previous has the shortest path to the end in nodes, so convert into canon.
        Path shortestPath = findShortestPath(previous, start, end); // fix this later
        return shortestPath;
    }

    private Path findShortestPath(Map<Node, Node> pathway, Node start, Node end){
        Path steps = new Path(); // take all nodes from pathway and retrace the node pathway. Add each step to steps
                                 // does not need to be factored form
        return steps;
    }
}