package com.example.davidren.hw5.server.Controller;

import java.util.Random;
/**
 * Created by davidren on 12/16/17.
 */

public class Srv_controller {

    private static readFile wordHandler;
    private static Random random;

    public Srv_controller() {
        wordHandler = readFile.getInstance();
        random = new Random();
    }
    public String getRandomWord(){
        return wordHandler.getWords().get(random.nextInt(wordHandler.getWords().size()));
    }
}
