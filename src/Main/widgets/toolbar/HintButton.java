package Main.widgets.toolbar;

import Main.Utils.UndoHandler;
import Main.widgets.Palette;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class HintButton {
    //The pane containing all toolbar elements
    GridPane toolbar;
    //The palette object displayed in the game
    Palette palette;
    //Undohandler object
    UndoHandler undoHandler;
    //True if the user has to color from 1 to n number of vertices
    boolean ordering;
    //Matrix containing every relation between the vertices
    int[][] r;
    //Number of vertices
    int v;
    //Array containing every Circle object (vertices)
    Circle[] allCircles;
    //Hint button
    Button hintButton;


    /**
     * Constructor of HintButton object
     *
     * @param toolbar The pane containing all toolbar elements
     * @param palette The palette object displayed in the game
     * @param undoHandler Undohandler object
     * @param ordering True if the user has to color from 1 to n number of vertices
     * @param r Matrix containing every relation between the vertices
     * @param v Number of vertices
     * @param allCircles Array containing every Circle object (vertices)
     * @param hintButton Hint button
     */
    public HintButton(GridPane toolbar, Palette palette, UndoHandler undoHandler, boolean ordering, int[][] r, int v, Circle[] allCircles, Button hintButton){
        this.toolbar = toolbar;
        this.palette = palette;
        this.undoHandler = undoHandler;
        this.ordering = ordering;
        this.r = r;
        this.v = v;
        this.allCircles = allCircles;
        this.hintButton = hintButton;
    }

    /**
     * Initializes the Hint button
     */
    public void Initialize() {

        //We set up properties of the hint button
        hintButton.setText("Hint");
        hintButton.getStyleClass().add("myButton");
        toolbar.addColumn(2, hintButton);

        //What happen if we click on hint button
        hintButton.setOnAction(event -> {
            //This array stores every color rectangles of the palette.
            Rectangle[] rectangles = palette.getAllRectangles();

            //This is the index of the focused circle
            int circleIndex = undoHandler.focusedIndex;

            //We first reset every stroke of the rectangles to the initial color if the rectangle is not the color picked
            for(int i = 0; i < rectangles.length; i++){
                if(rectangles[i] != palette.getCurrentRectangle()){
                    rectangles[i].setStyle("-fx-stroke : #FF53CD;");
                }
            }

            //This array will store every colors that the focused vertex can't take
            ArrayList<Paint> notColors = new ArrayList<>();
            /*
            If the ordering is true, we give the user the color the user can use to color the next focused vertex

            If the ordering is false, we pick the next uncolored vertex starting from index 0
             */
            if(ordering == true){

                /*
                We check for the focused vertex in the r array

                If it is related to vertex that are colored we store that color in the notColor array
                 */
                for(int i = 0; i < r.length; i++){
                    if(r[i][0] == circleIndex){
                        notColors.add(allCircles[r[i][1]].getFill());
                    }
                    if(r[i][1] == circleIndex){
                        notColors.add(allCircles[r[i][0]].getFill());
                    }
                }

                //We check for a rectangle that has a color that is not stored in the notColor array starting from 0 and we highlight it in white
                for(int i = 0; i < rectangles.length; i++){
                    if(!(notColors.contains(rectangles[i].getFill()))){
                        rectangles[i].setStyle("-fx-stroke : #FFFFFF;");
                        break;
                    }
                }
            } else {
                //We create a random index
                int randomIndexNotUsed = -1;
                //We pick an uncolored vertex starting from index 0
                for(int i = 0; i < v; i++){
                    if(allCircles[i].getFill() == Color.WHITE){
                        randomIndexNotUsed = i;
                        break;
                    }
                }

                //We focus the vertex in grey
                allCircles[randomIndexNotUsed].setStroke(Color.GREY);

                /*
                We check for the focused vertex in the r array

                If it is related to vertex that are colored we store that color in the notColor array
                 */
                for(int i = 0; i < r.length; i++){
                    if(r[i][0] == randomIndexNotUsed){
                        notColors.add(allCircles[r[i][1]].getFill());
                    }
                    if(r[i][1] == randomIndexNotUsed){
                        notColors.add(allCircles[r[i][0]].getFill());
                    }
                }

                //We check for a rectangle that has a color that is not stored in the notColor array starting from 0 and we highlight it in white
                for(int i = 0; i < rectangles.length; i++){
                    if(!(notColors.contains(rectangles[i].getFill()))){

                        rectangles[i].setStyle("-fx-stroke : #FFFFFF;");
                        break;
                    }
                }

            }
        });
    }
}
