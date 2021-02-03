package com.porejemplo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        Server chatServer = context.getBean("chatServer", Server.class);
        chatServer.start(8189);
    }
}
