package Astar;

import graph.Node;
import java.util.Comparator;

public class ComparatorNode implements Comparator<Node> {

    /** La seule méthode utile de cette classe... */
    @Override
    public int compare(Node node1, Node node2){
        if (node1.getHeuristic() < node2.getHeuristic()){
            return 1;
        }
        else if(node1.getHeuristic() == node2.getHeuristic()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
