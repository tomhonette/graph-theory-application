package Main.Utils;

import Main.graphGeneration.Graph;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class Drag {
    //The circle that is dragged
    private Circle vertex;
    //The text contained in the circle
    private Text text;

    /**
     * This method allows the user to drag and drop a vertice
     *
     * @param vertex The circle that is dragged
     * @param text The text contained in the circle
     */
    public Drag(Circle vertex, Text text) {
        this.vertex = vertex;
        this.text = text;
        vertex.setOnMouseDragged(this::handleMouseDragged);
    }

    /**
     * This method handle the drag and drop of the vertice
     *
     * @param event event of drag and dropping
     */
    public void handleMouseDragged(MouseEvent event) {
        //We set the new values x and y of the text and the vertice
        text.setX(event.getX() - 5);
        text.setY(event.getY() + 5);
        vertex.setCenterX(event.getX());
        vertex.setCenterY(event.getY());

    }


}
