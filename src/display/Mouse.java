package display;

import smartMath.Vector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {

    /** Panel... */
    private LandmarkPanel panel;

    /** Coorodonnées boutons */
    private Vector leftClickPosition;
    private Vector rightClickPosition;
    private Vector middleClickPosition;

    /** Constructeur */
    public Mouse(LandmarkPanel panel){
        this.panel=panel;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // Clic gauche
        if (e.getButton() == MouseEvent.BUTTON1){
            leftClickPosition = changeRefToLandmark(e.getX(), e.getY());
        }
        // Clic droit
        if (e.getButton() == MouseEvent.BUTTON3){
            rightClickPosition = changeRefToLandmark(e.getX(), e.getY());
        }
        // Clic mileu
        if (e.getButton() == MouseEvent.BUTTON2){
            middleClickPosition = changeRefToLandmark(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /** Change le vecteur dans le repère du Landmark */
    private Vector changeRefToLandmark(int x, int y){
        return new Vector(x - panel.getLandmark().getSizeX()/2, panel.getLandmark().getSizeY()/2 + 30 - y);
    }

    /** Met les clics à null */
    public void resetClicks(){
        leftClickPosition = null;
        rightClickPosition = null;
        middleClickPosition = null;
    }

    /** Getters */
    public Vector getLeftClickPosition() {
        return leftClickPosition;
    }
    public Vector getRightClickPosition() {
        return rightClickPosition;
    }
    public Vector getMiddleClickPosition() {
        return middleClickPosition;
    }
}
