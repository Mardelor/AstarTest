package Astar;

import smartMath.Vector;

public class NoPathFoundException extends Exception {

    /** Paramètres du pathfinding */
    private Vector begin;
    private Vector end;

    /** Constructeur */
    public NoPathFoundException(Vector begin, Vector end){
        this.begin = begin;
        this.end = end;
    }

    /** Getters */
    public Vector getBegin() {
        return begin;
    }
    public Vector getEnd() {
        return end;
    }
}
