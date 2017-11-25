package display;

import Astar.TestMode;
import graph.Graph;
import graph.Node;
import graph.Ridge;
import smartMath.Circle;
import smartMath.Landmark;
import smartMath.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class LandmarkPanel extends JPanel {

    /** Landmark */
    private Landmark landmark;

    /** Graphe */
    private Graph graph;
    private ArrayList<Vector> path;
    private ArrayList<Vector> clics;

    /** Informations */
    private ArrayList<String> infoQueue;
    private String errorMessage;

    /** Couleurs */
    private Color obstacleColor = new Color(160, 40, 20, 200);
    private Color nodeColor = new Color(40, 60, 160, 180);
    private Color ridgeColor = new Color(220, 100, 20, 80);
    private Color pathColor = new Color(120, 60, 120);
    private Color errorColor = new Color(250, 80, 80);
    private Color printColor = new Color(180, 200, 220);

    /** Constructeur */
    public LandmarkPanel(Landmark landmark, Graph graph){
        this.landmark = landmark;
        this.graph = graph;
        this.setLayout(null);

        path = new ArrayList<>();
        clics = new ArrayList<>();
        infoQueue = new ArrayList<>();
        errorMessage = "";
    }

    /** Méthode appelée automatiquement par Window qui permet de dessiner des trucs ! */
    @Override
    public void paintComponent(Graphics graphics){
        graphics.setColor(Color.DARK_GRAY);
        // Le Landmark
        graphics.drawRect(changeRef(landmark.getUpLeft()).getX(), changeRef(landmark.getUpLeft()).getY(), landmark.getSizeX(), landmark.getSizeY());

        graphics.setColor(obstacleColor);
        // Obstacles
        for (Circle circle : landmark.getListObst()) {
            graphics.fillOval((this.changeRef(circle.getCenter()).getX() - circle.getRay()),
                    (this.changeRef(circle.getCenter()).getY() - circle.getRay()),
                    2 * circle.getRay(),
                    2 * circle.getRay());
        }

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

        // Chemin
        graphics.setColor(pathColor);
        if(path.size() >0) {
            graphics.fillOval(changeRef(path.get(0)).getX() - 4, changeRef(path.get(0)).getY() - 4, 8, 8);
        }
        for(int i=0; i<path.size()-1; i++){
            graphics.drawLine(changeRef(path.get(i)).getX(), changeRef(path.get(i)).getY(), changeRef(path.get(i+1)).getX(), changeRef(path.get(i+1)).getY());
            graphics.fillOval(changeRef(path.get(i+1)).getX() - 4, changeRef(path.get(i+1)).getY() - 4, 8, 8);
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
            int nbCarachterPerLine = errorLength/7;
            int nbLines = errorMessage.length()/nbCarachterPerLine;
            for (int i = 0; i <= nbLines; i++) {
                String line = errorMessage.substring(i*nbCarachterPerLine, i*nbCarachterPerLine + Math.min((i+1)*nbCarachterPerLine, errorMessage.length() - i*nbCarachterPerLine));
                graphics.drawString(line, 150 + landmark.getSizeX()/2, landmark.getSizeY() + 40 + i * 20);
            }
        }
    }

    /** Change de ref
     * @param vector */
    private Vector changeRef(Vector vector){
        return new Vector(vector.getX() + landmark.getSizeX()/2, landmark.getSizeY()/2 - vector.getY());
    }

    /** Set un chemin
     * @param path */
    public void drawPath(ArrayList<Vector> path) {
        this.path = path;
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
