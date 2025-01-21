package com.tp;

public class UIThread implements Runnable {

    private String[] args;

    UIThread(String[] args) {
        this.args = args;
    }
    @Override
    public void run() {
        App.main(args);
    }
    
}
