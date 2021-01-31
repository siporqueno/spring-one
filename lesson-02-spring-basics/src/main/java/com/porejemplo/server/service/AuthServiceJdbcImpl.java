package com.porejemplo.server.service;

import com.porejemplo.server.entity.User;
import com.porejemplo.server.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("authService")
public class AuthServiceJdbcImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceJdbcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getNickByLoginAndPass(String login, String pass) {
        try {
            return userRepository.findByLoginAndPass(login, pass).getNickname();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            return userRepository.save(new User(login, password, nickname));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void disconnect() {
        try {
            userRepository.disconnect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
