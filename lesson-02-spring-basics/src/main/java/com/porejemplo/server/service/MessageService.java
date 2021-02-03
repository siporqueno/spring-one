package com.porejemplo.server.service;

public interface MessageService {

    void addMessageToDB(String sender, String receiver, String text, String date);

    String getMessagesFromDBForNick(String nick);

    void disconnect();

}
