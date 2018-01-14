package smartMath;

import algorithms.TestMode;

/** Classe qui permet d'implémenter des cercle mouvant, impélmenté sous forme de vecteur vitesse */
public class MovingCircle extends Circle {

    /** Vecteur vitesse du cercle */
    protected Vector speedVector;

    /** Constructeur */
    public MovingCircle(Vector center, int ray){
        super(center, ray);
        speedVector = new Vector(TestMode.initialSpeed, MathLib.randomGaussian(MathLib.modulo(center.getTheta() - Math.PI, 2*Math.PI), Math.PI/16));
    }

    /** Met à jour la position de l'obstacle et sa vitesse
     * Le nouveau vecteur vitesse dépend de l'ancien via une loi de proba gaussienne */
    public void updatePosition(){
        this.center.addVector(speedVector);
        this.speedVector = new Vector(MathLib.randomGaussian(speedVector.getRay(), 1), MathLib.randomGaussian(speedVector.getTheta(), Math.PI/16));
    }

    /** Getter & Setter */
    public Vector getSpeedVector() {
        return speedVector;
    }
    public void setSpeedVector(Vector speedVector) {
        this.speedVector = speedVector;
    }
}
