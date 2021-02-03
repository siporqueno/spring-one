package com.porejemplo.server.entity;

public class Message {
    private final String sender;
    private final String receiver;
    private final String text;
    private final String date;

    public Message(String sender, String receiver, String text, String date) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
