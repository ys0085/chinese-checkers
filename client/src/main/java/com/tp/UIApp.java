package com.tp;

import java.util.function.Consumer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UIApp extends Application {

    private static Consumer<Move> moveCallback;
    public static BoardPanel boardPanel;
    public static TurnLabel turnLabel;
    public static Variant variant;
    
    /** Generic setter
     * @param moveCallback
     */
    public static void setMoveCallback(Consumer<Move> moveCallback) {
        UIApp.moveCallback = moveCallback;
    }

    
    /** JavaFX Application start method
     * @param primaryStage
     */
    @Override
public void start(Stage primaryStage) {
    BorderPane root = new BorderPane(); 
    
    BoardPanel mainPanel = new BoardPanel(UIApp.moveCallback);
    UIApp.boardPanel = mainPanel;
    mainPanel.setStyle("-fx-background-color: white;");
    
    // Let the BoardPanel determine its own size
    mainPanel.setPrefSize(600, 550); // Set preferred size, but it can be overridden by layout
    
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

    // Create the scene with the root pane
    Scene scene = new Scene(root);
    
    // Bind the scene size to the preferred size of the BoardPanel
    primaryStage.setScene(scene);
    primaryStage.sizeToScene(); // Adjust the stage size to fit the scene

    primaryStage.setTitle("Player: " + Client.getInstance().getColor().toString());
    primaryStage.show();
    }

    public static void main() {
        launch();
    }
    
    
}