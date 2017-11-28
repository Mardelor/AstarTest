package Astar;

import graph.Graph;
import graph.Node;
import graph.PointOutOfLandmarkException;
import graph.Ridge;
import smartMath.Landmark;
import smartMath.Vector;
import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.PriorityQueue;

/** La classe qui contient le Djikstra, afin de le comparer au A* */
public class Dijkstra {

    /** Le repère */
    protected Landmark landmark;

    /** Le graphe */
    protected Graph graph;

    /** Liste ouverte */
    protected PriorityQueue<Node> openList;

    /** Noeuds spéciaux ! */
    protected Node beginNode;
    protected Node aimNode;

    /** Constructeur du bosseur !
     */
    public Dijkstra(Landmark landmark, Graph graph){
        this.landmark = landmark;
        this.graph = graph;

        openList = new PriorityQueue<>(new ComparatorNodeDistance());
    }

    /** Dijkstra */
    public ArrayList<Vector> findPath(Vector begin, Vector aim) throws PointOutOfLandmarkException, NoPathFoundException {

        // On tente toujours la ligne droite
        if(!landmark.intersectAnyObstacles(begin, aim)){
            ArrayList<Vector> path = new ArrayList<>();
            path.add(begin);
            path.add(aim);
            return path;
        }

        // Puis on intialise la pathfinding (la partie qui prend du temps)
        initPathfinder(begin, aim);

        // Dijkstra
        while (!openList.isEmpty()){
            long timeStep = System.nanoTime();
            Node visited = openList.poll();

            for(Ridge neighborRidge : visited.getRidgeList()){
                Node neighbor = neighborRidge.getSecondNode();
                int currentCost = visited.getCostFromBegin() + neighborRidge.getSingleCost();
                if(currentCost < neighbor.getCostFromBegin()){
                    openList.remove(neighbor);
                    neighbor.setCostFromBegin(currentCost);
                    neighbor.setPredecessor(visited);
                    openList.add(neighbor);
                }
            }
        }

        if(aimNode.getPredecessor() == null){
            throw new NoPathFoundException(begin, aim);
        }

        return reconstructPath();
    }

    /** Met à jour le graphe et ajoute les premiers noeuds
     * @param begin
     * @param aim
     */
    protected void initPathfinder(Vector begin, Vector aim) throws PointOutOfLandmarkException {
        if(landmark.isInObstacle(begin) || !landmark.isInLandmark(begin)){
            throw new PointOutOfLandmarkException(begin);
        }
        if(landmark.isInObstacle(aim) || !landmark.isInLandmark(aim)){
            throw new PointOutOfLandmarkException(aim);
        }
        beginNode = new Node(begin);
        aimNode = new Node(aim);

        graph.addNode(aimNode);
        graph.addNode(beginNode);
        graph.reinitGraph();

        beginNode.setCostFromBegin(0);
        openList.addAll(graph.getNodeList());
    }

    /** Reconstruit le chemin */
    protected ArrayList<Vector> reconstructPath(){
        ArrayList<Vector> toReturn = new ArrayList<Vector>();
        Node visited = aimNode;
        toReturn.add(aimNode.getPosition());

        while (!(visited.getPredecessor().equals(beginNode))){
            toReturn.add(0,visited.getPredecessor().getPosition());
            visited = visited.getPredecessor();
        }
        toReturn.add(0,beginNode.getPosition());
        graph.deleteNode(beginNode);
        graph.deleteNode(aimNode);
        openList.clear();
        return toReturn;
    }
}
