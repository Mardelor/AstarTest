package tests;

import Astar.ComparatorNodeDistance;
import Astar.ComparatorNodeHeuristic;
import graph.Node;
import org.junit.Before;
import org.junit.Test;
import smartMath.Vector;

public class JUnit_Comparator {

    /** Comparators */
    private ComparatorNodeHeuristic comparAstar;
    private ComparatorNodeDistance comparDijkstra;

    /** Examples nodes */
    private Node example1;
    private Node example2;

    @Before
    public void setUp(){
        comparAstar = new ComparatorNodeHeuristic();
        comparDijkstra = new ComparatorNodeDistance();
        ComparatorNodeHeuristic.aim = new Vector(0,0);
        example1 = new Node(new Vector(200, 800));
        example2 = new Node(new Vector(300, 900));
    }

    @Test
    public void testTime(){
        long timeStep = System.nanoTime();
        comparAstar.compare(example1, example2);
        System.out.println("Temps Astar : " + (System.nanoTime() - timeStep));

        timeStep = System.nanoTime();
        comparDijkstra.compare(example1, example2);
        System.out.println("Temps Djikstra : " + (System.nanoTime() - timeStep));
    }
}
