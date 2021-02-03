package com.porejemplo.server;

import com.porejemplo.server.service.AuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private AuthService authService;
    DataOutputStream out;
    DataInputStream in;
    private String nick;

    public ClientHandler(Server server, Socket socket, AuthService authService) {
        try {
            this.server = server;
            this.socket = socket;
            this.authService = authService;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    // ставим таймаут по бездействию на сокет
                    socket.setSoTimeout(120000);

                    // цикл авторизации.
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] token = str.split(" ");
                            String newNick =
                                    authService.getNickByLoginAndPass(token[1], token[2]);
                            //      Start of my code, home work of Lesson 7, task 2
                            if (newNick != null && !server.isNickOn(newNick))
                            //      End of my code, home work of Lesson 7, task 2
                            {
//                                sendMsg("/authok");
                                // will pass nick to the client
                                nick = newNick;
                                sendMsg("/authok " + nick);
                                server.subscribe(this);

                                //            Start of my code, class work of Lesson 9
                                try {
                                    socket.setSoTimeout(0);
                                } catch (SocketException e1) {
                                    e1.printStackTrace();
                                }
                                //            End of my code, class work of Lesson 9

                                break;
                            } else {
                                //      Start of my code, home work of Lesson 7, task 2
                                sendMsg(server.isNickOn(newNick) ? "Such User is already authorized" : "Wrong login / password");
                                //      End of my code, home work of Lesson 7, task 2
                            }
                        }
                        // регистрация
                        if (str.startsWith("/reg ")) {
                            String[] token = str.split(" ");
                            if (authService.registration(token[1], token[2], token[3]))
                                sendMsg("Registration has been successful");
                            else sendMsg("Registration has failed");
                        }

                        //            Start of my code, home work of Lesson 8
                       /* if (str.equals("/timeout120sec")) {
                            out.writeUTF("/timeout120sec");
                            break;
                        }*/
                        //            End of my code, home work of Lesson 8
                    }

                    //Цикл для работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.equals("/end")) {
                            out.writeUTF("/end");
//                            System.out.println("Клиент отключился");
                            break;
                        }

//                        Start of my code, home work of Lesson 7, task 1
                        if (str.startsWith("/w")) {
//                            + в строке ниже это из регулярных выражений. Значит, что пробел может повторяться один или БОЛЕЕ раз!!!
                            String[] token = str.split(" +", 3);
                            server.sendMsgFromNickToNick(token[2], getNick(), token[1]);
                        } else {
                            System.out.println(str);
                            server.broadcastMsg(str, getNick());
                        }
//                        End of my code, home work of Lesson 7, task 1

                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Таймаут (время на подключение клиенту) истек socket.hashCode() " + this.hashCode());
                    sendMsg("/end");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String str) {
        try {
            out.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
