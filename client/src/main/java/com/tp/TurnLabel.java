package com.tp;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class TurnLabel extends Label {
    TurnLabel(){
        super("");
        this.textProperty().bind(task.messageProperty());
        
    }

    private final Task<Void> task = new Task<Void>() {
        {
            updateMessage("It's not your turn.");
        }

        @Override
        public Void call() throws Exception {
            while (true) {
                if (Client.getInstance().isYourTurn())
                    updateMessage("It's your turn.");

                else
                    updateMessage("It's not your turn.");
            }
        }
    };

    public void start(){
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

        
}
