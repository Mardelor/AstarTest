package Astar;

import graph.Graph;
import graph.Node;
import graph.PointOutOfLandmarkException;
import graph.Ridge;
import smartMath.Landmark;
import smartMath.Vector;

import java.util.*;

/** La classe qui contient l'Astar.Astar */
public class Astar extends Dijkstra {

    /** Liste fermée */
    private ArrayList<Node> closedList;

    /** Constructeur du bosseur !
     */
    public Astar(Landmark landmark, Graph graph){
        super(landmark, graph);

        openList = new PriorityQueue<>(new ComparatorNodeSimpleHeuristic());
        closedList = new ArrayList<>();
    }

    /** A* */
    @Override
    public ArrayList<Vector> findPath(Vector begin, Vector aim) throws PointOutOfLandmarkException, NoPathFoundException {

        // On commence par mettre à jour le graphe vis-à-vis du dernier appel
        closedList.clear();

        // On tente toujours la ligne droite
        if(!landmark.intersectAnyObstacles(begin, aim)){
            ArrayList<Vector> path = new ArrayList<>();
            path.add(begin);
            path.add(aim);
            return path;
        }

        // Puis on intialise la pathfinding (la partie qui prend du temps)
        initPathfinder(begin, aim);
        int currentCost;

        // Astar en lui-même
        while (!openList.isEmpty())
        {
            Node visited = openList.poll();

            if(visited.equals(aimNode)){
                return reconstructPath();
            }

            for(Ridge neighborRidge : visited.getRidgeList()){
                Node neighbor = neighborRidge.getSecondNode();
                currentCost = visited.getCostFromBegin() + neighborRidge.getSingleCost();
                // Si l'on a déjà évalué ce noeud et que visited est un meilleur prédecesseur, on l'update !
                if ((openList.contains(neighbor) || closedList.contains(neighbor)) && currentCost < neighbor.getCostFromBegin()){
                    neighbor.setCostFromBegin(currentCost);
                    neighbor.setPredecessor(visited);
                    if(closedList.contains(neighbor)){
                        closedList.remove(neighbor);
                        openList.add(neighbor);
                    }
                }
                // Si c'est un noeud que l'on a jamais visité, on le visite !
                else if(!(openList.contains(neighbor) || closedList.contains(neighbor))){
                    neighbor.setCostFromBegin(currentCost);
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
    @Override
    protected void initPathfinder(Vector begin, Vector aim) throws PointOutOfLandmarkException {
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
        graph.reinitGraph();

        ComparatorNodeSimpleHeuristic.aim = aim;
        openList.add(beginNode);
    }
}