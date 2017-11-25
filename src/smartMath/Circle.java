package smartMath;

/** Classe implémentant le concept de cercle */
public class Circle {

    /** Centre & Rayon */
    private Vector center;
    private int ray;

    /** Constructeur d'un cercle
     * @param center
     * @param ray
     */
    public Circle(Vector center, int ray){
        this.center=center;
        this.ray=ray;
    }

    /** Clone */
    public Circle clone(){
        return new Circle(this.center, this.ray);
    }

    /** Getters & Setters */
    public Vector getCenter() {
        return center;
    }
    public void setCenter(Vector center) {
        this.center = center;
    }
    public int getRay() {
        return ray;
    }
    public void setRay(int ray) {
        this.ray = ray;
    }
}
