package Main.widgets;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.util.Random;

public class Palette {
    //Number of vertices
    private int v;
    //Array containing all the colors of the palette
    private Color[] allColors;
    //Array containing all the colored rectangles of the palette
    private Rectangle[] allRectangles;
    //Last color clicked
    private Rectangle lastClicked = null;
    //Current color picked
    public Paint currentColor = Color.WHITE;
    //Current clicked rectangle
    private Rectangle currentRectangle;

    /**
     * Constructor of the palette
     *
     * @param v number of vertices
     */
    public Palette (int v) {
        this.v = v;
        this.allColors = generateColors(v);
        this.allRectangles = new Rectangle[v];
    }

    /**
     * getter method
     *
     * @return the current color picked
     */
    public Paint getCurrentColor() {
        return currentColor;
    }

    /**
     * getter method
     *
     * @return an array of all colored rectangles of the palette
     */
    public Rectangle[] getAllRectangles() {
        return allRectangles;
    }

    /**
     * getter method
     *
     * @return the current rectangle picked in the color palette
     */
    public Rectangle getCurrentRectangle() {
        return currentRectangle;
    }


    /**
     * Initialize the GUI's of the palette
     *
     * @param root main root of the program
     */
    public void initialize(GridPane root) {
        //We create the main pane that will containe every colored rectangles, and we set its properties
        GridPane rectanglesPanes = new GridPane();
        rectanglesPanes.setHgap(15);
        rectanglesPanes.setVgap(10);
        rectanglesPanes.setPrefWidth(200);
        rectanglesPanes.setPadding(new Insets(10));
        rectanglesPanes.setStyle("-fx-background-color: #FF53CD; -fx-background-radius: 20");

        //We set a scrollPane to scroll through all the colors and we set its properties
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStylesheets().add("ScrollBarStyle.css");
        scrollPane.setPrefHeight(840);
        scrollPane.setPrefWidth(220);
        scrollPane.setLayoutX(root.getWidth() - 220);
        scrollPane.setLayoutY(25);
        scrollPane.setContent(rectanglesPanes);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //We add the scrollPane to the main root
        root.addColumn(1, scrollPane);
        root.setPadding(new Insets(10));




        //Creating all the rectangles that will represent the colors in the palette
        for(int i = 0; i < allColors.length; i++){
            //We create rectangles that will contain the colors and we set their properties
            Rectangle rectangle2 = new Rectangle();
            rectangle2.setHeight(50);
            rectangle2.setWidth(80);
            rectangle2.setFill(allColors[i]);
            rectangle2.setLayoutX(root.getWidth() - 225);
            rectangle2.setLayoutY(25);
            rectangle2.setArcHeight(20);
            rectangle2.setArcWidth(20);
            rectangle2.setStyle("-fx-stroke : #FF53CD;");
            rectangle2.setStrokeWidth(4);
            OnPickingColor(rectangle2);

            if(i % 2 == 0){
                rectanglesPanes.addColumn(0, rectangle2);
            } else {
                rectanglesPanes.addColumn(1, rectangle2);
            }

            allRectangles[i] = rectangle2;

        }
    }

    /**
     * Method that executes when we click on a color rectangle
     *
     * @param rectangle2 the rectangle that is picked
     */
    public void OnPickingColor(Rectangle rectangle2) {
        rectangle2.setOnMouseClicked(mouseEvent -> {
            //We check whether the last rectangle clicked is null
            if(lastClicked != null){
                //We set the different color properties of the last and new clicked rectangle, and we set a new color to the currentColor variable
                lastClicked.setStyle("-fx-stroke : #FF53CD;");
                currentColor = rectangle2.getFill();
                rectangle2.setStyle("-fx-stroke : #000000;");
                lastClicked = rectangle2;
                currentRectangle = rectangle2;
            } else {
                //We set the different color properties of the new clicked rectangle, and we set a new color to the currentColor variable
                currentColor = rectangle2.getFill();
                rectangle2.setStyle("-fx-stroke : #000000;");
                lastClicked = rectangle2;
                currentRectangle = rectangle2;
            }
        });
    }

    /**
     * This method generates all the colors of the palette
     *
     * @param v number of vertices - then also the number of colors
     * @return an array of all the colors
     */
    private Color[] generateColors(int v) {
        Color[] colors = new Color[v];
        Random random = new Random();
        for (int i = 0; i < v; i++) {
            colors[i] = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
        return colors;
    }
}
