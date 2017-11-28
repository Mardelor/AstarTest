import Astar.Astar;
import Astar.Dijkstra;
import Astar.NoPathFoundException;
import display.Window;
import graph.Graph;
import graph.PointOutOfLandmarkException;
import Astar.TestMode;
import smartMath.Landmark;
import smartMath.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    /** Variables */
    public static Landmark landmark;
    public static Graph graph;
    public static Astar astar;
    public static Dijkstra dijkstra;
    public static Window display;

    /** Méthode principale */
    public static void main(String[] args) throws InterruptedException{
        long timeStep = System.currentTimeMillis();
        landmark = new Landmark(1000,600, TestMode.RANDOM_OBSTACLES);
        graph = new Graph(landmark);
        astar = new Astar(landmark, graph);
        dijkstra = new Dijkstra(landmark, graph);
        display = new Window(landmark, graph);
        display.printDebug("Time to initialize : " + (System.currentTimeMillis()-timeStep) + " ms,");
        display.printDebug("Creating " + graph.getNodeList().size() + " Nodes, and " + graph.getRidgeList().size() + " Ridges");
        display.printDebug("To find a path, please clic on the Landmark : LEFT for the start, RIGHT for the aim");
        ArrayList<Vector> clics;
        ArrayList<Vector> pathAstar;
        ArrayList<Vector> pathDijkstra;

        while (true){
            try {
                display.printDebug("");
                display.printDebug("New Path");
                clics = display.waitLRClic();

                timeStep = System.currentTimeMillis();
                pathAstar = astar.findPath(clics.get(0), clics.get(1));
                display.printDebug("Astar Time to compute path : " + (System.currentTimeMillis() - timeStep) + " ms, going through " + pathAstar.size() + " nodes,");
                display.printDebug("for a distance of " + getDistanceFromPath(pathAstar));
                display.drawPath(pathAstar, Color.PINK);

                timeStep = System.currentTimeMillis();
                pathDijkstra = dijkstra.findPath(clics.get(0), clics.get(1));
                display.printDebug("Dijkstra Time to compute path : " + (System.currentTimeMillis() - timeStep) + " ms, going through " + pathDijkstra.size() + " nodes,");
                display.printDebug("for a distance of " + getDistanceFromPath(pathDijkstra));
                display.drawPath(pathDijkstra, Color.GREEN);

                display.removeErrorMess();

            }catch (PointOutOfLandmarkException e){
                display.resetPath();
                display.printError("Out of the Landmark : please clic somewhere else !");
            }catch (NoPathFoundException e){
                display.resetPath();
                display.printError("No Path between " + e.getBegin().toStringOnlyInt() + " and " + e.getEnd().toStringOnlyInt() + " : graph doesn't cover all nodes");
            }
        }
    }

    /** Méthode qui calcule la distance d'un chemin */
    public static int getDistanceFromPath(ArrayList<Vector> path){
        int cost = 0;
        for(int i=0; i<path.size()-1; i++){
            cost += (int)path.get(i).withdrawNewVector(path.get(i+1)).getRay();
        }
        return cost;
    }
}
