package Astar;

import graph.Graph;
import graph.Node;
import graph.PointOutOfLandmarkException;
import graph.Ridge;
import smartMath.Landmark;
import smartMath.MathLib;
import smartMath.Vector;

import javax.swing.text.Segment;
import java.util.*;

/** La classe qui contient l'Astar.Astar */
public class Astar {

    /** Le repère */
    private Landmark landmark;

    /** Le graphe */
    private Graph graph;

    /** Liste ouverte */
    private PriorityQueue<Node> openList;

    /** Liste fermée */
    private ArrayList<Node> closedList;

    /** Noeuds spéciaux ! */
    private Node beginNode;
    private Node aimNode;

    /** Constructeur du bosseur !
     */
    public Astar(Landmark landmark, Graph graph){
        this.landmark = landmark;
        this.graph = graph;

        openList = new PriorityQueue<>(this.graph.getNodeList().size(), new ComparatorNode());
        closedList = new ArrayList<>();
    }

    /** A* */
    public ArrayList<Vector> findPath(Vector begin, Vector aim) throws PointOutOfLandmarkException, NoPathFoundException {

        if(beginNode != null) {
            graph.deleteNode(beginNode);
            graph.deleteNode(aimNode);
            closedList.clear();
            openList.clear();
        }

        int costFromBegin;

        // On tente toujours la ligne droite
        if(!landmark.intersectAnyObstacles(begin, aim)){
            ArrayList<Vector> path = new ArrayList<>();
            path.add(begin);
            path.add(aim);
            return path;
        }

        // Puis on intialise la pathfinding (la partie qui prend du temps)
        initPathfinder(begin, aim);

        while (!openList.isEmpty())
        {
            Node visited = openList.poll();
            if(visited.equals(aimNode)){
                return reconstructPath();
            }

            for(Ridge neighborRidge : visited.getRidgeList()){
                Node neighbor = neighborRidge.getSecondNode();
                costFromBegin = visited.getCostFromBegin() + neighborRidge.getSingleCost();
                // Si l'on a déjà évalué ce noeud et que visited est un meilleur prédecesseur, on l'update !
                if ((openList.contains(neighbor) || closedList.contains(neighbor)) && costFromBegin < neighbor.getCostFromBegin()){
                    neighbor.setCostFromBegin(costFromBegin);
                    neighbor.setPredecessor(visited);
                }
                // Si c'est un noeud que l'on a jamais visité, on le visite !
                else if(!(openList.contains(neighbor) || closedList.contains(neighbor))){
                    neighbor.setCostFromBegin(costFromBegin);
                    neighbor.setPredecessor(visited);
                    openList.add(neighbor);
                }
            }
            closedList.add(visited);
        }
        throw new NoPathFoundException(begin, aim);
    }

    /** Met à jour le graphe et l'heuristique de chaque noeud
     * @param begin
     * @param aim
     */
    private void initPathfinder(Vector begin, Vector aim) throws PointOutOfLandmarkException {
        if(landmark.isInObstacle(begin) || !landmark.isInLandmark(begin)){
            throw new PointOutOfLandmarkException(begin);
        }
        if(landmark.isInObstacle(aim) || !landmark.isInLandmark(aim)){
            throw new PointOutOfLandmarkException(aim);
        }
        beginNode = new Node(begin);
        aimNode = new Node(aim);
        graph.addNode(beginNode);
        graph.addNode(aimNode);
        graph.initHeuristic(aim);
        openList.add(beginNode);
    }

    /** Reconstruit le chemin */
    private ArrayList<Vector> reconstructPath(){
        ArrayList<Vector> toReturn = new ArrayList<Vector>();
        Node visited = aimNode;
        toReturn.add(aimNode.getPosition());

        while (!(visited.getPredecessor().equals(beginNode))){
            toReturn.add(0,visited.getPredecessor().getPosition());
            visited = visited.getPredecessor();
        }
        toReturn.add(0,beginNode.getPosition());
        return toReturn;
    }
}