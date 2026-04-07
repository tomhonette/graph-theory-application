package Main.graphGeneration;

import Main.Utils.Drag;
import Main.Utils.Edge;
import Main.Utils.UndoHandler;
import Main.Utils.Vertex;
import Main.widgets.Palette;
import Main.widgets.ToolBar;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;

public class GraphDrawing {
    private final Graph graph;
    private final double centerX;
    private final double centerY;
    private final double radius;
    private final boolean ordering;
    private ToolBar toolBar;
    private final double stageWidth;
    private final double stageHeight;
    public AnimationTimer timer;
    public static GraphDrawing graphDrawing;

    /**
     *
     * @param graph
     * @param stageWidth
     * @param stageHeight
     * @param centerX
     * @param centerY
     * @param radius
     * @param ordering
     * @param toolBar
     */
    public GraphDrawing(Graph graph, double stageWidth, double stageHeight, double centerX, double centerY, double radius, boolean ordering, ToolBar toolBar) {
        this.graph = graph;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.ordering = ordering;
        this.toolBar = toolBar;
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
    }

    /**
     * This method updates positions of the edges
     * @param vertices Array of vertices represented as circles
     * @param edges Array of edges represented as lines
     */
    public void updatedge(Circle[] vertices,Line[] edges){
        for(int i = 0 ; i<edges.length;i++){
            double startx = vertices[graph.getEdges()[i][0]].getCenterX();
            double starty = vertices[graph.getEdges()[i][0]].getCenterY();
            double endx = vertices[graph.getEdges()[i][1]].getCenterX();
            double endy = vertices[graph.getEdges()[i][1]].getCenterY();
            edges[i].setStartX(startx);
            edges[i].setStartY(starty);
            edges[i].setEndX(endx);
            edges[i].setEndY(endy);
        }
    }

    /**
     *
     * @param palette Palette of the program
     * @return graphGroup object containing the graphical representation of the graph
     */
    public Group draw(Palette palette) {
        graphDrawing = this;
        UndoHandler undoHandler = UndoHandler.undoHandler;

        Group graphGroup = new Group();

        // Calculate positions for vertices

        Text[] texts = new Text[graph.getVerticesCount()];


        Vertex[] vertices= new Vertex[graph.getVerticesCount()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i, centerX, centerY);
        }


        Edge[] edgesss= new Edge[graph.getEdgesCount()];
        for (int i = 0; i < edgesss.length; i++) {
            int source= graph.getEdges()[i][0];
            int destination= graph.getEdges()[i][1];
            edgesss[i]= new Edge(vertices[source], vertices[destination]);
        }

        ForceDirected forceDirected = new ForceDirected(centerX, centerY, radius);
        forceDirected.applyForceDirectedLayout(vertices, edgesss);

        //scaling the vertices, keeping them closer to each other and within bounds
        double minX = 0;
        double minY = 0;
        double maxX = 0;
        double maxY = 0;

        for (int i = 0; i< vertices.length; i++) {
            double x, y;
            x = vertices[i].getX();
            y = vertices[i].getY();

            //counting min and max x-coordinate and y-coordinate
            if (i == 0) {
                minX = x;
                minY = y;
                maxX = x;
                maxY = y;
            } else {
                minX = Math.min(minX, x);
                minY = Math.min(minY, y);
                maxX = Math.max(maxX,x);
                maxY = Math.max(maxY,y);
            }
        }

        //0=a*xmin+b - 0 is the left border
        //2*centerX=a*xmax+b - 2*centerX is the right border
        //

        //smaller graphs
        if (vertices.length < 50) {
            double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 300;
            double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 300;
            double ax = WIDTH / (maxX - minX);
            double bx = -ax * minX + 30;
            double ay = HEIGHT / (maxY - minY);
            double by = -ay * minY + 80;

            //setting the new coordinates
            for (int i = 0; i < vertices.length; i++) {
                double x, y;
                x = vertices[i].getX();
                y = vertices[i].getY();
                vertices[i].setX(ax * x + bx + centerX - (WIDTH/2));
                vertices[i].setY(ay * y + by);
            }
        }
        //bigger graphs
        else {
            double WIDTH = stageWidth - 100;
            double HEIGHT = stageHeight - 100;
            double ax = WIDTH / (maxX - minX);
            double bx = -ax * minX + 30;
            double ay = HEIGHT / (maxY - minY);
            double by = -ay * minY + 30;

            //setting the new coordinates
            for (int i = 0; i < vertices.length; i++) {
                double x, y;
                x = vertices[i].getX();
                y = vertices[i].getY();
                vertices[i].setX(ax * x + bx + centerX - (WIDTH/2));
                vertices[i].setY(ay * y + by + centerY - (HEIGHT/2));
            }
        }

        //edges
        Line[] edgess = new Line[graph.getEdgesCount()];
        int count = 0;
        for (int[] edge : graph.getEdges()) {
            int source = edge[0];
            int dest = edge[1];

            Vertex vSource = vertices[source];
            Vertex vDest = vertices[dest];

            Line line = new Line(
                    vSource.getX(), vSource.getY(),
                    vDest.getX(), vSource.getY()
            );
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(2);
            edgess[count] = line;
            count++;
            graphGroup.getChildren().add(line);
        }


        //circles for the vertices:
        Circle[] circles= new Circle[vertices.length];

        //draw vertices
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            double x,y;

            x = vertices[i].getX();
            y = vertices[i].getY();

            //We set the properties of the vertices
            circles[i] = new Circle(x, y, 15);
            circles[i].setFill(Color.WHITE);
            circles[i].setStroke(Color.BLACK);
            circles[i].setStrokeWidth(2);

            //We set the properties of the texts
            texts[i] = new Text(""+(i + 1));
            texts[i].setTextAlignment(TextAlignment.CENTER);
            texts[i].setX(x - 5);
            texts[i].setY(y + 5);
            texts[i].setFill(Color.BLACK);

            //We create the drag object to allow drag and dropping vertices
            new Drag(circles[i], texts[i]);

            int finalI = i;
            texts[i].setOnMouseClicked(event -> {
                //We check if the game has already started
                if(toolBar.canColor == true){
                    if(ordering == false || undoHandler.focusedVertix == circles[finalI]){
                        Paint currentColor = palette.getCurrentColor();
                        int[][] edges = graph.getEdges();
                        boolean CheckIfCanColor = false;
                        //We check the relations of the vertex we want to color to be sure we can color it
                        for(int j = 0; j < edges.length; j++){
                            if(edges[j][0] == finalI){
                                if(circles[edges[j][1]].getFill() == currentColor){
                                    CheckIfCanColor = false;
                                    break;
                                } else {
                                    CheckIfCanColor = true;
                                }
                            } else if(edges[j][1] == finalI){
                                if(circles[edges[j][0]].getFill() == currentColor){
                                    CheckIfCanColor = false;
                                    break;
                                } else {
                                    CheckIfCanColor = true;
                                }
                            }
                        }


                        if(CheckIfCanColor == true){
                            //We add the coloring the color history
                            undoHandler.AddColoring(currentColor);
                            //We set the new properties of the circle
                            circles[finalI].setFill(currentColor);
                            circles[finalI].setStroke(Color.BLACK);
                            if(ordering == true){
                                //We increment the focus index to focus the next vertex
                                undoHandler.focusedIndex++;
                                if(undoHandler.focusedIndex <= circles.length - 1){
                                    //We set the new focused vertes
                                    undoHandler.focusedVertix = circles[undoHandler.focusedIndex];
                                    undoHandler.focusedVertix.setStroke(Color.GREY);
                                }
                                //We add the circle that is colored to the circleHistory
                                undoHandler.AddCircle(circles[finalI]);
                                undoHandler.currentCircleIndex++;
                            }
                        } else {
                            circles[finalI].setStroke(Color.RED);
                        }
                    }
                    //We check if the graph is all colored and if it is we reveal the result button
                    boolean reveal = false;
                    for(int j = 0; j < circles.length; j++){

                        if(circles[j].getFill() != Color.WHITE){
                            reveal = true;
                        } else {
                            reveal = false;
                            break;
                        }
                    }
                    if(reveal == true){
                        toolBar.revealPoints();
                    }
                } else {
                    if(palette.currentColor != Color.WHITE){
                        //if the game has not started we display a little screen saying the user has to start the game first
                        GridPane gridPane = new GridPane();
                        Stage stage = new Stage();
                        stage.setWidth(500);
                        stage.setHeight(200);
                        Text text = new Text("You need to start the game to colour vertices!");
                        gridPane.addColumn(0, text);
                        gridPane.setAlignment(Pos.CENTER);
                        text.getStyleClass().add("textPixel");
                        gridPane.getStyleClass().add("backgroundStyle");
                        Scene scene = new Scene(gridPane, 500, 200);
                        scene.getStylesheets().add("Bar.css");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });
            circles[i].setOnMouseClicked(event -> {
                if(toolBar.canColor == true){
                    if(ordering == false || undoHandler.focusedVertix == circles[finalI]){
                        Paint currentColor = palette.getCurrentColor();
                        int[][] edges = graph.getEdges();
                        boolean CheckIfCanColor = false;
                        //We check the relations of the vertex we want to color to be sure we can color it
                        for(int j = 0; j < edges.length; j++){
                            if(edges[j][0] == finalI){
                                if(circles[edges[j][1]].getFill() == currentColor){
                                    CheckIfCanColor = false;
                                    break;
                                } else {
                                    CheckIfCanColor = true;
                                }
                            } else if(edges[j][1] == finalI){
                                if(circles[edges[j][0]].getFill() == currentColor){
                                    CheckIfCanColor = false;
                                    break;
                                } else {
                                    CheckIfCanColor = true;
                                }
                            }
                        }


                        if(CheckIfCanColor == true){
                            //We add the coloring the color history
                            undoHandler.AddColoring(currentColor);
                            //We set the new properties of the circle
                            circles[finalI].setFill(currentColor);
                            circles[finalI].setStroke(Color.BLACK);
                            if(ordering == true){
                                //We increment the focus index to focus the next vertex
                                undoHandler.focusedIndex++;
                                if(undoHandler.focusedIndex <= circles.length - 1){
                                    //We set the new focused vertes
                                    undoHandler.focusedVertix = circles[undoHandler.focusedIndex];
                                    undoHandler.focusedVertix.setStroke(Color.GREY);
                                }
                                //We add the circle that is colored to the circleHistory
                                undoHandler.AddCircle(circles[finalI]);
                                undoHandler.currentCircleIndex++;
                            }
                        } else {
                            circles[finalI].setStroke(Color.RED);
                        }
                    }
                    //We check if the graph is all colored and if it is we reveal the result button
                    boolean reveal = false;
                    for(int j = 0; j < circles.length; j++){

                        if(circles[j].getFill() != Color.WHITE){
                            reveal = true;
                        } else {
                            reveal = false;
                            break;
                        }
                    }
                    if(reveal == true){
                        toolBar.revealPoints();
                    }
                } else {
                    if(palette.currentColor != Color.WHITE){
                        //if the game has not started we display a little screen saying the user has to start the game first
                        GridPane gridPane = new GridPane();
                        Stage stage = new Stage();
                        stage.setWidth(500);
                        stage.setHeight(200);
                        Text text = new Text("You need to start the game to colour vertices!");
                        gridPane.addColumn(0, text);
                        gridPane.setAlignment(Pos.CENTER);
                        text.getStyleClass().add("textPixel");
                        gridPane.getStyleClass().add("backgroundStyle");
                        Scene scene = new Scene(gridPane, 500, 200);
                        scene.getStylesheets().add("Bar.css");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });
            graphGroup.getChildren().add(circles[i]);
            graphGroup.getChildren().add(texts[i]);
        }

        //We add the circle to the allCircle variable from toolbar
        toolBar.allCircles = circles;
        //If the ordering is true we set the focus vertex to the first vertex
        if(ordering == true){
            undoHandler.focusedVertix = circles[0];
            undoHandler.focusedVertix.setStroke(Color.GREY);
        }

        double[][] velocity = new double[circles.length][2];//Array that keeps track of all the forces added to nodes

        updatePosistions(circles, velocity, texts);
        updatedge(circles, edgess);

        // Animation Timer that updates the edges and vertices based on the forces
        timer = new AnimationTimer() {
            @Override
            public void handle(long now){

                double[][] velocity = new double[circles.length][2];//Array that keeps track of all the forces added to nodes

                Attract(edgess, circles, velocity);
                Repel(circles,velocity);
                updatePosistions(circles, velocity, texts);
                updatedge(circles, edgess);
            }
        };

        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long now){
                double[][] velocity = new double[circles.length][2];//Array that keeps track of all the forces added to nodes

                updatePosistions(circles, velocity, texts);
                updatedge(circles, edgess);
            }
        };

        timer2.start();


        return graphGroup;
    }

    /**
     * Method for reppeling force
     *
     * @param vertices All vertices
     * @param velocity velocity of the force
     */
    public void Repel(Circle[] vertices , double[][] velocity){
        for(int i = 0;i<velocity.length;i++){
            for(int j = 0;j<2;j++){
                velocity[i][j] = 0;
            }
        }
        for(int i = 0 ; i<vertices.length;i++){
            for(int j = i+1 ; j<vertices.length;j++){
                double dx = vertices[j].getCenterX() - vertices[i].getCenterX();
                double dy = vertices[j].getCenterY() - vertices[i].getCenterY();
                double distance = Math.sqrt(dy*dy + dx*dx); //Formula for finding the distance between two nodes
                if(distance < 1) distance = 1;
                double force = 900/(distance*distance); //Coloumbs Law formula
                if(distance > 100) force = 0;
                velocity[i][0] -=  force;
                velocity[i][1] -=  force;
                velocity[j][0] +=  force;
                velocity[j][1] +=  force;


            }
        }
    }

    /**
     * Method that updates the vertice positions
     *
     * @param vertices All vertices
     * @param velocity velocity of the force
     * @param text All text contained in the vertices
     */
    public void updatePosistions(Circle[] vertices , double[][] velocity,Text[] text){
        for(int i = 0 ;i<vertices.length;i++){
            double newxposition = vertices[i].getCenterX() + velocity[i][0];
            double newyposition = vertices[i].getCenterY() +velocity[i][1];
            vertices[i].setCenterX(newxposition);
            vertices[i].setCenterY(newyposition);
            text[i].setX(vertices[i].getCenterX() - 5);
            text[i].setY(vertices[i].getCenterY() + 5);
        }
        for(int i = 0;i<velocity.length;i++){
            for(int j = 0;j<2;j++){
                velocity[i][j] = 0;}
        }

    }

    /**
     * Method for the attraction force
     *
     * @param edges All edges
     * @param vertices All vertices
     * @param v Velocity of the force
     */
    public void Attract(Line[] edges,Circle[] vertices, double[][]v){

        for(int i = 0 ; i<edges.length;i++){
            double vertice1posx = 0;
            double vertice1posy = 0;
            double vertice2posx = 0;
            double vertice2posy = 0;
            int rindexv1 = 0; // variable that remembers index for the first node
            int rindexv2 = 0; // variable that remembers index for the second node
            for(int j = 0 ; j<vertices.length;j++){ //loop for finding connected nodes
                if(edges[i].getStartX()==vertices[j].getCenterX() && edges[i].getStartY()==vertices[j].getCenterY()){
                    vertice1posx = vertices[j].getCenterX();
                    vertice1posy = vertices[j].getCenterY();
                    rindexv1 = j;
                }
            }

            for(int j = 0 ; j<vertices.length;j++){ //loop for finding connected nodes
                if(edges[i].getEndX()==vertices[j].getCenterX() && edges[i].getEndY()==vertices[j].getCenterY()){
                    vertice2posx = vertices[j].getCenterX();
                    vertice2posy = vertices[j].getCenterY();
                    rindexv2 = j;
                }

            }


            double dx = vertice2posx - vertice1posx ;
            double dy = vertice2posy - vertice1posy ;
            double distance = Math.sqrt(dy*dy + dx*dx); //Formula for finding the distance between two nodes
            if(distance < 1) distance = 1;
            double force = 0.5*distance; // Hookes Law
            v[rindexv1][0] += -dx /distance * force;
            v[rindexv1][1] += -dy / distance * force;
            v[rindexv2][0] += -dx /distance * force;
            v[rindexv2][1] += -dy / distance * force;



        }
    }
}


