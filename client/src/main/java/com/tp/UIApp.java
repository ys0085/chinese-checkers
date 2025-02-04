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

/**
 * The {@code UIApp} class represents the graphical user interface for the game.
 * It initializes and manages the JavaFX application, handling UI components
 * such as the board panel, turn display, and color indicators.
 */
public class UIApp extends Application {

    /**
     * Callback for handling moves.
     */
    private static Consumer<Move> moveCallback;

    /**
     * Reference to the {@code BoardPanel} component.
     */
    public static BoardPanel boardPanel;

    /**
     * Label to display whose turn it is.
     */
    public static Label turnLabel;

    /**
     * Variant of the game, though not utilized in this snippet.
     */
    public static Variant variant;

    /**
     * Label to display the player's color.
     */
    public static Label colorTextLabel;

    /**
     * Static text label for player color indication.
     */
    public static Label colorLabel;

    /**
     * Sets the move callback for handling player moves.
     *
     * @param moveCallback A {@code Consumer} that takes a {@code Move} object.
     */
    public static void setMoveCallback(Consumer<Move> moveCallback) {
        UIApp.moveCallback = moveCallback;
    }

    /**
     * The start method initializes the JavaFX application.
     *
     * @param _primaryStage_ The primary stage for this application.
     */
    @Override
    public void start(Stage _primaryStage_) {
        BorderPane root = new BorderPane();
        BoardPanel mainPanel = new BoardPanel(UIApp.moveCallback);
        UIApp.boardPanel = mainPanel;
        mainPanel.setStyle("-fx-background-color: white;");
        mainPanel.setMinWidth(600);
        mainPanel.setMinHeight(550);
        root.setTop(mainPanel);

        // Create a horizontal box for displaying bottom text
        HBox bottomText = new HBox();
        bottomText.setAlignment(Pos.CENTER);
        bottomText.setSpacing(20);
        HBox.setHgrow(bottomText, Priority.ALWAYS);

        // Initialize labels to display color information
        colorLabel = new Label("Your color is: ");
        colorTextLabel = new Label();

        // Set font size for improved visibility
        colorLabel.setFont(new Font(20));
        colorTextLabel.setFont(new Font(20));

        turnLabel = new Label();
        turnLabel.setAlignment(Pos.CENTER);
        turnLabel.setFont(new Font(20));

        // Add color and turn labels to the bottom text container
        bottomText.getChildren().addAll(colorLabel, colorTextLabel, turnLabel);

        // Set the bottom text container as the bottom section of the root
        root.setBottom(bottomText);

        boardPanel.update();

        // Create a new scene with the root layout and set its dimensions
        Scene scene = new Scene(root, 750, 700);
        _primaryStage_.setTitle("Player: " + Client.getInstance().getColor().toString());
        _primaryStage_.setScene(scene);
        _primaryStage_.show();
    }

    /**
     * Updates the color and turn labels based on the current game state.
     */
    public static void updateLabel() {
        if (colorTextLabel != null) {
            colorTextLabel.setTextFill(Client.getInstance().getColor().toPaintColor());
            colorTextLabel.setText(Client.getInstance().getColor().toString());
        }

        if (turnLabel != null) {
            turnLabel.setText(Client.getInstance().isYourTurn() ? "Your turn" : "Opponent's turn");
        }
    }

    /**
     * Main method to launch the JavaFX application.
     *
     *  Command line arguments, though unused in this implementation.
     */
    public static void main() {
        launch();
    }
}
