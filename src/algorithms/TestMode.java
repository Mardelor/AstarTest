package algorithms;

public enum TestMode {

    DEFAULT_OBSTACLES,
    RANDOM_OBSTACLES,
    ;

    /** Nombre qui peut servir en fonction du mode */
    public static int numberOfObstacles = 10;
    public static int averageRayObstacle = 30;
    public static int standartDeviation = 15;
    public static int averageRayMovingObstacle = 40;
    public static int standartDeviationMovingObstacle = 5;
    public static double bernouilliMovingPopObstacle = Math.pow(10, -4);
    public static double initialSpeed = 2;

    /** Constructors */
    TestMode(){}
}
