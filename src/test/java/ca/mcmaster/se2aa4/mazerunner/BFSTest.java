package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class BFSTest {
    @Test
    public void testSolve_NullGraph(){
        BFS solver = new BFS();
        assertThrows(NullPointerException.class, () -> solver.solve(null));
    }
    
    @Test
    public void testSolve_StraightPath() throws Exception{
        // Create a sample graph
        Graph graph = new Graph(new Maze("examples/straight.maz.txt"));
        
        // Solve the graph using BFS
        GraphSolver solver = new BFS();
        Path path = solver.solve(graph);
        String stringP = path.getCanonicalForm();
        
        // Assert the shortest path
        assertEquals("FFFF", stringP);
    }
    @Test
    public void testSolve_GiantPath() throws Exception{
        // Create a sample graph
        Graph graph = new Graph(new Maze("examples/giant.maz.txt"));
        
        // Solve the graph using BFS
        GraphSolver solver = new BFS();
        Path path = solver.solve(graph);
        String stringP = path.getCanonicalForm();
        
        // Assert the shortest path
        assertEquals("F L FF R FF L FFFFFF R FF L FFFFFF R FF R FF L FF R FF L FF R FFFFFFFF L FFFF R FFFF L FFFFFF R FF L FFFF R FF L FF R FFFF L FFFF R FF L FFFFFFFFFFFFFFFFFF R FFFF L FFFF R FF L FF R FF L FFFF R FFFF L FF R FF L FF L FF R FFFF L FF R FFFF L FF R FFFFFFFFFF L FFFFFF R FF L FF R FFFFFF L FF R FF R FFFF L FF R FF L FFFFFFFFFFFFFF R FFFF L FFFF R FF L FF R FFFFFFFF L FFFFFFFFFF R FF L FFFF R FF L FFFFFF R FF L FFFF R FF L FFFFFF L FF R FF L FFFF R FFFFF", stringP);
    }
    @Test
    public void testSolve_TinyPath() throws Exception{
        // Create a sample graph
        Graph graph = new Graph(new Maze("examples/tiny.maz.txt"));
        
        // Solve the graph using BFS
        GraphSolver solver = new BFS();
        Path path = solver.solve(graph);
        String stringP = path.getFactorizedForm();
        
        // Assert the shortest path
        assertEquals("3F L 4F R 3F", stringP);
    }
}