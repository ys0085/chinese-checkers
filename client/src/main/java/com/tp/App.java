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

        
        Region leftPanel = new Region();
        leftPanel.setStyle("-fx-background-color: lightblue;");
        leftPanel.setMinWidth(450);

        
        VBox rightPanel = new VBox();
        rightPanel.setMinWidth(150);

        
        Region topRightPanel = new Region();
        topRightPanel.setStyle("-fx-background-color: lightcoral;");
        topRightPanel.setPrefHeight(200);

        Region bottomRightPanel = new Region();
        bottomRightPanel.setStyle("-fx-background-color: lightgreen;");
        bottomRightPanel.setPrefHeight(200);

        
        rightPanel.getChildren().addAll(topRightPanel, bottomRightPanel);

        
        root.setLeft(leftPanel);
        root.setRight(rightPanel);

        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Split Panel Layout Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
