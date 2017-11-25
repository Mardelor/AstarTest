package graph;

import smartMath.Vector;

public class PointOutOfLandmarkException extends Exception {

    /** Position */
    private Vector position;

    /** */
    public PointOutOfLandmarkException(Vector position){
        this.position = position;
    }

    /** Getters */
    public Vector getPosition() {
        return position;
    }
}
