package Main.widgets.toolbar;

import Main.Utils.UndoHandler;
import Main.graphGeneration.Graph;
import Main.structure.MenuPanes;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GiveUpButton {
    //The pane containing all toolbar elements
    GridPane toolbar;
    //Timeline object handling the timer
    Timeline timeline;
    //Hint button
    Button hintButton;
    //Undo button
    Button undoButton;
    //Start button
    Button startButton;
    //Result button
    Button point;
    //Undohandler object
    UndoHandler undoHandler;
    //Main stage of the program
    Stage mainStage;
    //Counter of the timer
    SimpleIntegerProperty counter;
    //Give up button
    Button giveUp;

    /**
     * Constructor of the GiveUpButton object
     *
     * @param toolbar The pane containing all toolbar elements
     * @param timeline Timeline object handling the timer
     * @param hintButton Hint button
     * @param undoButton Undo button
     * @param startButton Start button
     * @param point Result button
     * @param undoHandler UndoHandler object
     * @param mainStage Main stage of the program
     * @param counter counter of the timer
     * @param giveUp Give up button
     */
    public GiveUpButton(GridPane toolbar, Timeline timeline, Button hintButton, Button undoButton, Button startButton, Button point, UndoHandler undoHandler, Stage mainStage, SimpleIntegerProperty counter, Button giveUp) {
        this.toolbar = toolbar;
        this.timeline = timeline;
        this.hintButton = hintButton;
        this.undoButton = undoButton;
        this.startButton = startButton;
        this.point = point;
        this.undoHandler = undoHandler;
        this.mainStage = mainStage;
        this.counter = counter;
        this.giveUp = giveUp;
    }

    /**
     * Initializes the giveUp button
     */
    public void Initialize() {

        giveUp.setText("Give up");
        toolbar.addColumn(0, giveUp);

        //Setting up the styleClass for the CSS code
        giveUp.getStyleClass().add("giveUp");

        //What happens when we click on giveUp button
        giveUp.setOnAction(event -> {
            //We set a new stage that is going to display if the user is sure to give up in case of missclick
            Stage stage = new Stage();
            stage.setWidth(500);
            stage.setHeight(200);

            //Label displayed
            Text text = new Text();
            text.setText("Are you sure to end the current game?");
            //Setting up the style class of the text to apply CSS on it
            text.getStyleClass().add("textPixel");

            //We create the 2 buttons yes and no and we set their properties
            Button yes = new Button();
            Button no = new Button();
            yes.setText("Yes");
            yes.getStyleClass().add("giveUp");
            no.setText("No");
            no.getStyleClass().add("startButton");

            //We create it a gridPane and set its properties
            GridPane root = new GridPane();
            root.getStyleClass().add("backgroundStyle");
            root.setAlignment(Pos.CENTER);
            root.add(text, 0, 0, 2, 1);
            root.add(yes, 0, 1);
            root.add(no, 1, 1);
            root.setVgap(10);
            root.setHgap(20);

            //What happens if we click on yes
            yes.setOnAction(actionEvent -> {
                //We stop the timer
                timeline.stop();
                //We hide every buttons
                hintButton.setVisible(false);
                undoButton.setVisible(false);
                startButton.setVisible(false);
                giveUp.setVisible(false);
                point.setVisible(false);

                //We reset the undohandler
                undoHandler.Reset();

                //This is the new root where the menu will be displayed
                BorderPane newRoot = new BorderPane();
                //We initialize the new menu and close the stage we created
                MenuPanes menuPanes = new MenuPanes(mainStage, newRoot);
                menuPanes.Initialize();
                mainStage.setTitle("Graph Visualizer");
                stage.close();
            });
            no.setOnAction(actionEvent -> {
                //We close the stage we created
                stage.close();
            });

            //This scene handles the CSS background
            Scene scene = new Scene(root, 500, 200);
            scene.getStylesheets().add("Bar.css");

            stage.setTitle("Are you sure?");
            stage.setScene(scene);
            stage.show();

        });

        //We assign the result button its CSS properties
        point.getStyleClass().add("myButton");

        toolbar.addColumn(24, point);

        //What happens if we click on result button
        point.setOnAction(event ->{
            //We stop the timer
            timeline.stop();
            //We hide every buttons
            hintButton.setVisible(false);
            undoButton.setVisible(false);
            startButton.setVisible(false);
            giveUp.setVisible(false);
            point.setVisible(false);

            double points = 100;
            /*
            We calculate the points based on user's coloring

            We decrease by 8 everytime the user exceeds the chromatic number
            We decrease by 10 every n*3 seconds (n being the number of vertices)
            At some point if the user takes a really long time or exceeds too much the chromatic number its score will stay to 0
             */
            int totalColoursUsed = undoHandler.colorHistory.size();
            int ExceededNr = totalColoursUsed - Graph.chromaticNumber;
            double decrease1 = ExceededNr * 8;

            int value = counter.get();
            int decrease2 = 0;
            for(int i = 0; i < value / (Graph.vCount*3); i++){
                decrease2 += 10;
            }
            System.out.println(decrease1);
            System.out.println(decrease2);

            double totalpoints= (points - (decrease1 + decrease2));
            if(totalpoints < 0){
                totalpoints = 0;
            }




            //We create the home button and set its properties
            Button homeButton = new Button();
            homeButton.setText("Home");
            homeButton.getStyleClass().add("myButton");
            toolbar.addColumn(0, homeButton);

            //The new stage will display user's result
            Stage stage = new Stage();

            //What happens if we click on home button
            homeButton.setOnAction(event1 -> {
                //We reset the undohandler
                undoHandler.Reset();

                //This is the new root where the menu will be displayed
                BorderPane newRoot = new BorderPane();
                //We initialize the new menu and close the stage we created
                MenuPanes menuPanes = new MenuPanes(mainStage, newRoot);
                menuPanes.Initialize();
                mainStage.setTitle("Graph Visualizer");
                stage.close();
            });

            //This text displays user's results
            Text sid= new Text("it took you "+ value + " seconds\n "+
                    " The chromatic number was "+ Graph.chromaticNumber +
                    "\n You get "+ totalpoints+ " points");
            //We set the properties of the text
            sid.setX(50);
            sid.setY(50);
            sid.setFill(Color.BLACK);
            sid.getStyleClass().add("textPixel");

            //This pane will show the home button and the text
            GridPane window = new GridPane();
            window.setAlignment(Pos.CENTER);
            window.add(sid, 0, 0, 2, 1);
            window.add(homeButton, 1, 1);
            window.setVgap(10);
            window.getStyleClass().add("backgroundStyle");

            //This scene handles the CSS background
            Scene scene = new Scene(window, 300, 300);
            scene.getStylesheets().add("Bar.css");


            stage.setScene(scene);
            stage.show();


        });

        //We hide every buttons that should be in case they are displayed
        undoButton.setVisible(false);
        point.setVisible(false);
        hintButton.setVisible(false);
        giveUp.setVisible(false);
    }
}
