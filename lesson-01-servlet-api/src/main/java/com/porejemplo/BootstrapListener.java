package com.porejemplo;

import com.porejemplo.persist.User;
import com.porejemplo.persist.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        UserRepository userRepository = new UserRepository();
        sc.setAttribute("userRepository", userRepository);

        userRepository.insert(new User("user1"));
        userRepository.insert(new User("user2"));
        userRepository.insert(new User("user3"));
        userRepository.insert(new User("user4"));
        userRepository.insert(new User("user5"));
    }
}
