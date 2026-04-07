package Main.widgets.toolbar;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpButton {
    //The pane containing all toolbar elements
    GridPane toolbar;
    //Help button
    Button explain;

    /**
     * Constructor of HelpButton object
     *
     * @param toolbar The pane containing all toolbar elements
     * @param explain Help button
     */
    public HelpButton(GridPane toolbar, Button explain) {
        this.toolbar = toolbar;
        this.explain = explain;
    }

    /**
     * Initializes the Help button
     */
    public void Initialize() {
        //We set the properties of the button
        explain.getStyleClass().add("myButton");
        toolbar.addColumn(23, explain);

        //What happens if we click on help button
        explain.setOnAction(event -> {
            //We create a new text to explain of the game works
            Text explanation= new Text("Once you've chosen your game mode, the proper graph will appear. \n" +
                    "Higher points (the maximum achievable point is 100) will be given when you have used the least amount of colours  for the vertices in the least amount of time."+
                    " To play, start the timer by pressing the \"Star\" button and \"GiveUp\"/\"Results\" once you have finished.");

            //We set the properties of the tect
            explanation.setWrappingWidth(260);
            explanation.getStyleClass().add("textPixel");

            //This pane will contain the text element
            GridPane root = new GridPane();
            //We set its properties
            root.getStyleClass().add("backgroundStyle");
            root.setStyle("-fx-background-color: #fffcf2");
            root.addColumn(0, explanation);
            root.setAlignment(Pos.CENTER);

            //This scene handles the CSS background
            Scene scene = new Scene(root, 300, 300);
            scene.getStylesheets().add("Bar.css");

            //We create and display the new stage containing these elements
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.show();

        });
    }
}
