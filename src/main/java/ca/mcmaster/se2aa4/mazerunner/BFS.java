package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.Map.*;

public class BFS implements GraphSolver {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Graph map) {
        
        Position start = map.getStart();
        Position end = map.getEnd();
        Map<Position, List<Position>> graph = map.getGraph();

        Queue<Position> queue = new LinkedList<>();
        Map<Position, Integer> distance = new HashMap<>();
        Map<Position, Position> previous = new HashMap<>();

        for (Position node : graph.keySet()){
            logger.info(node.x() + " " + node.y());
            distance.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        logger.info("START x: " + start.x() + " y: " + start.y());
        distance.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current == end) {
                break; // Found the shortest path to the end node
            }
            for (Position neighbor : graph.get(current)) {
                if (distance.get(neighbor) == Integer.MAX_VALUE) {
                    distance.put(neighbor, distance.get(current)+1);
                    //logger.info("x: " + neighbor.x() + " y: " + neighbor.y());
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        logger.info("END x: " + end.x() + " y: " + end.y());
        // rn previous has the shortest path to the end in nodes, so convert into canon.
        Path shortestPath = findShortestPath(previous, start, end); // DOES NOT ACCOUNT FOR STARTING POSITION BC ONLY EVER ADDS NEIGHBOURS
        //logger.info("completed solve");
        return shortestPath;
    }

    private Path findShortestPath(Map<Position, Position> pathway, Position start, Position end){

        Path steps = new Path();
        // get rid of redundant nodes, only want the shortest path from the end
        List<Position> simplifiedPath = new ArrayList<>();
        Position current = end;
        while (current!=null){
            simplifiedPath.add(current);
            current = pathway.get(current);
        }
        Collections.reverse(simplifiedPath);
        for (Position i : simplifiedPath){
            logger.info("x: " + i.x() + " y: " + i.y());
        }
        
        steps.addStep('F');
        return steps;
    }
}