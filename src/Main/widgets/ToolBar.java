package Main.widgets;

import Main.Utils.UndoHandler;
import Main.structure.MenuPanes;
import Main.graphGeneration.Graph;
import Main.widgets.toolbar.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class ToolBar{
    private ScrollPane scrollPane;
    private StackPane stackPane;
    Button point= new Button("Results");
    UndoHandler undoHandler = UndoHandler.undoHandler;
    public boolean canColor = false;
    public Circle[] allCircles;

    /**
     * constructor of ToolBar object
     *
     * @param scrollPane
     * @param stackPane
     */
    public ToolBar(ScrollPane scrollPane, StackPane stackPane) {
        this.scrollPane = scrollPane;
        this.stackPane = stackPane;
    }

    Button giveUp = new Button();
    Button startButton = new Button("Start");
    Button undoButton = new Button();
    Button hintButton = new Button();
    Button explain= new Button ("Help");
    static Button points = new Button("Results");
    Button forces = new Button();


    /**
     * Initializing the structure of the toolbar and its buttons
     *
     * @param v the number of vertices
     * @param e the number of edges
     * @param r the relations between the edges and vertices
     * @param canUndo whether the user can undo his colorings or not
     * @param mainStage the main stage of the program
     */

    public void initialize(int v, int e, int[][] r, boolean canUndo, boolean ordering, Stage mainStage, Palette palette, String graphType) {
        //We create the pane where every element of the toolbar will be displayed
        GridPane toolbar = new GridPane();
        //We set the properties of the pane
        toolbar.setAlignment(Pos.CENTER);
        toolbar.toFront();
        toolbar.setHgap(20);
        toolbar.setMaxHeight(50);
        toolbar.setMaxWidth(scrollPane.getPrefWidth() - 100);
        toolbar.setStyle("-fx-background-color: #FF53CD; -fx-background-radius: 5");
        stackPane.getChildren().add(toolbar);
        StackPane.setAlignment(toolbar, Pos.TOP_CENTER);

        //We set up the timer
        SimpleIntegerProperty counter = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            counter.set(counter.get() + 1);
        }));

        //We create the buttons object that will be displayed on the toolbar
        ForcesButton forcesButton = new ForcesButton(forces, toolbar);
        GiveUpButton giveUpButton = new GiveUpButton(toolbar, timeline, hintButton, undoButton, startButton, points, undoHandler, mainStage, counter, giveUp);
        StartButton startButtonObj = new StartButton(toolbar, this, undoButton, hintButton, giveUp, counter, timeline, startButton, forces);
        HelpButton helpButton = new HelpButton(toolbar, explain);
        UndoButton undoButtonObj = new UndoButton(canUndo, undoHandler, toolbar, undoButton);
        HintButton hintButtonObj = new HintButton(toolbar, palette, undoHandler,ordering, r, v, allCircles, hintButton);


        //We initialize them by calling their Initialize() method
        giveUpButton.Initialize();
        startButtonObj.Initialize();
        hintButtonObj.Initialize();
        forcesButton.Initialize();
        helpButton.Initialize();
        undoButtonObj.Initialize();

        //This text displays the special graph if there is one detected by our algorithm
        Text text = new Text(graphType);
        toolbar.addColumn(26, text);
        text.getStyleClass().add("textPixel");



    }


    /**
     * Reveals the point button
     */
    public void revealPoints() {
        points.setVisible(true);
    }

    /**
     * Unreveals the point button
     */
    public static void unRevealPoints() {
        points.setVisible(false);
    }
}