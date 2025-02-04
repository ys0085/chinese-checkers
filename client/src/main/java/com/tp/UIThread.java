package com.tp;

import java.util.concurrent.BlockingQueue;

public class UIThread implements Runnable  {

    private BlockingQueue<Move> uiActionQueue;

    UIThread(BlockingQueue<Move> uiActionQueue) {
        this.uiActionQueue = uiActionQueue;
        UIApp.setMoveCallback(this::makeMove);
    }

    @Override
    public void run() {
        UIApp.main();
    }

    
    /** 
     * @param move
     */
    public void makeMove(Move move) {
        try {
            uiActionQueue.put(move);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
}
