package Main.Utils;

import Main.widgets.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class UndoHandler {
    //The history of all clicked vertices during the game
    public ArrayList<Circle> verticesHistory = new ArrayList<>();
    //The history if all clicked colors during the game
    public ArrayList<Paint> colorHistory = new ArrayList<>();
    //The current Circle (vertice) that is clicked
    public int currentCircleIndex = -1;
    //The vertice that is focused in grey stroke
    public Circle focusedVertix = null;
    //The index of the vertice that is focused
    public int focusedIndex = 0;
    //The getter of the undoHandler. We needed an universal getter for all the game
    public static UndoHandler undoHandler;

    /**
     * constructor of UndoHandler object
     */
    public UndoHandler() { }

    /**
     * Adds a color to the colorHistory array
     *
     * @param color The color picked when the circle is colored
     */
    public void AddColoring(Paint color) {
        this.colorHistory.add(color);
    }

    /**
     * Adds a color to the verticesHistory array
     *
     * @param newCircle The new circle that is colored
     */
    public void AddCircle(Circle newCircle) {
        this.verticesHistory.add(newCircle);
    }

    /**
     * Resets all the variables of the UndoHandler to default
     */
    public void Reset(){
        verticesHistory.clear();
        colorHistory.clear();
        currentCircleIndex = -1;
        focusedVertix = null;
        focusedIndex = 0;
        undoHandler = new UndoHandler();
    }


    /**
     * Undo and action
     */
    public void Undo() {
        ToolBar.unRevealPoints();
        //We check if the undo action can be done (e.g. if there is no vertices already colored)
        if(!this.verticesHistory.isEmpty() && !this.colorHistory.isEmpty() && currentCircleIndex >= 0) {
            //We set the current circle to the one colored beforehand
            Circle currentCircle = verticesHistory.get(this.currentCircleIndex);
            //We set the new properties of the circle colored and last circle colored
            currentCircle.setFill(Color.WHITE);
            currentCircle.setStroke(Color.GREY);
            focusedVertix.setStroke(Color.BLACK);

            //We delete the last circle of vertice and color history
            verticesHistory.remove(this.currentCircleIndex);
            colorHistory.remove(this.currentCircleIndex);

            //We increment all of our indexes variable
            focusedIndex--;
            focusedVertix = currentCircle;
            currentCircleIndex--;


        }
    }

}
