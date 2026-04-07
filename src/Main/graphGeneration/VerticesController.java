package Main.graphGeneration;

import Main.widgets.Palette;
import Main.widgets.ToolBar;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class VerticesController {
    //Main root of the program
    public Pane root;
    //Palette of the program
    public Palette palette;
    //Number of vertices
    private int v;
    //Number of edges
    private int e;
    //Center of the graph on the abscissa axis
    private double center_x;
    //Center of the graph on the ordinate axis
    private double center_y;
    //Radius of the circle graph
    private double radius;
    //Graph object
    Graph graph;
    //TODO add scrollpane to center when bigger graphs


    /**
     * Constructor of the VerticesController object
     *
     * @param root Main root of the program
     * @param palette Palette of the program
     * @param v Number of vertices
     * @param e Number of edges
     * @param center_x Center of the graph on the abscissa axis
     * @param center_y Center of the graph on the ordinate axis
     * @param radius Radius of the circle graph
     */
    public VerticesController(Pane root, Palette palette, int v, int e, double center_x, double center_y, double radius) {
        this.root = root;
        this.palette = palette;
        this.v = v;
        this.e = e;
        this.radius = radius;
        this.center_x = center_x;
        this.center_y = center_y;
        this.graph = new Graph(v, e);
    }

    /**
     * Getter method
     *
     * @return a matrix with all the relations between the edges of the graph
     */
    public int[][] getEdges() {
        return graph.getEdges();
    }

    /**
     * Initializes the drawing of the graph
     *
     * @param ordering Whether the graph is ordered from 1 to n number of vertices
     * @param toolBar the toolbar of the game
     */
    public void initialize(boolean ordering, ToolBar toolBar) {

        GraphDrawing drawing = new GraphDrawing(graph, root.getPrefWidth(), root.getPrefHeight(), center_x, center_y, radius, ordering, toolBar);
        Group graphGroup = drawing.draw(this.palette);

        //We clear all the children and add the new graph
        root.getChildren().clear();
        root.getChildren().add(graphGroup);
    }
}
