package display;

import graph.Graph;
import landmark.Landmark;
import smartMath.Vector;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    /** JPanel */
    private LandmarkPanel panel;

    /** Mouse Listener */
    private Mouse mouse;

    /** Constructeur */
    public Window(Landmark landmark, Graph graph){
        this.setTitle("Landmark - algorithms");
        this.setSize(landmark.getSizeX()+3, landmark.getSizeY()+30+340);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.LIGHT_GRAY);

        panel = new LandmarkPanel(landmark, graph);
        this.setContentPane(panel);

        mouse = new Mouse(panel);
        addMouseListener(mouse);

        this.setVisible(true);
    }

    /** Pour afficher le chemin */
    public void drawPath(ArrayList<Vector> path, Color color){
        if(color.equals(Color.GREEN)) {
            this.panel.drawPathGreen(path);
        }
        else{
            this.panel.drawPathPurple(path);
        }
    }

    /** Met les chemin à zéro */
    public void resetPath(){
        this.panel.resetPaths();
    }

    /** Attend les clics gauche et droit et renvoie leur position sur le landmark */
    public ArrayList<Vector> waitLRClic() throws InterruptedException{
        ArrayList<Vector> clics = new ArrayList<>();
        while(mouse.getLeftClickPosition() == null || mouse.getRightClickPosition() == null){
            Thread.sleep(1);
        }
        clics.add(mouse.getLeftClickPosition());
        clics.add(mouse.getRightClickPosition());
        panel.setClics(clics);
        mouse.resetClicks();
        return clics;
    }

    /** Attend que l'on clic middle */
    public Vector waitMidClic() throws InterruptedException{
        while (mouse.getMiddleClickPosition() == null){
            Thread.sleep(100);
        }
        Vector clic = mouse.getMiddleClickPosition();
        mouse.resetClicks();
        return clic;
    }

    /** Permet d'afficher des messages dans l'interface
     * @param informations */
    public void printDebug(String informations){
        panel.printDebug(informations);
    }

    /** Affiche errerus (en rouge)
     * @param errors */
    public void printError(String errors){
        panel.printError(errors);
    }

    /** ... */
    public void removeErrorMess(){
        panel.removeErrorMessage();
    }

    /** Getters */
    public LandmarkPanel getPanel() {
        return panel;
    }
    public Vector getRightClickPosition(){
        return mouse.getRightClickPosition();
    }
    public Vector getLeftClickPosition(){
        return mouse.getLeftClickPosition();
    }
    public Mouse getMouse() {
        return mouse;
    }
}
