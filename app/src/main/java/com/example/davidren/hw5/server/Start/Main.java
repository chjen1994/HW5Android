package com.example.davidren.hw5.server.Start;

import com.example.davidren.hw5.server.Net.Connection;
/**
 * Created by davidren on 12/16/17.
 */

public class Main {
    public static void main(String[] args) {
        Connection server = new Connection();
        server.start();
    }
}
