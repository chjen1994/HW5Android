package com.example.davidren.hw5.server.Net;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.davidren.hw5.server.Controller.Srv_controller;
/**
 * Created by davidren on 12/16/17.
 */

public class Connection {
    private static ServerSocket server_socket;
    private static final int PORT = 1234;
    private Srv_controller controller;

    public Connection(){
        this.controller = new Srv_controller();
    }
    public void start(){
        try{
            server_socket = new ServerSocket(PORT);
            System.out.println("Opening port");
            do {
                //Wait for client...
                //limit the number of server_thread
                Socket linkA = server_socket.accept();
                System.out.println("\nNew client accepted.\n");
                ClientHandler handler = new ClientHandler(linkA, this);
                handler.start();

            }while(true);
        }catch(IOException e){
            System.out.println("Unable to open port or accept client!!");
            System.exit(1);
        }finally{
            try{
                if (server_socket != null){
                    System.out.println("Quiting the game...");
                    server_socket.close();
                }
            }catch(IOException ioEx){
                System.out.println("Unable to quiting the game!!");
                System.exit(1);
            }

        }

    }

    public synchronized String getRandomWord() {
        return controller.getRandomWord();
    }
}
