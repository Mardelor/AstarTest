package graph;

import smartMath.Circle;
import smartMath.Landmark;
import smartMath.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Graph {

    /** Liste des noeuds */
    private CopyOnWriteArrayList<Node> nodeList;
    private ArrayList<Node> staticNodes;

    /** Liste des arrêtes */
    private HashSet<Ridge> ridgeList;

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
        this.ridgeList = new HashSet<>();
        this.initGraph();
    }

    /************************
     * INITIALISATION GRAPH *
     ************************/

    /** Initialise le graphe : place les noeuds en fonction des obstacles & les relies avec des arrêtes */
    private void initGraph(){
        initNodes();
        initRidges();
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
        for(int i=0; i<nodeList.size()-1; i++){
            Node node1 = nodeList.get(i);
            for(int j=i+1; j<nodeList.size(); j++){
                Node node2 = nodeList.get(j);
                if(!landmark.intersectAnyObstacles(node1.getPosition(), node2.getPosition())){
                    node1.createLink(node2);
                }
            }
            ridgeList.addAll(node1.getRidgeList());
        }
    }

    /*********
     * UTILS *
     *********/

    /** Place les noeuds autour d'un obstacle
     * @param circle */
    private ArrayList<Node> placeNodes(Circle circle){
        Vector posCenter = circle.getCenter();
        ArrayList<Node> addNodes = new ArrayList<>();
        int nbNodes = circle.getRay()/4;
        for(int i=-nbNodes/2; i<=nbNodes/2; i++){
            Vector posNode = new Vector(1.2*circle.getRay(), i*2*Math.PI/nbNodes);
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
    public HashSet<Ridge> getRidgeList() {
        return ridgeList;
    }
}
