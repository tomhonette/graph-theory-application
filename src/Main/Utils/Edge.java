package Main.Utils;

public class Edge {
    //The vertice from where the line goes
    Vertex source;
    //The vertice where the line goes
    Vertex destination;

    /**
     * Constructor of the Edge object
     *
     * @param source The vertice from where the line goes
     * @param destination The vertice where the line goes
     */
    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Getter method
     *
     * @return the vertice from where the line goes
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Getter method
     *
     * @return the vertice where the line goes
     */
    public Vertex getDestination() {
        return destination;
    }
}
