package com.porejemplo.server.service;

public interface AuthService {

    String getNickByLoginAndPass(String login, String pass);

    boolean registration(String login, String password, String nickname);

    void disconnect();

}
