import algorithms.Astar;
import algorithms.Dijkstra;
import algorithms.NoPathFoundException;
import display.ThreadInterface;
import display.Window;
import graph.Graph;
import graph.PointOutOfLandmarkException;
import algorithms.TestMode;
import landmark.DynamicObjectHandler;
import landmark.Follower;
import landmark.Landmark;
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
    public static DynamicObjectHandler dynamicObjectHandler;
    public static ThreadInterface gInterface;

    /** Méthode principale */
    public static void main(String[] args) throws InterruptedException{
        double timeStep = System.currentTimeMillis();
        landmark = new Landmark(1400,700, TestMode.RANDOM_OBSTACLES);
        graph = new Graph(landmark);
        astar = new Astar(landmark, graph);
        dijkstra = new Dijkstra(landmark, graph);
        gInterface = new ThreadInterface(landmark, graph);
        double initialisationTime = System.currentTimeMillis() - timeStep;

        display = gInterface.getWindow();
        dynamicObjectHandler = new DynamicObjectHandler(landmark);

        gInterface.start();
        dynamicObjectHandler.start();

        display.printDebug("Time to initialize : " + initialisationTime + " ms,");
        display.printDebug("Creating " + graph.getNodeList().size() + " Nodes, and " + graph.getRidgeList().size() + " Ridges");
        display.printDebug("To find a path, please clic on the Landmark : LEFT for the start, RIGHT for the aim");
        Vector clics;
        ArrayList<Vector> pathAstar;
        ArrayList<Vector> pathDijkstra;
        ArrayList<Vector> basicPath = new ArrayList<>();
        basicPath.add(new Vector(0,0));
        dynamicObjectHandler.createFollower(basicPath);

        while (true){
            try {
                display.printDebug("");
                display.printDebug("New Path");
                clics = display.waitLClic();

                timeStep = System.nanoTime();
                pathAstar = astar.findPath(dynamicObjectHandler.getFollower().getCenter(), clics);
                display.printDebug("Astar Time to compute path : " + getTimeDuration(timeStep) + " ms, going through " + pathAstar.size() + " nodes,");
                display.printDebug("for a distance of " + getDistanceFromPath(pathAstar));
                dynamicObjectHandler.createFollower(pathAstar);
                display.drawPath(pathAstar, Color.PINK);

                timeStep = System.nanoTime();
                pathDijkstra = dijkstra.findPath((Vector)dynamicObjectHandler.getFollower().getCenter().clone(), clics);
                display.printDebug("Dijkstra Time to compute path : " + getTimeDuration(timeStep) + " ms, going through " + pathDijkstra.size() + " nodes,");
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

    /** Méthode qui retourne le temps écoulé entre start et le temps actuel en ms avec 2 chiffres après la virgule */
    public static String getTimeDuration(double start){
        double current = System.nanoTime();
        if(start-current < 10000000) {
            return new Double((current - start) / 1000000.0).toString().substring(0,4);
        }
        return new Double((current - start) / 1000000.0).toString().substring(0,5);
    }
}
