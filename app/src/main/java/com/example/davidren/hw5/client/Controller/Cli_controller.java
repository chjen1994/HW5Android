package com.example.davidren.hw5.client.Controller;


import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import com.example.davidren.hw5.client.Net.OutputHandler;
import com.example.davidren.hw5.client.Net.Connect;
import com.example.davidren.hw5.client.Start.MainActivity;


import android.os.Build;
import android.support.annotation.RequiresApi;
/**
 * Created by davidren on 12/16/17.
 */

public class Cli_controller {

    private Connect client;
    private boolean connection = false;

    private boolean isGameOngoing = false;

    private MainActivity view;

    public Cli_controller(MainActivity view) {
        this.view = view;
    }

    public boolean isConnected() {
        return connection;
    }

    public synchronized void appendText(String text) {
        if(isGameOngoing) {
            view.runOnUiThread(() -> {
                view.appendText(text);
            });
        }
    }

    public synchronized void appendNewConnection(String text) {
        view.runOnUiThread(() -> {
            view.appendText(text);
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void connect(String ip, int port, OutputHandler outputHandler) {
        CompletableFuture.runAsync(() -> {
            disconnect();
            client = new Connect(ip, port, outputHandler);
            try {
                client.connect();
                connection = true;
            } catch (IOException ie) {
                appendText("Failed to establish connection.");
                try {
                    client.quit();
                    connection = false;
                } catch (Exception e) {
                    System.out.println("Cleanup failed.");
                }
            }
        }).thenRun(() -> outputHandler.handleNewConnection("Connected to " + ip + ":" + port));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void disconnect() {
        CompletableFuture.runAsync(() -> {
            if(connection) {
                try {
                    client.quit();
                    connection = false;
                    appendText("Disconnected.");
                } catch (IOException e) {
                    view.runOnUiThread(() -> {
                        view.showToast("Failed to disconnect.");
                    });
                }
            }
        });
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendGuessMessage(String guess) {
        CompletableFuture.runAsync(() -> {
            try {
                if(isGameOngoing) {
                    appendText("Guess: " + guess);
                    client.sendGuessMessage(guess);
                } else {
                    view.runOnUiThread(() -> {
                        view.showToast("Start a new game.");
                    });
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendNewGameMessage() {
        CompletableFuture.runAsync(() -> {
            try {
                if(!isGameOngoing) {
                    client.sendStartMessage();
                    isGameOngoing = true;
                } else {
                    view.runOnUiThread(() -> {
                        view.showToast("Game already ongoing.");
                    });
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void connect(String ip, int port) {
        connect(ip,port,new ViewOutput());
    }

    private class ViewOutput implements OutputHandler {

        @Override
        public void handleNewConnection(String message) {
            appendNewConnection(message);
        }

        @Override
        public void handleMessage(String message) {
            appendText(message);
        }

        @Override
        public void handleGameOver() {
            appendText("Game over, start a new game to play again.");
            isGameOngoing = false;
        }
    }
}
