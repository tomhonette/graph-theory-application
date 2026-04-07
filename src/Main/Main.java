package Main;

import Main.Utils.UndoHandler;
import Main.structure.MenuPanes;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }


    @Override
    public void start(Stage stage) {
        // This method includes the initialization of the main stage and the main root that will be used throughout all this project
        BorderPane root = new BorderPane();
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setResizable(false);

        // We create a new object MenuPanes that starts the initialization of the main structure of the GUI's
        MenuPanes menuPanes = new MenuPanes(stage, root);
        menuPanes.Initialize();
        // We reset the UndoHandler first to avoid conflict between old values and the default values
        UndoHandler undoHandler = new UndoHandler();
        undoHandler.Reset();

        stage.setTitle("Graph Visualizer");
        stage.show();
    }
}