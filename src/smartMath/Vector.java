package smartMath;

/**
 * Classe implémentant un vecteur en dimension 2
 */
public class Vector {

    /** Abscisse x */
    private int x;

    /** Ordonnée y */
    private int y;

    /** Rayon ray */
    private double ray;

    /** Angle theta, [-pi, pi[ */
    private double theta;

    /** Constructeur d'un vecteur nul */
    public Vector(){
        this.x=0;
        this.y=0;
        this.ray=0;
        this.theta=0;
    }

    /** Constructeur d'un vecteur en coordonnées cartesienne
     * @param x
     * @param y
     */
    public Vector(int x, int y){
        this.x=x;
        this.y=y;
        this.ray=this.computeRay();
        this.theta=this.computeAngle();
    }

    /** Constructeur d'un vecteur en coorodnnées polaires
     * @param ray
     * @param theta
     */
    public Vector(double ray, double theta){
        if (ray < 0){
            ray=-ray;
            theta=MathLib.modulo(theta + Math.PI, 2*Math.PI);
        }
        this.ray=ray;
        this.theta=MathLib.modulo(theta, 2*Math.PI);
        this.x = (int)(ray*Math.cos(theta));
        this.y = (int)(ray*Math.sin(theta));
    }

    /** Retourne la distance au carré du vecteur en INTEGER */
    public int squaredLength(){
        return x*x + y*y;
    }

    /** Effectue le produit scalaire avec un second vecteur
     * @param other le second vecteur du produit scalaire
     */
    public int scalarProduct(Vector other) {
        return x * other.x + y * other.y;
    }

    /** Effectue le produit vectoriel avec un second vecteur
     * ATTENTION : le produit vectoriel est anti-symétrique, le paramètre est donc le 2e vecteur
     * De plus, comme on est en 2D, on renvoie évidemment le scalaire associé au vecteur
     * @param other le second vecteur du produit vectoriel
     */
    public int vectorProduct (Vector other){
        return (x * other.y - y * other.x);
    }

    /** Retourne la somme de deux vecteurs
     * @param other
     */
    public Vector addNewVector(Vector other){
        return new Vector(this.x + other.x, this.y + other.y);
    }

    /** Retourne la différence de deux vecteurs
     * @param other
     */
    public Vector withdrawNewVector(Vector other){
        return new Vector(this.x - other.x, this.y - other.y);
    }

    /** Ajoute un vecteur
     * @param other
     */
    public void addVector(Vector other){
        this.x += other.x;
        this.y += other.y;
        this.ray = this.computeRay();
        this.theta = this.computeAngle();
    }

    /** Enlève un vecteur
     * @param other
     */
    public void withdrawVector(Vector other){
        this.x -= other.x;
        this.y -= other.y;
        this.ray = this.computeRay();
        this.theta = this.computeAngle();
    }

    /** Retourne un nouveau vecteur rescale
     * @param scale
     */
    public Vector rescaleNewVector(double scale){
        return new Vector(this.ray*scale, this.theta);
    }

    /** Multiplication par un scalaire
     * @param scale
     */
    public void rescale(double scale){
        this.setRay(scale*ray);
    }

    /** Egalité selon les cartésiennes
     * @param other
     */
    public boolean equals(Vector other){
        return (this.x == other.x & this.y == other.y);
    }

    /** Affiche en cartesiennes et en polaires (avec troncature pour les doubles)
     */
    @Override
    public String toString() {
        String troncRay = String.format("%s", ray).substring(0, 5);
        String troncTheta = String.format("%s", theta).substring(0,5);
        return String.format("(%s , %s) (%s , %s)", x,y,troncRay,troncTheta);
    }

    /** Affiche uniquement les cartesiennes */
    public String toStringOnlyInt(){
        return String.format("(%s , %s)", x,y);
    }

    /** Calcul d'un nouvel angle :
     * Développement limité d'atan à l'ordre 7 pour calculé un angle entre 0 et pi/4
     * On lui ajoute ensuite un offset en fonction des signes de x, y et de x-y */
    private double computeAngle(){
        if (this.squaredLength() == 0)
            return 0;

        double ratio = Math.min((double) Math.abs(x), Math.abs(y)) / Math.max(Math.abs(x), Math.abs(y));
        double squaredRatio = ratio*ratio;
        double noOffsetAngle = ((-0.0464964749 * squaredRatio + 0.15931422) * squaredRatio - 0.327622764) * squaredRatio * ratio + ratio;

        if (Math.abs(y) > Math.abs(x))
            noOffsetAngle = 1.57079637 - noOffsetAngle;
        if (x < 0)
            noOffsetAngle = 3.14159274 - noOffsetAngle;
        if (y < 0)
            noOffsetAngle = -noOffsetAngle;
        return noOffsetAngle;
    }

    /** Calcul du nouveau rayon */
    private double computeRay(){
        return Math.sqrt(x*x + y*y);
    }

    /** Getters */
    public int getX() { return x; }
    public int getY() { return y; }
    public double getRay() { return ray; }
    public double getTheta() { return theta; }

    /** Setters */
    public void setX(int x) {
        this.x = x;
        this.theta = this.computeAngle();
        this.ray = this.computeRay();
    }
    public void setY(int y) {
        this.y = y;
        this.theta = this.computeAngle();
        this.ray = this.computeRay();
    }
    public void setRay(double ray) {
        this.ray = ray;
        this.x = (int) (ray*Math.cos(this.theta));
        this.y = (int) (ray*Math.sin(this.theta));
    }
    public void setTheta(double theta) {
        this.theta = theta;
        this.x = (int) (ray*Math.cos(this.theta));
        this.y = (int) (ray*Math.sin(this.theta));
    }
}