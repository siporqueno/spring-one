package com.porejemplo;

import com.porejemplo.persist.User;
import com.porejemplo.persist.UserRepository;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.persistence.EntityManagerFactory;

public class DemoOfUserRepository {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        UserRepository userRepository = new UserRepository(emFactory);

        userRepository.insert(new User("Sergey", "pass1", "serg@mail.aa"));
        userRepository.insert(new User("Egor", "pass2", "egor@mail.bb"));
        userRepository.insert(new User("Maria", "pass3", "masha@mail.cc"));
        userRepository.insert(new User("Maria", "pass3", "masha@mail.cc"));

        System.out.println(userRepository.findAll());

        User secondUser = userRepository.findById(2L);
        System.out.println(secondUser);

        secondUser.setEmail("egor@hotmail.com");
        userRepository.update(secondUser);
        userRepository.update(new User("Bob", "pass4", "bob@gmail.dd"));

        userRepository.delete(1L);

        userRepository.delete(500L);

        System.out.println(userRepository.findAll());

        emFactory.close();
    }
}
