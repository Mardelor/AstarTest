package algorithms;

public enum TestMode {

    DEFAULT_OBSTACLES,
    RANDOM_OBSTACLES,
    ;

    /** Nombre qui peut servir en fonction du mode */
    public static int numberOfObstacles = 50;
    public static int averageRayObstacle = 50;
    public static int standartDeviation = 10;
    public static int averageRayMovingObstacle = 50;
    public static int standartDeviationMovingObstacle = 10;
    public static double bernouilliMovingPopObstacle = 4*Math.pow(10, -4);
    public static double initialSpeed = 1;

    /** Constructors */
    TestMode(){}
}
