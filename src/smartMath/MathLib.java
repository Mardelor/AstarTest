package smartMath;

import com.sun.nio.sctp.PeerAddressChangeNotification;

import java.util.Random;

/** Lib de methodes statiques pour calculs mathématiques */
public class MathLib {

    /** Modulo qui calcul entre -module/2 inclue et +module/2 non inclue
     * @param value
     * @param module
     */
    public static double modulo(double value, double module){
        value = value%module;
        if (value >= module/2){
            value-=module;
        }
        else if(value < -module/2){
            value+= module;
        }
        return value;
    }

    /**
     * Détermine si un segment et un cercle s'intersectent ou non
     * @param vec1
     * @param vec2
     * @param circle
     * @return vrai si il y a intersection entre le segment et le cercle, faux sinon
     */
    public static boolean intersect(Vector vec1, Vector vec2, Circle circle)
    {
        // TODO : expliquer l'algo
        double area = ((double)circle.getCenter().getX() - (double)vec1.getX())*((double)vec2.getY() - (double)vec1.getY()) - ((double)circle.getCenter().getY() - (double)vec1.getY())*((double)vec2.getX() - (double)vec1.getX());
        double distA = ((double)vec1.getX() - (double)circle.getCenter().getX())*((double)vec1.getX() - (double)circle.getCenter().getX()) + ((double)vec1.getY() - (double)circle.getCenter().getY())*((double)vec1.getY() - (double)circle.getCenter().getY());
        double distB = ((double)vec2.getX() - (double)circle.getCenter().getX())*((double)vec2.getX() - (double)circle.getCenter().getX()) + ((double)vec2.getY() - (double)circle.getCenter().getY())*((double)vec2.getY() - (double)circle.getCenter().getY());
        if(distA >= circle.getRay() * circle.getRay() && distB < circle.getRay() * circle.getRay() || distA < circle.getRay() * circle.getRay() && distB >= circle.getRay() * circle.getRay())
            return true;
        return distA >= circle.getRay() * circle.getRay()
                && distB >= circle.getRay() * circle.getRay()
                && area * area / (((double)vec2.getX() - (double)vec1.getX())*((double)vec2.getX() - (double)vec1.getX())+((double)vec2.getY() - (double)vec1.getY())*((double)vec2.getY() - (double)vec1.getY())) <= circle.getRay() * circle.getRay()
                && ((double)vec2.getX() - (double)vec1.getX())*((double)circle.getCenter().getX() - (double)vec1.getX()) + ((double)vec2.getY() - (double)vec1.getY())*((double)circle.getCenter().getY() - (double)vec1.getY()) >= 0
                && ((double)vec1.getX() - (double)vec2.getX())*((double)circle.getCenter().getX() - (double)vec2.getX()) + ((double)vec1.getY() - (double)vec2.getY())*((double)circle.getCenter().getY() - (double)vec2.getY()) >= 0;
    }

    /** Renvoie la réalisation d'une variable aléatoire avec paramètres données
     * @param n
     * @param p entre 0 et 1
     */
    public static int randomBinom(int n, double p){
        if(p > 1 || p < 0){
            System.err.println("p n'est pas compris entre 0 et 1...");
            return 0;
        }

        Random rand = new Random();
        int result = 0;
        for(int i=0; i<n; i++){
            if (rand.nextDouble() < p){
                result+=1;
            }
        }
        return result;
    }

    /** Renvoie la réalisation d'un variable aléatoire de loi uniforme
     * @param begin
     * @param end
     */
    public static int randomUniform(int begin, int end){
        Random rand = new Random();
        return (int)(rand.nextDouble()*(end - begin) + begin);
    }
}

