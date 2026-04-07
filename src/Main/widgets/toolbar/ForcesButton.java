package Main.widgets.toolbar;

import Main.graphGeneration.GraphDrawing;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ForcesButton {
    //Forces button
    Button forces;
    //The pane containing all toolbar elements
    GridPane toolbar;
    //true if the forces button is activated
    boolean isActivated = false;
    //GraphDrawing object
    GraphDrawing graphDrawing;


    /**
     * Constructor of ForcesButton object
     *
     * @param forces Forces button
     * @param toolbar The pane containing all toolbar elements
     */
    public ForcesButton(Button forces, GridPane toolbar) {
        this.forces = forces;
        this.toolbar = toolbar;
    }

    /**
     * Initializes the forces button
     */
    public void Initialize() {

        //We set the properties of the button
        forces.setText("forces off");
        forces.getStyleClass().add("forces");
        toolbar.addColumn(3, forces);
        //We set the graphDrawing variable to the graphDrawing object we currently use
        graphDrawing = GraphDrawing.graphDrawing;

        //What happens when we click on the force button
        forces.setOnAction(actionEvent -> {
            /*
            If the button has already been clicked we stop the timer which calls the repel and attraction forces

            otherwise we start the timer
             */
            if(isActivated == false){
                graphDrawing.timer.start();
                forces.setText("forces on");
                isActivated = true;
            } else {
                graphDrawing.timer.stop();
                forces.setText("forces off");
                isActivated = false;
            }
        });
    }
}
