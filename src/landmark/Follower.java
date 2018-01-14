package landmark;

import smartMath.MovingCircle;
import smartMath.Vector;

import java.util.ArrayList;

public class Follower extends MovingCircle {

    /** Chemin suivi */
    private ArrayList<Vector> followedPath;

    /** Constructor */
    public Follower(int size, ArrayList<Vector> path){
        super((Vector) path.get(0).clone(), size);
        followedPath = (ArrayList<Vector>) path.clone();
    }

    /** Méthode appelé pour le suivit de chemin */
    @Override
    public void updatePosition(){
        this.center.addVector(speedVector);
        if(!followedPath.isEmpty()) {
            speedVector = followedPath.get(0).withdrawNewVector(center);
            if(speedVector.getRay() > 4){
                speedVector.setRay(4);
            }else{
                updateAim();
            }
        }else{
            speedVector.setRay(0);
        }
    }

    /** Met à jour le point visé (position 0 du chemin) */
    private void updateAim(){
        if(!followedPath.isEmpty()){
            followedPath.remove(0);
        }
    }

    /** Getters & Setters */
    public ArrayList<Vector> getFollowedPath() {
        return followedPath;
    }
    public void setFollowedPath(ArrayList<Vector> followedPath) {
        this.followedPath = followedPath;
    }
}
