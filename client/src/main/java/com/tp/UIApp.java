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
    private final Client client = Client.getInstance();
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
        mainPanel.setMinWidth(600);
        mainPanel.setMinHeight(550);
        
        root.setTop(mainPanel);

        Pane bottomText = new Pane();
        Label colorLabel = new Label("Your color is: ");
        Label colorTextLabel = new Label(client.getColor().toString());
        colorTextLabel.setTextFill(client.getColor().toPaintColor());
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
        primaryStage.setTitle("Player: " + client.getColor().toString());
        primaryStage.setScene(scene);
        primaryStage.show();

        if(client.isReplayMode()){
            enterReplayMode(boardPanel);
        }
    }

    public static void main() {
        launch();
    }

    private void enterReplayMode(BoardPanel bp){
        Replay replay = client.getReplay();
        bp.playReplay(replay);
    }
    
    
}