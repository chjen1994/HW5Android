package com.example.davidren.hw5.client.Start;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.davidren.hw5.R;
import com.example.davidren.hw5.client.Controller.Cli_controller;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Cli_controller controller;
    private EditText enter;
    private Button start;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textArea);
        textView.setMovementMethod(new ScrollingMovementMethod());

        start = findViewById(R.id.newGameButton);
        enter = findViewById(R.id.enterText);

        controller = new Cli_controller(this);

        start.setOnClickListener(view -> {
            controller.sendNewGameMessage();
        });

        findViewById(R.id.exitButton).setOnClickListener(view -> {
            controller.disconnect();
            finish();
        });

        findViewById(R.id.sendButton).setOnClickListener(view -> {
            controller.sendGuessMessage(enter.getText().toString());
            enter.getText().clear();
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();
        if(controller.isConnected()) {
            controller.disconnect();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        showToast("connecting to server");
        if(!controller.isConnected()) {
            controller.connect("100.124.98.212",1234);
        }
    }

    public void appendText(String text) {
        textView.append(text + "\n");
    }

    public void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
