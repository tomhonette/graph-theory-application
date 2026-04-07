package Main.structure;

import Main.graphGeneration.Graph;
import Main.graphGeneration.VerticesController;
import Main.widgets.Palette;
import Main.widgets.ToolBar;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

public class GamePanes {

    /**
     * constructor of GamePanes object
     */
    public GamePanes() { }

    /**
     * This method initializes the structure of the game screen
     *
     * @param canUndo true if the user can undo coloring with the undo button
     * @param ordering true if the user has to color from 1 to n number of vertices
     * @param mainstage the main stage of the program
     * @param v the number of vertices
     * @param e the number of edges
     */
    public void InitializeStructure(boolean canUndo, boolean ordering, Stage mainstage, int v, int e) {

        //We create the root of the game and setting its ID
        GridPane root = new GridPane();
        root.setId("Back");
        root.setHgap(10);

        //We create a new scene to apply the GUI's of the game
        Scene scene = new Scene(root, mainstage.getWidth(), mainstage.getHeight());
        scene.getStylesheets().add("Generated.css");
        scene.getStylesheets().add("Bar.css");
        mainstage.setScene(scene);

        //We set a new name to the main stage
        mainstage.setTitle("Graph Coloring");
        mainstage.show();

        //This stackpane is here to stack the toolbar over the scrollPane
        StackPane stackPane = new StackPane();

        //This pane contains all the graph IN the scrollPane
        Pane scrollRoot = new Pane();
        scrollRoot.setPrefWidth(3000);
        scrollRoot.setPrefHeight(3000);

        //We create the scrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 220);
        stackPane.getChildren().add(scrollPane);
        scrollPane.setHvalue(0.5);
        //setting the backgrounds transparent to see the custom image background
        stackPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollRoot.setStyle("-fx-background-color: transparent;");


        //We create the ToolBar object
        ToolBar toolBar = new ToolBar(scrollPane, stackPane);
        //We create the Palette object
        Palette palette = new Palette(v);
        //We create the VerticesController object and initialize it
        VerticesController verticesController = new VerticesController(scrollRoot, palette, v, e, scrollRoot.getPrefWidth() / 2, scrollRoot.getPrefHeight() / 2, 400);
        verticesController.initialize(ordering, toolBar);
        //We initialize the palette
        palette.initialize(root);

        //We set the content of the scrollRoot to the scrollPane
        scrollPane.setContent(scrollRoot);

        //We add the stackPane to the root
        root.addColumn(0, stackPane);

        //We initialize the toolbar
        toolBar.initialize(v, e, verticesController.getEdges(), canUndo, ordering,  mainstage, palette, Graph.graphType);
    }
}