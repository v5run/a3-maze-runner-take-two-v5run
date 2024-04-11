package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testGraphConstruction() throws Exception{
        Maze maze = new Maze("./examples/straight.maz.txt");
        Graph graph = new Graph(maze);
        Map<Position, List<Position>> constructedGraph = graph.getGraph();
        
        assertNotNull(constructedGraph);
        assertEquals(maze.getStart(), graph.getStart());
        assertEquals(maze.getEnd(), graph.getEnd());

        for (Position pos : constructedGraph.keySet()) {
            List<Position> neighbors = constructedGraph.get(pos);
            for (Position neighbor : neighbors) {
                assertTrue(neighbor.x() >= 0 && neighbor.x() < maze.getSizeX());
                assertTrue(neighbor.y() >= 0 && neighbor.y() < maze.getSizeY());
                assertFalse(maze.isWall(neighbor));
            }
        }
    }
}
