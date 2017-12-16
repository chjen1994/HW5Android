package com.example.davidren.hw5.client.Net;

/**
 * Created by davidren on 12/16/17.
 */

public interface OutputHandler {


    public void handleMessage(String message);

    public void handleGameOver();

    public void handleNewConnection(String message);
}
