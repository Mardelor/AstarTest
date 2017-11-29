package Astar;

public enum TestMode {

    DEFAULT_OBSTACLES,
    RANDOM_OBSTACLES,
    ;

    /** Nombre qui peut servir en fonction du mode */
    public static int numberOfObstacles = 80;
    public static int averageRayObstacle = 30;
    public static int standartDeviation = 10;

    /** Constructors */
    TestMode(){}
}
