package com.example.davidren.hw5.server.Model;

/**
 * Created by davidren on 12/16/17.
 */

public class Hangman {
    private String word;
    private int life;
    private boolean win;
    private boolean check = false;
    private char[] dash;


    public Hangman(String word){
        this.word = word;

        life = word.length();

        dash = new char[life];

        for(int i = 0; i < dash.length;i++) {
            dash[i] = '_';
        }
        win = false;
    }
    public boolean win() {
        return win;
    }

    public int getLife() {
        return life;
    }

    public boolean validInput(String guess) {
        return guess.length() == word.length() || guess.length() == 1;
    }

    public void guess(String guess){
        if(guess.length() == word.length()) {
            if(guess.equalsIgnoreCase(word)) {
                for(int i = 0; i < dash.length;i++) {
                    dash[i] = word.charAt(i);
                }
                win = true;
                check = true;
            }
        } else {
            for (int i = 0; i < dash.length; i++) {
                if (word.charAt(i) == guess.charAt(0)) {
                    dash[i] = guess.charAt(0);
                    check = true;
                }
            }
        }
        if (check != true){
            life--;
        }
        if (isWordSolved()) {
            win = true;
        }
        //if guess not right
        check = false;
    }

    @Override
    public String toString() {
        return "Word: " + String.valueOf(dash) + ", Remaining attempt: " + life;
    }

    private boolean isWordSolved() {
        return String.valueOf(dash).equalsIgnoreCase(word);
    }
}
