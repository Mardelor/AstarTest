package Astar;

public enum TestMode {

    DEFAULT_OBSTACLES,
    RANDOM_OBSTACLES,
    ;

    /** Nombre qui peut servir en fonction du mode */
    public static int numberOfObstacles = 60;
    public static int averageRayObstacle = 50;
    public static int standartDeviation = 25;

    /** Constructors */
    TestMode(){}
}
