import Astar.Astar;
import Astar.NoPathFoundException;
import display.Window;
import graph.Graph;
import graph.PointOutOfLandmarkException;
import Astar.TestMode;
import smartMath.Landmark;
import smartMath.Vector;
import java.util.ArrayList;

public class Main {

    /** Variables */
    public static Landmark landmark;
    public static Graph graph;
    public static Astar astar;
    public static Window display;

    /** Méthode principale */
    public static void main(String[] args) throws InterruptedException{
        long timeStep = System.currentTimeMillis();
        landmark = new Landmark(1000,600, TestMode.RANDOM_OBSTACLES);
        graph = new Graph(landmark);
        astar = new Astar(landmark, graph);
        display = new Window(landmark, graph);
        display.printDebug("Time to initialize : " + (System.currentTimeMillis()-timeStep) + " ms");
        ArrayList<Vector> clics;
        ArrayList<Vector> path;

        while (true){
            try {
                display.printDebug("New Path");
                clics = display.waitLRClic();
                timeStep = System.currentTimeMillis();
                path = astar.findPath(clics.get(0), clics.get(1));
                display.printDebug("Time to compute path : " + (System.currentTimeMillis() - timeStep) + " ms, going through " + path.size() + " nodes");
                display.drawPath(path);
                display.removeErrorMess();
            }catch (PointOutOfLandmarkException e){
                display.drawPath(new ArrayList<>());
                display.printError("Out of the Landmark : please clic somewhere else !");
            }catch (NoPathFoundException e){
                display.drawPath(new ArrayList<>());
                display.printError("No Path between " + e.getBegin().toStringOnlyInt() + " and " + e.getEnd().toStringOnlyInt() + " : graph doesn't cover all nodes");
            }
        }
    }
}
