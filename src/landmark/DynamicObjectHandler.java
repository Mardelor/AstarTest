package landmark;

import algorithms.TestMode;
import smartMath.Circle;
import smartMath.MathLib;
import smartMath.MovingCircle;
import smartMath.Vector;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class DynamicObjectHandler extends Thread {

    /** Landmark */
    private Landmark landmark;

    /** Liste des obstacles */
    private CopyOnWriteArrayList<MovingCircle> dynamicObstacles;

    /** Le suiveur de chemin */
    private Follower follower;

    /** Paramètre d'une VA de Bernouilli */
    private double bernouilliParam;

    /** Constructeur */
    public DynamicObjectHandler(Landmark landmark){
        this.landmark = landmark;
        this.dynamicObstacles = landmark.getListMovingObst();
        this.bernouilliParam = TestMode.bernouilliMovingPopObstacle;
    }

    /** Ajoute un obstacle mobile suivant une loi de proba de Bernouilli
     * Si elle est réalisée, on ajoute un obstacle mouvant et on réinitialise le paramètre de Bernouilli,
     * Sinon on incrémente le paramètre le Bernouilli
     */
    private void addMovingObstacle(){
        if(MathLib.randomBernouilli(bernouilliParam)) {
            Vector position = new Vector(MathLib.randomUniform(-landmark.getSizeX() / 2 + TestMode.averageRayObstacle, landmark.getSizeX() / 2 - TestMode.averageRayObstacle),
                    MathLib.randomUniform(-landmark.getSizeY() / 2 + TestMode.averageRayObstacle, landmark.getSizeY() / 2 - TestMode.averageRayObstacle));
            int ray = (int) MathLib.randomGaussian(TestMode.averageRayMovingObstacle, TestMode.standartDeviationMovingObstacle);
            dynamicObstacles.add(new MovingCircle(position, ray));
            bernouilliParam = TestMode.bernouilliMovingPopObstacle;
        }else{
            bernouilliParam += TestMode.bernouilliMovingPopObstacle;
        }
    }

    /** Met à jour la position des obstacles mouvants */
    private void updateMovingObstacle(){
        for(MovingCircle obstacle : dynamicObstacles){
            obstacle.updatePosition();
            if(!landmark.isInLandmark(obstacle.getCenter())){
                dynamicObstacles.remove(obstacle);
            }
        }
    }

    /** Initialise un follower */
    public void createFollower(ArrayList<Vector> path){
        this.follower = new Follower(40, path);
        landmark.setFollower(follower);
    }

    /** Le Thread se contente de rajouter des obstacles et de les mettre à jour/supprimer */
    @Override
    public void run(){
        while(true){
            try {
                addMovingObstacle();
                updateMovingObstacle();
                if(follower != null){
                    follower.updatePosition();
                }
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
