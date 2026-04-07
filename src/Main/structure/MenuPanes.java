package Main.structure;


import Main.Utils.ReadGraph;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;

public class MenuPanes {
    //This class contains 2 starting variables:
    //The main root created in the Main file
    //The main stage created in the Main file
    Stage mainStage;
    BorderPane root;

    public MenuPanes(Stage mainStage, BorderPane root) {
        this.mainStage = mainStage;
        this.root = root;
    }

    //This method initializes the structure of the main menu of the game
    public void Initialize() {
        //We create first the main pane
        BorderPane graphPane = new BorderPane();
        //We add the 4 buttons of the menu
        Button btnGenerateGraph = new javafx.scene.control.Button("GENERATE GRAPH");
        Button btnInputGraph = new Button("INPUT GRAPH");
        Button btnUseFile = new Button("EXTERNAL FILE");
        Button btnLeave = new Button("LEAVE");


        //This following piece of code sets the sizes, location and the Wraptext of the buttons we just created

        btnGenerateGraph.setPrefSize(300,143);
        btnGenerateGraph.setWrapText(true);

        btnUseFile.setPrefSize(300,145);
        btnUseFile.setWrapText(true);

        btnInputGraph.setPrefSize(300,143);
        btnInputGraph.setWrapText(true);

        btnLeave.setPrefSize(300,145);
        btnLeave.setWrapText(true);
        root.setTop(btnLeave); //we left it in the borderPane root so it can appear on the top left side

        //we added the buttons to a horizontal Box root so they are all next to each other within the borderPane
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(btnGenerateGraph, btnUseFile, btnInputGraph);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        root.setCenter(buttons);


        //What happens when we click on Generate graph button
        btnGenerateGraph.setOnAction((event) -> {
            //We generate the random graph and leads to the difficulty page
            Random random = new Random();
            int v = 4 + random.nextInt(25);
            int e = setUpEdges(v);
            chooseDifficulty(this.mainStage, v, e);
        });

        //What happens when we click on Input Graph button
        btnInputGraph.setOnAction(actionEvent -> {
            inputGraph(mainStage);
        });

        //What happens when we click on leave button
        btnLeave.setOnAction(actionEvent -> {
            //We initialize a new Window that will be displayed
            Stage stage = new Stage();
            stage.setWidth(300);
            stage.setHeight(400);
            GridPane root = new GridPane();
            //Main label of the screen
            Text text = new Text();
            text.setText("ARE YOU SURE YOU WANT TO LEAVE?");

            //We create 2 buttons yes and no
            Button yes = new Button();
            Button no = new Button();
            yes.setText("YES :(");
            no.setText("NO :)");

            //We set up the CSS properties of the root, the 2 buttons and the text
            text.getStyleClass().add("textPixel");
            root.getStyleClass().add("backgroundStyle");
            yes.getStyleClass().add("giveUp");
            no.getStyleClass().add("startButton");

            //We ask for the user if he's sure to leave the game
            yes.setOnAction(event -> {
                mainStage.close();
                stage.close();
            });
            no.setOnAction(event -> {
                stage.close();
            });
            //We set the elements in the root
            root.setAlignment(Pos.CENTER);
            root.add(text, 0, 0, 2, 1);
            root.add(yes, 0, 1);
            root.add(no, 1, 1);
            root.setVgap(10);
            root.setHgap(20);

            //We create the scene to apply the css code
            Scene scene = new Scene(root, 300, 200);
            scene.getStylesheets().add("Bar.css");
            stage.setTitle("Are you sure?");
            stage.setScene(scene);
            stage.show();
        });

        //What happens when we click on Generate select file button
        btnUseFile.setOnAction(event -> {
            try{
                //We open the file picker to choose which file we want to use
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt") );
                File selectedFile = fileChooser.showOpenDialog(mainStage);
                //This variable stores the path of the file to use it afterward
                String path = selectedFile.getAbsolutePath();

                if (selectedFile != null) {
                    //We call this method to get the values from the file the user just picked
                    ReadGraph.SetUp(path);

                    //Getting back the values in these 3 variables
                    int verticesCount = ReadGraph.n;
                    int edgesCount = ReadGraph.e.length;
                    int[][] edges = new int[ReadGraph.e.length][2];
                    for (int i = 0; i < ReadGraph.e.length; i++) {
                        edges[i][0] = ReadGraph.e[i].u;
                        edges[i][1] = ReadGraph.e[i].v;
                    }

                    //We lead to the difficulty screen
                    chooseDifficulty(mainStage, verticesCount, edgesCount);
                } else {
                    System.out.println("Failed to read the graph file.");
                }
            } catch (Exception e) {
                System.out.println("Couldn't open file");
            }
        });

        //This new scene is used to display on it the different animations/backgrounds that you can see on the menu
        Scene scene = new Scene(root);
        mainStage.setScene(scene);

        //We set the ID's of the different elements we want to customize in CSS
        root.setId("Boh");
        btnGenerateGraph.setId("button0");
        btnUseFile.setId("button1");
        btnLeave.setId("button2");
        btnInputGraph.setId("button0");
        scene.getStylesheets().add("Game.css");

        //We get the Image of the character and set its properties
        // Image person = new Image("images/character1.png");
        Image person = new Image("images/character1.png");
        ImageView personView = new ImageView(person);
        personView.setFitWidth(225);
        personView.setFitHeight(294);

        //We animate this character going from the right to the left and from the left to the right
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(personView);
        transition.setDuration(Duration.seconds(3));
        transition.setByX(700); // Move horizontally from the first button till the cat
        transition.setAutoReverse(true); // after it reaches the cat, it goes back
        transition.setCycleCount(TranslateTransition.INDEFINITE); // indefinitely
        transition.play();

        root.setBottom(personView);

        //We get the Image of the arrow and set its properties
        //Image arrow= new Image("images/arrow.png");
        Image arrow= new Image("images/arrow.png");
        ImageView arrowView = new ImageView(arrow);
        arrowView.setFitWidth(40);
        arrowView.setFitHeight(40);


        //We animate this arrow going
        //There is a specific transition for the arrow, this time it just gets bigger and smaller
        ScaleTransition transition1 = new ScaleTransition();
        transition1.setNode(arrowView);
        transition1.setByX(1.5); // Increase width by 150%
        transition1.setByY(1.5);
        transition1.setDuration(Duration.seconds(3));
        transition1.setAutoReverse(true);
        transition1.setCycleCount(TranslateTransition.INDEFINITE);
        transition1.play();

        root.setLeft(arrowView);
    }

    /**
    * Switches the game to the difficulty choosing window
    *
    * @param mainStage The main stage of the program
    * @param v The number of vertices of the graph we want to generate
    * @param e The number of vertices of the graph we want to generate
    */
    public void chooseDifficulty(Stage mainStage, int v, int e) {
        //We create a GamePanes object
        GamePanes gamePanes = new GamePanes();
        //We initialize the main pane of the window which is a GridPane this time
        GridPane gamemodePane = new GridPane();
        gamemodePane.setAlignment(Pos.CENTER);
        gamemodePane.setStyle("-fx-background-color: #fffcf2");
        gamemodePane.setHgap(20);
        gamemodePane.setVgap(20);
        gamemodePane.setId("Window");

        //We change the mainStage name
        mainStage.setTitle("Choose Difficulty");

        //We create a label displaying the text to choose the difficulty
        Text subTitle = new Text();
        subTitle.setText("Select a difficulty level:");
        gamemodePane.add(subTitle, 1, 0);
        subTitle.setId("TextA");

        //We set the 3 buttons representing the 3 difficulties possible
        Button easy = new Button("To the bitter end");
        Button medium = new Button("Random order");
        Button hard = new Button("I changed my mind");
        gamemodePane.add(easy, 20, 2);
        gamemodePane.add(medium, 20, 3);
        gamemodePane.add(hard, 20, 4);
        easy.setPrefSize(300,145);
        easy.setWrapText(true);
        medium.setPrefSize(300,145);
        medium.setWrapText(true);
        hard.setPrefSize(300,145);
        hard.setWrapText(true);


        //We set what happens when we click on the buttons
        //We call each time the same method but with different parameters (see the 'InitializeStructure' method documentation)
        easy.setOnAction(actionEvent -> {
            gamePanes.InitializeStructure(false, false, mainStage, v, e);
        });
        medium.setOnAction(actionEvent -> {
            gamePanes.InitializeStructure(false, true, mainStage, v, e);
        });
        hard.setOnAction(actionEvent -> {
            gamePanes.InitializeStructure(true, true, mainStage, v, e);
        });

        //We create a new scene to add the CSS code to our buttons/pane
        Scene scene = new Scene(gamemodePane, mainStage.getWidth(), mainStage.getHeight());
        mainStage.setScene(scene);
        scene.getStylesheets().add("Difficulty.css");

        mainStage.show();
    }

    /**
     * Inputing a graph manually by the user
     *
     * @param mainStage The main stage of the program
     */
    public void inputGraph(Stage mainStage){
        //We first the main pane of the window which is a GridPane
        GridPane gamemodePane = new GridPane();
        gamemodePane.setAlignment(Pos.CENTER);
        gamemodePane.setStyle("-fx-background-color: #fffcf2");
        gamemodePane.setHgap(10);
        gamemodePane.setVgap(10);

        //We set a new Title to the main stage
        mainStage.setTitle("Input values");

        //We create a label which will display (if there are) the errors of the user when inputting values
        Text text = new Text();
        text.setFill(Color.RED);
        text.setStyle("-fx-font-family: 'Gameplay'; -fx-font-size: 17px;");

        //We create a label explaining the user what to do
        Text subTitle = new Text();
        subTitle.setFill(Color.RED);

        subTitle.setText("INPUT THE NUMBER OF VERTICES AND EDGES:");
        subTitle.getStyleClass().add("textTitle");
        subTitle.setStyle("-fx-font-family: 'Gameplay'; -fx-font-size: 17px;");
        gamemodePane.add(subTitle, 1, 0);

        //We create two textfield to let the user inputting values
        TextField vertices = new TextField();
        vertices.setPromptText("NUMBER OF VERTICES");
        vertices.getStyleClass().add("textInput");
        TextField edges = new TextField();
        edges.setPromptText("NUMBER OF EDGES");
        edges.getStyleClass().add("textInput");
        gamemodePane.add(vertices, 1, 1);
        gamemodePane.add(edges, 1, 2);

        gamemodePane.add(text, 1, 4);

        //We create the button to generate the graph and set its action
        Button generateGraph = new Button("GENERATE");
        generateGraph.getStyleClass().add("genButton");
        gamemodePane.add(generateGraph, 1, 3);
        gamemodePane.setId("Backgr");
        generateGraph.setOnAction(actionEvent -> {
            try{
                //We try to parse the values inputted by the user into 2 integer variables (vertices and edges)
                int e = Integer.parseInt(edges.getText());
                int v = Integer.parseInt(vertices.getText());
                //We check if the graph can be drawn with these 2 values
                if(e <= (v * (v - 1)) / 2 && e >= v - 1) {
                    chooseDifficulty(mainStage, v, e);
                } else {
                    //if it isn't we set the text we created before to an error message
                    text.setText("The number of edges should be equal or higher\n than the number of vertices and should\n not bypass (n*(n-1))/2 vertices");
                }
            } catch (NumberFormatException err) {
                //if we catch an error while parsing values to integer we display an error message to the user with the text we created before
                text.setText("You should enter a valid number of edges and vertices");
            }
        });


        Scene scene = new Scene(gamemodePane, mainStage.getWidth(), mainStage.getHeight());
        scene.getStylesheets().add("Bar.css");
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * This method creates a random possible number of vertices for a graph
     *
     * @param v the number of vertices
     * @return the random number of edges based on the number of vertices
     */
    public int setUpEdges(int v) {
        Random random = new Random();
        int e = random.nextInt(((v * (v - 1)) / 2) - 1);
        while (e < v){
            e = random.nextInt(((v * (v - 1)) / 2) - 1);;
        }
        return e;
    }
}
