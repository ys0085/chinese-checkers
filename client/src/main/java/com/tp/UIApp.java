package com.tp;

import java.util.function.Consumer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UIApp extends Application {

    private static Consumer<Move> moveCallback;
    public static BoardPanel boardPanel;

    
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
        mainPanel.setMinWidth(700);
        
        root.setLeft(mainPanel);

        mainPanel.update();

        
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main() {
        launch();
    }
}