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
}
