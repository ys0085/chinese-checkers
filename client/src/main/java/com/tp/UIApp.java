package com.tp; // Package declaration

import java.util.function.Consumer; // Import Consumer functional interface for callbacks

import javafx.application.Application; // Import JavaFX Application class
import javafx.geometry.Pos; // Import Pos for positioning elements
import javafx.scene.Scene; // Import Scene class for creating a scene
import javafx.scene.control.Label; // Import Label class for displaying text
import javafx.scene.layout.BorderPane; // Import BorderPane layout
import javafx.scene.layout.HBox; // Import HBox layout for horizontal alignment
import javafx.scene.layout.Priority; // Import Priority for layout management
import javafx.scene.text.Font; // Import Font class for text styling
import javafx.stage.Stage; // Import Stage class for the main application window

public class UIApp extends Application { // Main application class extending Application

    private static Consumer<Move> moveCallback; // Callback for handling moves
    public static BoardPanel boardPanel; // Reference to the BoardPanel component
    public static Label turnLabel; // Label to display whose turn it is
    public static Variant variant; // Variant of the game, though not utilized in this snippet
    public static Label colorTextLabel; // Label to display the player's color
    public static Label colorLabel; // Static text label for player color indication

    /**
     * Generic setter method to set the move callback.
     * 
     * @param moveCallback A Consumer that takes a Move object.
     */
    public static void setMoveCallback(Consumer<Move> moveCallback) {
        UIApp.moveCallback = moveCallback; // Assign the provided callback to the static variable
    }

    /**
     * The start method is the entry point for all JavaFX applications.
     * 
     * @param _primaryStage_ The primary stage for this application.
     */
    @Override
<<<<<<< Updated upstream
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane(); 
        
        BoardPanel mainPanel = new BoardPanel(UIApp.moveCallback);
        UIApp.boardPanel = mainPanel;
        mainPanel.setStyle("-fx-background-color: white;");
        mainPanel.setMinWidth(600);
        mainPanel.setMinHeight(550);
        
        root.setTop(mainPanel);

        Pane bottomText = new Pane();
        Label colorLabel = new Label("Your color is: ");
        Label colorTextLabel = new Label(Client.getInstance().getColor().toString());
        colorTextLabel.setTextFill(Client.getInstance().getColor().toPaintColor());
        colorTextLabel.setLayoutX(200);
        colorLabel.setAlignment(Pos.CENTER);
        colorTextLabel.setAlignment(Pos.CENTER);

        turnLabel = new TurnLabel();
        turnLabel.setLayoutX(450);
        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.start();
        

        bottomText.setMinWidth(600);
        bottomText.setMinHeight(50);
        bottomText.getChildren().addAll(colorLabel, colorTextLabel, turnLabel);

        root.setBottom(bottomText);

        boardPanel.update();

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Player: " + Client.getInstance().getColor().toString());
        primaryStage.setScene(scene);
        primaryStage.show();
=======
    public void start(Stage _primaryStage_) {
        BorderPane root = new BorderPane(); // Create the root layout as a BorderPane
        BoardPanel mainPanel = new BoardPanel(UIApp.moveCallback); // Instantiate BoardPanel with move callback
        UIApp.boardPanel = mainPanel; // Assign the main panel to the static variable
        mainPanel.setStyle("-fx-background-color: white;"); // Set background color of the board panel
        mainPanel.setMinWidth(600); // Set minimum width for the board panel
        mainPanel.setMinHeight(550); // Set minimum height for the board panel
        root.setTop(mainPanel); // Add the board panel to the top of root layout

        // Create a horizontal box for displaying bottom text
        HBox bottomText = new HBox();
        bottomText.setAlignment(Pos.CENTER); // Center align the contents of the HBox
        bottomText.setSpacing(20); // Set spacing between elements
        HBox.setHgrow(bottomText, Priority.ALWAYS); // Allow HBox to grow

        // Initialize labels to display color information
        colorLabel = new Label("Your color is: "); // Label for text
        colorTextLabel = new Label(); // Label to display player's color

        // Set font size for improved visibility
        colorLabel.setFont(new Font(20)); // Set font size for color label
        colorTextLabel.setFont(new Font(20)); // Set font size for color text label

        turnLabel = new Label(); // Initialize the turn display label
        turnLabel.setAlignment(Pos.CENTER); // Center align this label
        turnLabel.setFont(new Font(20)); // Set font size for turn label

        // Add color and turn labels to the bottom text container
        bottomText.getChildren().addAll(colorLabel, colorTextLabel, turnLabel);

        // Set the bottom text container as the bottom section of the root
        root.setBottom(bottomText);

        boardPanel.update(); // Update the board panel display

        // Create a new scene with the root layout and set its dimensions
        Scene scene = new Scene(root, 750, 700);
        _primaryStage_.setTitle("Player: " + Client.getInstance().getColor().toString()); // Set window title
        _primaryStage_.setScene(scene); // Set the scene to the primary stage
        _primaryStage_.show(); // Display the primary stage
>>>>>>> Stashed changes
    }

    /**
     * Updates the color and turn labels based on the current game state.
     */
    public static void updateLabel() {
        // Update color text label if not null
        if (colorTextLabel != null) {
            colorTextLabel.setTextFill(Client.getInstance().getColor().toPaintColor()); // Set the text color
            colorTextLabel.setText(Client.getInstance().getColor().toString()); // Set the text to display color
        }

        // Update turn label if not null
        if (turnLabel != null) {
            // Change the text based on whose turn it is
            turnLabel.setText(Client.getInstance().isYourTurn() ? "Your turn" : "Opponent's turn");
        }
    }

    /**
     * Main method to launch the JavaFX application.
     * 
     * @param args Command line arguments, though unused in this implementation.
     */
    public static void main() {
        launch(); // Launch the JavaFX application
    }
}