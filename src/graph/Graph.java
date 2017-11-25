package graph;

import smartMath.Circle;
import smartMath.Landmark;
import smartMath.Vector;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Graph {

    /** Liste des noeuds */
    private CopyOnWriteArrayList<Node> nodeList;
    private ArrayList<Node> staticNodes;

    /** Liste des obstacles */
    private ArrayList<Circle> obstacleList;

    /** Repère */
    private Landmark landmark;

    /** Constructeur du graphe
     * @param landmark
     */
    public Graph(Landmark landmark){
        this.landmark = landmark;
        this.obstacleList = landmark.getListObst();
        this.nodeList = new CopyOnWriteArrayList<>();
        this.staticNodes = new ArrayList<>();
        this.initGraph();
    }

    /************************
     * INITIALISATION GRAPH *
     ************************/

    /** Initialise le graphe : place les noeuds en fonction des obstacles & les relies avec des arrêtes */
    private void initGraph(){
        Ridge.staticCost = 50;
        initNodes();
        initRidges();
    }

    /** Initialise l'heuristique pour un but donné
     * @param aim
     */
    public void initHeuristic(Vector aim){
        for(Node node : nodeList){
            node.setHeuristic(aim.withdrawNewVector(node.getPosition()).squaredLength());
        }
    }

    /** Placement des noeuds */
    private void initNodes(){
        for(Circle circle : obstacleList){
            placeNodes(circle);
        }
        staticNodes.add(new Node(landmark.getUpLeft().addNewVector(new Vector(20, -20))));
        staticNodes.add(new Node(landmark.getUpRight().addNewVector(new Vector(-20, -20))));
        staticNodes.add(new Node(landmark.getDownLeft().addNewVector(new Vector(20, 20))));
        staticNodes.add(new Node(landmark.getDownRight().addNewVector(new Vector(-20, 20))));
        staticNodes.add(new Node(new Vector(0,0)));

        for(Node node : staticNodes){
            if(!landmark.isInObstacle(node.getPosition())){
                nodeList.add(node);
            }
        }
    }

    /** Placement des arrêtes */
    private void initRidges(){
        Node node1;
        Node node2;
        for(int i=0; i<nodeList.size()-1; i++){
            node1 = nodeList.get(i);
            for(int j=i+1; j<nodeList.size(); j++){
                node2 = nodeList.get(j);
                if(!landmark.intersectAnyObstacles(node1.getPosition(), node2.getPosition())){
                    node1.createLink(node2);
                }
            }
        }
    }

    /************************
     * MISE A JOUR DU GRAPH *
     ************************/

    /** Mise à jour du graph (un peu bourin) */
    public void update(){
        this.obstacleList = landmark.getListObst();
        this.nodeList.clear();
        initNodes();
        initRidges();
    }

    /*********
     * UTILS *
     *********/

    /** Place les noeuds autour d'un obstacle
     * @param circle */
    private ArrayList<Node> placeNodes(Circle circle){
        Vector posCenter = circle.getCenter();
        ArrayList<Node> addNodes = new ArrayList<>();
        int nbNodes = circle.getRay()/7;
        for(int i=-nbNodes/2; i<=nbNodes/2; i++){
            Vector posNode = new Vector(1.3*circle.getRay(), i*2*Math.PI/nbNodes);
            posNode.addVector(posCenter);

            if(landmark.isInLandmark(posNode) && !landmark.isInObstacle(posNode)){
                Node addOne = new Node(posNode);
                nodeList.add(addOne);
                addNodes.add(addOne);
            }
        }
        return addNodes;
    }

    /** Ajoute un noeud et place les arrêtes sur ces noeuds */
    public void addNode(Node node){
        if(!landmark.isInLandmark(node.getPosition()) || landmark.isInObstacle(node.getPosition())){
            System.out.println("Position dans un obstacle ou hors la table...");
        }else{
            nodeList.add(node);
            replaceRidges(node);
        }
    }

    /** Enlève un noeud et ses arrêtes */
    public void deleteNode(Node node){
        for(Ridge ridge : node.getRidgeList()){
            ridge.getSecondNode().getRidgeList().remove(ridge);
        }
        nodeList.remove(node);
    }

    /** Replace les arrêtes à partir d'un nouveau noeud */
    private void replaceRidges(Node newNode){
        for(int i=0; i<nodeList.size(); i++){
            if(!nodeList.get(i).equals(newNode) && !landmark.intersectAnyObstacles(newNode.getPosition(), nodeList.get(i).getPosition())){
                newNode.createLink(nodeList.get(i));
            }
        }
    }

    /** Getters */
    public CopyOnWriteArrayList<Node> getNodeList() {
        return nodeList;
    }
}
