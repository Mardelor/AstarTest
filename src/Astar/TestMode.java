package Astar;

public enum TestMode {

    DEFAULT_OBSTACLES,
    RANDOM_OBSTACLES(20),
    ;

    /** Nombre qui peut servir en fonction du mode */
    private int numberOfObstacles;
    public static int averageRayObstacle = 60;

    /** Constructors */
    TestMode(int defaultNumberObstacles){
        numberOfObstacles = defaultNumberObstacles;
    }
    TestMode(){}

    /** Getters and Setters */
    public void setNumberOfObstacles(int numberOfObstacles) {
        this.numberOfObstacles = numberOfObstacles;
    }
    public int getNumberOfObstacles() {
        return numberOfObstacles;
    }
}
