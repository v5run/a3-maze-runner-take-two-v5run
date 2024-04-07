package ca.mcmaster.se2aa4.mazerunner;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFS implements GraphSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Graph graph) {
        Path path = new Path();

        // grab start and end nodes
        Node start = graph.start();
        Node end = graph.end();

        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        
        for (Node n : graph.keyset()) {
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

            for (Node neighbor : current.getNeighbors()) {
                int newDistance = distance.get(current) + 1; // Assuming all edges have weight 1
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;

    }
}
