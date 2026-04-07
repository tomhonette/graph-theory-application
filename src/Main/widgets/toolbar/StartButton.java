package Main.widgets.toolbar;

import Main.widgets.ToolBar;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class StartButton {
    //The pane containing all toolbar elements
    GridPane toolbar;
    //The toolbar object displayed in the game
    ToolBar toolBar2;
    //Undo button
    Button undoButton;
    //Hint button
    Button hintButton;
    //GiveUp button
    Button giveUp;
    //Counter of the timer
    SimpleIntegerProperty counter;
    //Timeline object handling the timer
    Timeline timeline;
    //Start button
    Button startButton;
    //Forces button
    Button forces;

    /**
     * Constructor of StartButton object
     *
     * @param toolbar The pane containing all toolbar elements
     * @param toolBar2 The toolbar object displayed in the game
     * @param undoButton Undo button
     * @param hintButton Hint button
     * @param giveUp Give up button
     * @param counter Counter of the timer
     * @param timeline Timeline object handling the timer
     * @param startButton Start button
     * @param forces Forces button
     */
    public StartButton(GridPane toolbar,ToolBar toolBar2, Button undoButton, Button hintButton, Button giveUp, SimpleIntegerProperty counter, Timeline timeline, Button startButton, Button forces) {
        this.toolBar2 = toolBar2;
        this.undoButton = undoButton;
        this.hintButton = hintButton;
        this.giveUp = giveUp;
        this.counter = counter;
        this.timeline = timeline;
        this.startButton = startButton;
        this.toolbar = toolbar;
        this.forces = forces;
    }

    /**
     * Initializes the Start button
     */
    public void Initialize() {
        //We set properties of start button
        startButton.getStyleClass().add("startButton");

        // This text displays the time the user takes to color the graph
        Text time = new Text();
        Text seconds = new Text("seconds");
        toolbar.addColumn(6, startButton);
        toolbar.addColumn(9, time);

        //What happens if we click on start button
        startButton.setOnAction(startEvt -> {
            //We switch the canColor variable to true which will let the user color the vertices
            toolBar2.canColor = true;
            //We show the buttons we should and hide start button
            undoButton.setVisible(true);
            hintButton.setVisible(true);
            giveUp.setVisible(true);
            startButton.setVisible(false);
            forces.setVisible(true);

            //We set the timer to O and loop it indefinitely until we stop it
            counter.set(0);
            timeline.setCycleCount(Timeline.INDEFINITE);
            //Sticking the timer to the text
            time.textProperty().bind(Bindings.createStringBinding(() -> Integer.toString(counter.get()), counter));
            //Starting the timer
            timeline.play();
            toolbar.addColumn(8, seconds);

        });
    }
}
