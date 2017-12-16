package com.example.davidren.hw5.server.Net;


import java.io.*;

import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

import com.example.davidren.hw5.server.Model.Hangman;
import com.example.davidren.hw5.common.Message;
import com.example.davidren.hw5.common.MsgType;
/**
 * Created by davidren on 12/16/17.
 */

public class ClientHandler extends Thread  {
    int life = 0;
    int score;
    private Socket client;
    private ObjectInputStream Server_input;
    private ObjectOutputStream Server_output;
    private boolean connection = true;
    private Hangman hangman;
    private Connection server;
    public ClientHandler(Socket socket, Connection server){
        client = socket;
        this.server = server;

        score = 0;
        hangman = null;
    }

    @Override
    public void run(){
        try {
            Server_input = new ObjectInputStream(client.getInputStream());
            Server_output = new ObjectOutputStream(client.getOutputStream());
            send(MsgType.NONE, "Hangman Game started Score: " + score);
            while(connection){

                Message msg = (Message) Server_input.readObject();
                switch (msg.getMessageType()){
                    case START:
                        startHangman();
                        break;
                    case GUESS:
                        guess(msg);
                        break;
                    case QUIT:
                        connection = false;
                        break;
                    default:
                        break;
                }
            }
        } catch(IOException ioEx){
            ioEx.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (client != null) {
                    send(MsgType.NONE, "QUITING");
                    quit();
                }
            }catch(IOException ioEx){
                ioEx.printStackTrace();
                System.out.println("Unable to quiting the game!!");
                System.exit(1);
            }

        }


    }
    public void send(MsgType msgType, String msg){
        Message sendmsg = new Message(msgType, msg);
        try{
            Server_output.writeObject(sendmsg);
            Server_output.flush();
            Server_output.reset();
        }catch(IOException ioEx){
            ioEx.printStackTrace();
            System.out.println("Unable to send msg!");
            System.exit(1);
        }

    }



    public void startHangman(){
        hangman = new Hangman(server.getRandomWord());

        send(MsgType.NONE, hangman.toString()+ ", Score: " + score);
    }
    public void guess(Message m){
        //check if the word is 1 or the whole word
        if(hangman.validInput(m.getMessage())) {


            //guess the word
            hangman.guess(m.getMessage().toLowerCase());

            if(hangman.win()) {
                score++;
                send(MsgType.GAME_OVER, hangman.toString()+ ", Score: " + score);
                hangman = null;
            }

            else if (hangman.getLife() == 0) {
                score--;
                send(MsgType.GAME_OVER, hangman.toString()+ ", Score: " + score);
                hangman = null;
            }
            else {
                send(MsgType.NONE, hangman.toString()+ ", Score: " + score);
            }
        }else {
            send(MsgType.NONE, "Guess the word");
        }
    }

    public void quit() throws IOException{
        client.close();
        Server_output.close();
        Server_input.close();
    }


}
