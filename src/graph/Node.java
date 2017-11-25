package graph;

import smartMath.Vector;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/** Classe implémentant le concept de noeuds dans un graphe */
public class Node {

    /** Position du noeud */
    private Vector position;

    /** Heuristique */
    private int heuristic;
    public static int initialHeuristc;

    /** Prédecesseur */
    private Node predecessor = null;
    private int costFromBegin;

    /** Liste des aretes */
    private CopyOnWriteArrayList<Ridge> ridgeList;

    /** Constructeur de base
     * @param position
     */
    public Node(Vector position){
        this.position = position;
        this.heuristic = initialHeuristc;
        ridgeList = new CopyOnWriteArrayList<>();
    }

    /** Renvoie la distance entre deux noeuds
     * @param other
     */
    public int distanceTo(Node other){
        return (int)(this.position.withdrawNewVector(other.position).getRay());
    }

    /** Créer une arrête entre deux noeuds, avec un coût supplémentaire pour chaque arrête
     * @param other
     */
    public void createLink(Node other){
        ridgeList.add(new Ridge(this, other));
        other.getRidgeList().add(new Ridge(other, this));
    }

    /** Getters */
    public Vector getPosition() {
        return position;
    }
    public CopyOnWriteArrayList<Ridge> getRidgeList() {
        return ridgeList;
    }
    public int getHeuristic() {
        return heuristic;
    }
    public Node getPredecessor() {
        return predecessor;
    }
    public int getCostFromBegin() {
        return costFromBegin;
    }

    /** Setters */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }
    public void setCostFromBegin(int costFromBegin) {
        this.costFromBegin = costFromBegin;
    }
}
