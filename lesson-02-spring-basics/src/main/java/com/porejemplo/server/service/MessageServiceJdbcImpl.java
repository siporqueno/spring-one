package com.porejemplo.server.service;

import com.porejemplo.server.entity.Message;
import com.porejemplo.server.persistence.MessageRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("msgService")
public class MessageServiceJdbcImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceJdbcImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void addMessageToDB(String sender, String receiver, String text, String date) {
        try {
            messageRepository.save(new Message(sender, receiver, text, date));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getMessagesFromDBForNick(String nick) {
        try {
            return messageRepository.findMessagesByNick(nick);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            messageRepository.disconnect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
