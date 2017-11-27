package smartMath;

import Astar.TestMode;
import graph.Node;
import graph.Ridge;

import java.util.ArrayList;
import java.util.Random;

/** Un repère délimité contenant des obstacles */
public class Landmark {

    /** Liste de cercles représentant un obstacle */
    private ArrayList<Circle> listObst;
    private TestMode mode;

    /** Délimitation du landmark */
    private int sizeX;
    private int sizeY;
    private Vector upLeft;
    private Vector upRight;
    private Vector downLeft;
    private Vector downRight;

    /** Constucteur du Landmark */
    public Landmark(int sizeX, int sizeY, TestMode mode){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.mode = mode;
        this.upLeft = new Vector(-sizeX/2, sizeY/2);
        this.upRight = new Vector(sizeX/2, sizeY/2);
        this.downLeft = new Vector(-sizeX/2, -sizeY/2);
        this.downRight = new Vector(sizeX/2, -sizeY/2);
        listObst = new ArrayList<>();

        initObstacle();
    }

    /** True si le vecteur est dans le landmark */
    public boolean isInLandmark(Vector toVerify){
        return (Math.abs(toVerify.getX())<=sizeX/2 && Math.abs(toVerify.getY()) <= sizeY/2);
    }

    /** Initialise les obstacles */
    private void initObstacle(){
        if(mode.equals(TestMode.RANDOM_OBSTACLES)){
            for(int i=0; i<mode.getNumberOfObstacles(); i++){
                int randX = MathLib.randomUniform(-sizeX/2 + 100, sizeX/2 - 50);
                int randY = MathLib.randomUniform(-sizeY/2 + 100, sizeY/2 - 50);
                int randRay = MathLib.randomBinom(10, 0.5)*10;
                listObst.add(new Circle(new Vector(randX, randY), randRay));
            }
        }
        else if(mode.equals(TestMode.DEFAULT_OBSTACLES)){
            listObst.add(new Circle(new Vector(0, sizeY / 4), (int) (sizeY / 10.0)));
            listObst.add(new Circle(upLeft.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(upLeft.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(upRight.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(upRight.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(downLeft.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(downLeft.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(downRight.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listObst.add(new Circle(downRight.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
        }
        Ridge.staticCost = 0;
    }

    /** Vérifie si la position donnée est dans un obstacle
     * @param toVerify
     */
    public boolean isInObstacle(Vector toVerify){
        for (Circle circle : listObst){
            if(toVerify.withdrawNewVector(circle.getCenter()).getRay() <= circle.getRay()){
                return true;
            }
        }
        return false;
    }

    /** Vérifie si le segment définie par les deux vecteurs intersecte un des obstacles de la table
     * @param vec1
     * @param vec2
     */
    public boolean intersectAnyObstacles(Vector vec1, Vector vec2){
        for(Circle circle : listObst){
            if(MathLib.intersect(vec1, vec2, circle)){
                return true;
            }
        }
        return false;
    }

    /** Getters & Setters */
    public ArrayList<Circle> getListObst() {
        return listObst;
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public TestMode getMode() {
        return mode;
    }
    public Vector getUpLeft() {
        return upLeft;
    }
    public Vector getUpRight() {
        return upRight;
    }
    public Vector getDownLeft() {
        return downLeft;
    }
    public Vector getDownRight() {
        return downRight;
    }
}
