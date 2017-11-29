package display;

import graph.Graph;
import graph.Node;
import graph.Ridge;
import smartMath.Circle;
import smartMath.Landmark;
import smartMath.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class LandmarkPanel extends JPanel {

    /** Landmark */
    private Landmark landmark;
    private Image background;

    /** Graphe */
    private Graph graph;
    private ArrayList<Vector> greenPath;
    private ArrayList<Vector> purplePath;
    private ArrayList<Vector> clics;

    /** Informations */
    private ArrayList<String> infoQueue;
    private String errorMessage;
    private Font messageStyle = new Font("Default", Font.BOLD, 12);

    /** Couleurs */
    private Color obstacleColor = new Color(160, 40, 20, 160);
    private Color nodeColor = new Color(40, 60, 160, 180);
    private Color ridgeColor = new Color(220, 100, 50, 30);
    private Color greenPathColor = new Color(60, 160, 60, 180);
    private Color purplePathColor = new Color(160, 60, 160, 180);
    private Color errorColor = new Color(250, 80, 80);
    private Color printColor = new Color(180, 200, 220);

    /** Constructeur */
    public LandmarkPanel(Landmark landmark, Graph graph){
        this.landmark = landmark;
        this.graph = graph;

        greenPath = new ArrayList<>();
        purplePath = new ArrayList<>();
        clics = new ArrayList<>();
        infoQueue = new ArrayList<>();
        errorMessage = "";
    }

    /** Méthode appelée automatiquement par Window qui permet de dessiner des trucs ! */
    @Override
    public void paintComponent(Graphics graphics){

        graphics.setColor(Color.WHITE);
        // Le Landmark
        graphics.fillRect(changeRef(landmark.getUpLeft()).getX(), changeRef(landmark.getUpLeft()).getY(), landmark.getSizeX(), landmark.getSizeY());
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawRect(changeRef(landmark.getUpLeft()).getX(), changeRef(landmark.getUpLeft()).getY(), landmark.getSizeX(), landmark.getSizeY());

        graphics.setColor(obstacleColor);
        // Obstacles
        for (Circle circle : landmark.getListObst()) {
            graphics.fillOval((this.changeRef(circle.getCenter()).getX() - circle.getRay()),
                    (this.changeRef(circle.getCenter()).getY() - circle.getRay()),
                    2 * circle.getRay(),
                    2 * circle.getRay());
        }

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, landmark.getSizeY() +1, landmark.getSizeX(), landmark.getSizeY());

        // Noeuds
        for (Node node : graph.getNodeList()) {
            graphics.setColor(nodeColor);
            Vector positionDisplay = changeRef(node.getPosition());
            graphics.fillOval(positionDisplay.getX() - 2,
                    positionDisplay.getY() - 2,
                    4, 4);
            graphics.setColor(ridgeColor);
            for (Ridge ridge : node.getRidgeList()) {
                graphics.drawLine(positionDisplay.getX(),
                        positionDisplay.getY(),
                        changeRef(ridge.getSecondNode().getPosition()).getX(),
                        changeRef(ridge.getSecondNode().getPosition()).getY());
            }
        }

        // Arrêtes
        graphics.setColor(ridgeColor);
        for (Ridge ridge : graph.getRidgeList()) {
            graphics.drawLine(changeRef(ridge.getFirstNode().getPosition()).getX(),
                    changeRef(ridge.getFirstNode().getPosition()).getY(),
                    changeRef(ridge.getSecondNode().getPosition()).getX(),
                    changeRef(ridge.getSecondNode().getPosition()).getY());
        }

        // Chemins
        graphics.setColor(greenPathColor);
        graphics.setFont(messageStyle);
        if(greenPath.size() >0) {
            graphics.fillOval(changeRef(greenPath.get(0)).getX() - 4, changeRef(greenPath.get(0)).getY() - 4, 8, 8);
            graphics.drawString("Dijkstra", 20, 20);
        }
        for(int i=0; i<greenPath.size()-1; i++){
            graphics.drawLine(changeRef(greenPath.get(i)).getX(), changeRef(greenPath.get(i)).getY(), changeRef(greenPath.get(i+1)).getX(), changeRef(greenPath.get(i+1)).getY());
            graphics.fillOval(changeRef(greenPath.get(i+1)).getX() - 4, changeRef(greenPath.get(i+1)).getY() - 4, 8, 8);
        }
        graphics.setColor(purplePathColor);
        if(purplePath.size() >0) {
            graphics.fillOval(changeRef(purplePath.get(0)).getX() - 4, changeRef(purplePath.get(0)).getY() - 4, 8, 8);
            graphics.drawString("Astar", 20, 40);
        }
        for(int i=0; i<purplePath.size()-1; i++){
            graphics.drawLine(changeRef(purplePath.get(i)).getX(), changeRef(purplePath.get(i)).getY(), changeRef(purplePath.get(i+1)).getX(), changeRef(purplePath.get(i+1)).getY());
            graphics.fillOval(changeRef(purplePath.get(i+1)).getX() - 4, changeRef(purplePath.get(i+1)).getY() - 4, 8, 8);
        }

        // Clics
        for(Vector clic : clics){
            Vector clicDisplay = changeRef(clic);
            graphics.fillOval(clicDisplay.getX() - 4, clicDisplay.getY() - 4, 8, 8);
            graphics.drawString(clic.toStringOnlyInt(), clicDisplay.getX() - 30, clicDisplay.getY() + 20);
        }

        // Infos
        graphics.setColor(Color.DARK_GRAY);
        int debugLength = (landmark.getSizeX() - 40)/2 + 110;
        graphics.fillRoundRect(20, landmark.getSizeY() + 20, debugLength, 300, 20, 20);
        int errorLength = (landmark.getSizeX() - 40)/2 - 130;
        graphics.fillRoundRect(landmark.getSizeX()/2 + 130, landmark.getSizeY() +20, errorLength, 300, 20, 20);

        graphics.setColor(printColor);
        for (int i=0; i<infoQueue.size(); i++){
            graphics.drawString(infoQueue.get(i),30,landmark.getSizeY() + 40 + i*20);
        }

        graphics.setColor(errorColor);
        if(errorMessage.length() != 0) {
            int nbCharachterPerLine = errorLength/8;
            int nbLines = errorMessage.length()/nbCharachterPerLine;
            for (int i = 0; i <= nbLines; i++) {
                String line = errorMessage.substring(i*nbCharachterPerLine, i*nbCharachterPerLine + Math.min((i+1)*nbCharachterPerLine, errorMessage.length() - i*nbCharachterPerLine));
                graphics.drawString(line, 150 + landmark.getSizeX()/2, landmark.getSizeY() + 40 + i * 20);
            }
        }
    }

    /** Change de ref
     * @param vector */
    private Vector changeRef(Vector vector){
        return new Vector(vector.getX() + landmark.getSizeX()/2, landmark.getSizeY()/2 - vector.getY());
    }

    /** Set un chemin violet
     * @param path */
    public void drawPathPurple(ArrayList<Vector> path) {
        this.purplePath = path;
        removeAll();
        revalidate();
    }

    /** Set un chemin vert
     * @param path */
    public void drawPathGreen(ArrayList<Vector> path){
        this.greenPath = path;
        removeAll();
        revalidate();
    }

    /** Reset les chemins */
    public void resetPaths(){
        this.greenPath = new ArrayList<>();
        this.purplePath = new ArrayList<>();
        removeAll();
        revalidate();
    }

    /** Set les positions des clics
     * @param clics */
    public void setClics(ArrayList<Vector> clics){
        this.clics = clics;
        removeAll();
        revalidate();
    }

    /** Affiche des infos dans l'interface
     * @param message */
    public void printDebug(String message){
        infoQueue.add(message);
        if(infoQueue.size() > 14){
            infoQueue.remove(0);
        }
        removeAll();
        revalidate();
    }

    /** Affiche les erreurs sur l'interface
     * @param message */
    public void printError(String message){
        errorMessage = message;
        removeAll();
        revalidate();
    }

    /** Reset le message d'erreur à 0 */
    public void removeErrorMessage(){
        errorMessage = "";
    }

    /** Getters */
    public Landmark getLandmark(){
        return landmark;
    }
}
