package graph;

/** Classe implémentant le concept d'arrete
 */
public class Ridge {

    /** Noeuds qui forment l'arete */
    private Node firstNode;
    private Node secondNode;

    /** Coût d'une arrête */
    private int singleCost;

    /** Coût statique d'une arrête */
    public static int staticCost = 0;

    /** Constructeur d'arete : par défaut, le coût est égale à la distance euclidienne au carré
     * @param firstNode
     * @param secondNode
     */
    public Ridge(Node firstNode, Node secondNode){
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.singleCost = firstNode.distanceTo(secondNode) + staticCost;
    }

    /** Constructeur d'une arete avec cout personnalisable
     * @param firstNode
     * @param secondNode
     * @param cost
     */
    public Ridge(Node firstNode, Node secondNode, int cost){
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.singleCost = cost;
    }

    /** Si deux arrêtes ont les même noeuds d'extremité, PEU IMPORTE L'ORDRE, c'est les mêmes */
    @Override
    public boolean equals(Object other){
        if(other instanceof Ridge) {
            if ((firstNode.equals(((Ridge) other).firstNode) && secondNode.equals(((Ridge) other).secondNode))
                    || (firstNode.equals(((Ridge) other).secondNode) && secondNode.equals(((Ridge) other).firstNode))) {
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    /** Getters */
    public Node getFirstNode() {
        return firstNode;
    }
    public Node getSecondNode() {
        return secondNode;
    }
    public int getSingleCost() {
        return singleCost;
    }
}
