package Astar;

import graph.Node;
import smartMath.Vector;
import java.util.Comparator;

public class ComparatorNode implements Comparator<Node> {

    /** Position visée */
    public static Vector aim;

    /** La seule méthode utile de cette classe... */
    @Override
    public int compare(Node node1, Node node2){
        if (computeHeuristic(node1) < computeHeuristic(node2)){
            return 1;
        }
        else if(computeHeuristic(node1) == computeHeuristic(node2)){
            return 0;
        }
        else{
            return -1;
        }
    }

    /** Calcul l'heuristique */
    private int computeHeuristic(Node node){
        if(node.getHeuristic() == -1 && !node.getPosition().equals(aim)){
            node.setHeuristic(node.getPosition().withdrawNewVector(aim).squaredLength());
        }
        return node.getHeuristic();
    }
}
