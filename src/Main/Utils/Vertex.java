package Main.Utils;

public class Vertex {
    int number;
    double x;
    double y;
    private double forceX;
    private double forceY;
    private double radius;

    /**
     * Constructor of the Vertice object
     *
     * @param number number of the current vertice
     * @param x position of the vertice on the abscissa axis
     * @param y position of the vertice on the ordinate axis
     */
    public Vertex(int number, double x, double y) {
        this.x=x;
        this.y=y;
        this.number = number;
        this.forceX = 0;
        this.forceY = 0;
        this.radius = radius;
    }

    /**
     * Getter method
     * @return the number of the vertice
     */
    public int getNumber() {
        return number;
    }

    /**
     * Getter method
     * @return position of the vertice on the abscissa axis
     */
    public double getX() {
        return x;
    }

    /**
     * Getter method
     * @return position of the vertice on the ordinate axis
     */
    public double getY() {
        return y;
    }

    /**
     * Setter method
     * Sets the position of the vertice on the abscissa axis
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter method
     * Sets the position of the vertice on the ordinate axis
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Adds a force to a vertex
     *
     * @param fx force on the abscissa axis
     * @param fy force on the ordinate axis
     */
    public void addForce(double fx, double fy) {
        this.forceX += fx;
        this.forceY += fy;
    }

    /**
     * getter method
     *
     * @return the force on the abscissa axis
     */
    public double getForceX() {
        return forceX;
    }

    /**
     * Getter method
     *
     * @return the force on the ordinate axis
     */
    public double getForceY() {
        return forceY;
    }

    /**
     * Resets the force for a specific vertex
     */
    public void resetForce() {
        this.forceX = 0;
        this.forceY = 0;
    }
}
