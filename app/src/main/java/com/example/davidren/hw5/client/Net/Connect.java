package com.example.davidren.hw5.client.Net;

import com.example.davidren.hw5.common.Message;
import com.example.davidren.hw5.common.MsgType;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Created by davidren on 12/16/17.
 */

public class Connect {
    private static Thread thrd = null;
    private static int PORT;// port number between the server and the client
    private static InetAddress host;
    private static String IP;
    private Socket socket;
    private ObjectOutputStream Client_output;
    private OutputHandler outputHandler;

    public Connect(String ip, int port, OutputHandler outputHandler) {
        this.IP = ip;
        this.PORT = port;
        this.outputHandler = outputHandler;
    }


    public void send(MsgType msgType, String msg){
        Message sendmsg = new Message(msgType, msg);
        try{
            Client_output.writeObject(sendmsg);
            Client_output.flush();
            Client_output.reset();
        }catch(IOException ioEx){
            ioEx.printStackTrace();
            System.out.println("Unable to send msg!");
            System.exit(1);
        }

    }

    public void sendGuessMessage(String guess) throws IOException {
        send(MsgType.GUESS, guess);
    }

    public void sendStartMessage() throws IOException {
        send(MsgType.START, "");
    }

    public void sendQuitMessage() throws IOException {
        send(MsgType.QUIT, "");
    }


    public void connect() throws IOException {
        host = InetAddress.getByName(IP);
        //connect to the server
        socket = new Socket(host, PORT);
        //set the client input and output stream
        Client_output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream Client_input = new ObjectInputStream(socket.getInputStream());

        //start the thread
        thrd = new Thread(new Client_thread(Client_input, outputHandler));
        thrd.start();
    }

    public void quit() throws IOException {
        sendQuitMessage();
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }





    private class Client_thread implements Runnable{
        private ObjectInputStream Client_input;
        private OutputHandler outputHandler;

        public Client_thread(ObjectInputStream Client_input, OutputHandler outputHandler) {
            this.Client_input = Client_input;
            this.outputHandler = outputHandler;
        }

        @Override
        public void run() {
            try {


                while (true) {

                    //get server's message
                    Message msg = (Message)Client_input.readObject();

                    outputHandler.handleMessage(msg.getMessage());

                    //if get game over message from the server
                    if(msg.getMessageType() == MsgType.GAME_OVER) {
                        outputHandler.handleGameOver();
                    }
                }


            } catch (IOException | ClassNotFoundException e2) {

            }
        }
    }


}

