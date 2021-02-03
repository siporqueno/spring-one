package com.porejemplo.server;

import com.porejemplo.server.service.AuthService;
import com.porejemplo.server.service.MessageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

@Service("chatServer")
public class Server {
    private final AuthService authService;
    private final MessageService msgService;
    Vector<ClientHandler> clients;

    public Server(AuthService authService, MessageService msgService) {
        this.authService = authService;
        this.msgService = msgService;
    }

    public void start(int port) {

        ServerSocket server = null;
        Socket socket = null;

        try {
            clients = new Vector<>();
            server = new ServerSocket(port);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket, authService);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.disconnect();
            msgService.disconnect();
        }
    }

    public void broadcastMsg(String str, String sender) {
        msgService.addMessageToDB(sender, null, str, "00-00-00");
        for (ClientHandler o : clients) {
            o.sendMsg(sender + ": " + str);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
        clientHandler.sendMsg(
                msgService.getMessagesFromDBForNick(clientHandler.getNick())
        );
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    //    Start of my code, home work of Lesson 7, task 1
    public void sendMsgFromNickToNick(String str, String sender, String receiver) {
        msgService.addMessageToDB(sender, receiver, str, "00-00-00");
        for (ClientHandler o : clients) {
            if (o.getNick().equals(sender) || o.getNick().equals(receiver)) {
                o.sendMsg("private [" + sender + " ] to [ " + receiver + " ] :" + str);
            }
        }
    }
    //    End of my code, home work of Lesson 8, task 1

    //    Start of my code, home work of Lesson 7, task 2
    public boolean isNickOn(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) return true;
        }
        return false;
    }
    //    End of my code, home work of Lesson 8, task 2

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");

        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }

        String msg = sb.toString();

        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

}
