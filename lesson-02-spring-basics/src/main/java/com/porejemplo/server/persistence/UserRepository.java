package com.porejemplo.server.persistence;

import com.porejemplo.server.entity.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserRepository {

    private final Connection conn;
    private final Statement stmt;

    public UserRepository(DataSource ds) throws SQLException {
        conn = ds.getConnection();
        stmt = conn.createStatement();
    }

    public boolean save(User user) throws SQLException {
        String sql = String.format("INSERT INTO main(login, password, nickname)\n" +
                "VALUES('%s','%s','%s');", user.getLogin(), user.getPassword(), user.getNickname());
        int count = 0;
        count = stmt.executeUpdate(sql);
        return count > 0;
    }

    public User findByLoginAndPass(String login, String pass) throws SQLException {
        String sql = String.format("SELECT nickname FROM main\n" +
                "WHERE login = '%s'\n" +
                "AND password = '%s'", login, pass);

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) return new User(login, pass, rs.getString(1));
        return null;
    }

    public void disconnect() throws SQLException {
        conn.close();
    }
}
