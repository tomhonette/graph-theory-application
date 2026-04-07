package Main.widgets.toolbar;

import Main.Utils.UndoHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class UndoButton {
    //True if the user can undo its colorings
    boolean canUndo;
    //Undohandler button
    UndoHandler undoHandler;
    //The pane containing all toolbar elements
    GridPane toolbar;
    //Undo button
    Button undoButton;


    /**
     * Constructor of UndoButton object
     *
     * @param canUndo True if the user can undo its colorings
     * @param undoHandler Undohandler button
     * @param toolbar The pane containing all toolbar elements
     * @param undoButton Undo button
     */
    public UndoButton(boolean canUndo, UndoHandler undoHandler, GridPane toolbar, Button undoButton) {
        this.canUndo = canUndo;
        this.undoHandler = undoHandler;
        this.toolbar = toolbar;
        this.undoButton = undoButton;
    }

    /**
     * Initializes the Undo button
     */
    public void Initialize() {
        //We set properties of undo button
        undoButton.getStyleClass().add("myButton");
        undoButton.setText("Undo");
        //We first make sure that we can undo even if the button should not be displayed
        if(canUndo == true) {
            toolbar.addColumn(1, undoButton);
            undoButton.setOnAction(event -> {
                //We call the undo method from the undoHandler
                undoHandler.Undo();
            });
        }
    }
}
