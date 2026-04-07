package Main.graphGeneration;

import Main.Utils.Edge;
import Main.Utils.Vertex;

import java.util.Timer;

public class ForceDirected {

    private double centerX;
    private double centerY;
    private double radius;
    private static final double REPULSION_CONSTANT = 100.0;
    private static final double SPRING_CONSTANT = 0.01;
    private static final double DAMPING = 0.5; //reduces the movement of vertices
    private static final double THRESHOLD = 0.1; //the maximum movement allowed in an iteration

    /**
     * Constructs a ForceDirected layout with a specified center and radius
     * @param centerX The x-coordinate of the center of the layout
     * @param centerY The y-coordinate of the center of the layout
     * @param radius The radius of the circular area for the layout
     */
    public ForceDirected(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * This method applies forces to vertices and edges
     * @param vertices Array of vertices in the graph
     * @param edges Array of edges in the graph
     */
    public void applyForceDirectedLayout(Vertex[] vertices, Edge[] edges) {
        Timer timer = new Timer();
        long startTime = System.currentTimeMillis();

        initializePositions(vertices); //positioning vertices
        double maxMovement; //the largest distance that any vertex has moved in the iteration

        //this loop iterates until the maximum movement of any vertex in a single iteration is below the threshold
        do {
            maxMovement = 0;
            for (Vertex vertex : vertices) {//applying forces

                applyRepulsiveForces(vertex, vertices);
                applyAttractiveForces(vertex, edges, vertices);
            }
            for (Vertex vertex : vertices) { //updating positions
                maxMovement = Math.max(maxMovement, updatePosition(vertex)); //updating movement
            }
        }
        while (maxMovement > THRESHOLD && System.currentTimeMillis() - startTime < 10000);
        timer.cancel();

    }


    /**
     * This method positions the vertices in a circle
     * @param vertices Array of vertices in the graph
     */
    private void initializePositions(Vertex[] vertices) {
        for (int i = 0; i < vertices.length; i++) {

            double angle = 2 * Math.PI * vertices[i].getNumber() / vertices.length;
            double x = centerX + radius/10 * Math.cos(angle);
            double y = centerY + radius/10 * Math.sin(angle);

            vertices[i].setX(x);
            vertices[i].setY(y);

        }
    }

    /**
     * Repulsive force method (Coulumb's law)
     * @param vertex The vertex to which the repulsive forces are being applied
     * @param vertices Array of vertices in the graph
     */
    private void applyRepulsiveForces(Vertex vertex, Vertex[] vertices) {
        for (Vertex other : vertices) {
            if (vertex != other) {
                double dx = vertex.getX() - other.getX(); //horizontal difference
                double dy = vertex.getY() - other.getY(); //vertical difference
                double distance = Math.sqrt(dx * dx + dy * dy);
                double force = REPULSION_CONSTANT / (distance * distance); //F=k/r^2
                vertex.addForce(dx / distance * force, dy / distance * force);
            }
        }
    }

    /**
     * Attractive force method (Hooke's law)
     * @param vertex The vertex to which the repulsive forces are being applied
     * @param edges Array of edges in the graph
     */
    private void applyAttractiveForces(Vertex vertex, Edge[] edges, Vertex[] vertices) {
        for (Edge edge : edges) {
            Vertex source = edge.getSource();
            Vertex dest = edge.getDestination();

            if (vertex == source || vertex == dest) {
                Vertex other;
                if (vertex == source) {
                    other = dest;
                } else {
                    other = source;
                }
                double dx = vertex.getX() - other.getX(); //horizontal difference
                double dy = vertex.getY() - other.getY(); //vertical difference
                double distance = Math.sqrt(dx * dx + dy * dy);
                if ((edges.length/vertices.length)<15) {
                    double force = SPRING_CONSTANT * (distance); //F=kx
                    vertex.addForce(-dx / distance * force, -dy / distance * force);
                } else if ((edges.length/vertices.length)<98) {
                    double force = SPRING_CONSTANT/10 * (distance); //F=kx
                    vertex.addForce(-dx / distance * force, -dy / distance * force);
                } else {
                    double force = SPRING_CONSTANT/100 * (distance); //F=kx
                    vertex.addForce(-dx / distance * force, -dy / distance * force);
                }
            }
        }
    }

    /**
     * This method updates positions of vertices and returns movement
     * @param vertex The vertex to which the repulsive forces are being applied
     * @return movement The distance caused by the applied forces
     */
    private double updatePosition(Vertex vertex) {
        double dx = vertex.getForceX()*DAMPING;
        double dy = vertex.getForceY()*DAMPING;
        double movement = Math.sqrt(dx*dx + dy*dy);
        vertex.setX(vertex.getX()+dx);
        vertex.setY(vertex.getY()+dy);
        vertex.resetForce(); //prepare for next iteration
        return movement;

    }
}

