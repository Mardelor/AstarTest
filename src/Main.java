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
        display.printDebug("Temps en initialisation : " + (System.currentTimeMillis()-timeStep) + " ms");
        ArrayList<Vector> clics;
        ArrayList<Vector> path;

        while (true){
            try {
                display.printDebug("Nouveau chemin");
                clics = display.waitLRClic();
                timeStep = System.currentTimeMillis();
                path = astar.findPath(clics.get(0), clics.get(1));
                display.printDebug("Temps de calcul : " + (System.currentTimeMillis() - timeStep) + " ms, en passant par " + path.size() + " noeuds");
                display.drawPath(path);
                display.removeErrorMess();
            }catch (PointOutOfLandmarkException e){
                display.printError("Point Hors du landmark : cliquez autre part !");
            }catch (NoPathFoundException e){
                display.printError("Aucun chemin trouver entre " + e.getBegin().toStringOnlyInt() + " et " + e.getEnd().toStringOnlyInt() + " : le graphe n'est pas recouvrant");
            }
        }
    }
}
