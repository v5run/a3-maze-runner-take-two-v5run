package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.Map.*;

public class BFS implements GraphSolver {
    private static final Logger logger = LogManager.getLogger();
    private Direction dir = Direction.RIGHT; // assume intially facing right
    private Queue<Position> queue = new LinkedList<>();
    private Map<Position, Boolean> visited = new HashMap<>();
    private Map<Position, Position> previous = new HashMap<>();

    @Override
    public Path solve(Graph map) {
        
        Position start = map.getStart();
        Position end = map.getEnd();
        Map<Position, List<Position>> graph = map.getGraph();

        for (Position node : graph.keySet()){
            visited.put(node, false);
            previous.put(node, null);
        }
        visited.put(start, true);
        queue.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current == end) {
                break; // Found the shortest path to the end node
            }
            for (Position neighbor : graph.get(current)) {
                if (!visited.get(neighbor)) { // if not visited, then mark visited
                    visited.put(neighbor, true);
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        Path shortestPath = findShortestPath(previous, start, end);
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
        Position prev = simplifiedPath.get(0);
        if (start.x() != 0){
            this.dir = Direction.LEFT;
        }

        for (int i = 1; i < simplifiedPath.size(); i++){
            Position next = simplifiedPath.get(i);
            Direction nextDir = getDirection(prev, next);

            char nextMove = getTurn(dir, nextDir);
            if (nextMove != 'F'){
                steps.addStep(nextMove); 
            }
            steps.addStep('F');
            dir = nextDir;
            prev = next;
        }
        return steps;
    }

    private Direction getDirection(Position prev, Position next) {
        if (next.x() == prev.x()) {
            if (next.y() == prev.y() - 1){
                return Direction.UP;
            }
            if (next.y() == prev.y() + 1){
                return Direction.DOWN;
            }
        } else if (next.y() == prev.y()) {
            if (next.x() == prev.x() - 1){
                return Direction.LEFT; 
            }
            else if (next.x() == prev.x() + 1){
                return Direction.RIGHT;
            }
        }
        throw new IllegalArgumentException("Invalid positions: " + prev + ", " + next);
    }
    
    private char getTurn(Direction currentDir, Direction nextDir) {
        if (currentDir == nextDir) {
            return 'F';
        } else if (currentDir.turnRight() == nextDir) {
            return 'R';
        } else if (currentDir.turnLeft() == nextDir) {
            return 'L';
        }
        throw new IllegalArgumentException("Invalid directions: " + currentDir + ", " + nextDir);
    }
}