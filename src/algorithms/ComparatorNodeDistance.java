package algorithms;

import graph.Node;
import java.util.Comparator;

public class ComparatorNodeDistance implements Comparator<Node>{

    /** La seule méthode utile de cette classe... */
    @Override
    public int compare(Node node1, Node node2){
        if (node1.getCostFromBegin() > node2.getCostFromBegin()){
            return 1;
        }
        else if(node1.getCostFromBegin() == node2.getCostFromBegin()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
