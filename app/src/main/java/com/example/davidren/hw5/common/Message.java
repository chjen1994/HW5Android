package com.example.davidren.hw5.common;

import java.io.Serializable;

/**
 * Created by davidren on 12/16/17.
 */

public class Message implements Serializable {
    private MsgType messageType;
    private String message;


    public Message(MsgType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public MsgType getMessageType() {
        return messageType;
    }
}
