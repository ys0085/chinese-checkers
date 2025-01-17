package com.tp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        BorderPane root = new BorderPane();

        
        BoardPanel leftPanel = new BoardPanel();
        leftPanel.setStyle("-fx-background-color: lightblue;");
        leftPanel.setMinWidth(900);

        
        VBox rightPanel = new VBox();
        rightPanel.setMinWidth(300);

        
        Region topRightPanel = new Region();
        topRightPanel.setStyle("-fx-background-color: lightcoral;");
        topRightPanel.setPrefHeight(400);

        Region bottomRightPanel = new Region();
        bottomRightPanel.setStyle("-fx-background-color: lightgreen;");
        bottomRightPanel.setPrefHeight(400);

        
        rightPanel.getChildren().addAll(topRightPanel, bottomRightPanel);

        
        root.setLeft(leftPanel);
        root.setRight(rightPanel);

        
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Split Panel Layout Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
