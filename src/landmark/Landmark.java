package landmark;

import algorithms.TestMode;
import graph.Ridge;
import smartMath.Circle;
import smartMath.MathLib;
import smartMath.MovingCircle;
import smartMath.Vector;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/** Un repère délimité contenant des obstacles */
public class Landmark {

    /** Liste de cercles représentant un obstacle */
    private ArrayList<Circle> listStaticObst;
    private CopyOnWriteArrayList<MovingCircle> listMovingObst;
    private Follower follower;
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
        listStaticObst = new ArrayList<>();
        listMovingObst = new CopyOnWriteArrayList<>();

        initObstacle();
    }

    /** True si le vecteur est dans le landmark */
    public boolean isInLandmark(Vector toVerify){
        return (Math.abs(toVerify.getX())<=sizeX/2 && Math.abs(toVerify.getY()) <= sizeY/2);
    }

    /** Initialise les obstacles */
    private void initObstacle(){
        if(mode.equals(TestMode.RANDOM_OBSTACLES)){
            for(int i=0; i<TestMode.numberOfObstacles; i++){
                int randX = (int) MathLib.randomUniform(-sizeX/2 + TestMode.averageRayObstacle, sizeX/2 - TestMode.averageRayObstacle);
                int randY = (int) MathLib.randomUniform(-sizeY/2 + TestMode.averageRayObstacle, sizeY/2 - TestMode.averageRayObstacle);
                int randRay = (int) MathLib.randomGaussian(TestMode.averageRayObstacle, TestMode.standartDeviation);
                listStaticObst.add(new Circle(new Vector(randX, randY), randRay));
            }
        }
        else if(mode.equals(TestMode.DEFAULT_OBSTACLES)){
            listStaticObst.add(new Circle(new Vector(0, sizeY / 4), (int) (sizeY / 10.0)));
            listStaticObst.add(new Circle(upLeft.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(upLeft.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(upRight.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(upRight.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(downLeft.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(downLeft.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(downRight.rescaleNewVector(0.3), (int) ((sizeY + sizeX) / 24.0)));
            listStaticObst.add(new Circle(downRight.rescaleNewVector(0.6), (int) ((sizeY + sizeX) / 24.0)));
        }
        Ridge.staticCost = 0;
    }

    /** Vérifie si la position donnée est dans un obstacle
     * @param toVerify
     */
    public boolean isInObstacle(Vector toVerify){
        for (Circle circle : listStaticObst){
            if(toVerify.withdrawNewVector(circle.getCenter()).getRay() <= circle.getRay()){
                return true;
            }
        }
        for (Circle circle : listMovingObst){
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
        for(Circle circle : listStaticObst){
            if(MathLib.intersect(vec1, vec2, circle)){
                return true;
            }
        }
        for(Circle circle : listMovingObst){
            if(MathLib.intersect(vec1, vec2, circle)){
                return true;
            }
        }
        return false;
    }

    /** Getters & Setters */
    public ArrayList<Circle> getListStaticObst() {
        return listStaticObst;
    }
    public CopyOnWriteArrayList<MovingCircle> getListMovingObst() {
        return listMovingObst;
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
    public Follower getFollower() {
        return follower;
    }
    public void setFollower(Follower follower) {
        this.follower = follower;
    }
}
