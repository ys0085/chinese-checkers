package com.tp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane(); 
        
        BoardPanel mainPanel = new BoardPanel();
        mainPanel.setStyle("-fx-background-color: white;");
        mainPanel.setMinWidth(700);
        
        root.setLeft(mainPanel);

        mainPanel.update();

        
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
