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
        System.out.println("Temps Astar 1rst : " + (System.nanoTime() - timeStep));

        timeStep = System.nanoTime();
        comparAstar.compare(example1, example2);
        System.out.println("Temps Astar 2nd : " + (System.nanoTime() - timeStep));

        timeStep = System.nanoTime();
        comparDijkstra.compare(example1, example2);
        System.out.println("Temps Djikstra : " + (System.nanoTime() - timeStep));
    }

    @Test
    public void testHeron(){
        System.out.println("Héron : " + example2.getPosition().quickDistanceTo(example1.getPosition()));
        System.out.println("Attendu : " + example2.getPosition().withdrawNewVector(example1.getPosition()).getRay());
    }
}
