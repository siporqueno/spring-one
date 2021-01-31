package com.porejemplo.server.persistence;

import com.porejemplo.server.entity.Message;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class MessageRepository {

    private final Connection conn;
    private final Statement stmt;

    public MessageRepository(DataSource ds) throws SQLException {
        conn = ds.getConnection();
        stmt = conn.createStatement();
    }

    public void save(Message msg) throws SQLException {
        String sql = String.format("INSERT INTO messages(sender, receiver, text, date)\n" +
                "VALUES('%s','%s','%s', '%s');", msg.getSender(), msg.getReceiver(), msg.getText(), msg.getDate());
        stmt.executeUpdate(sql);
    }

    public String findMessagesByNick(String nick) throws SQLException {
        String sql = String.format("SELECT * FROM messages\n" +
                "WHERE sender='%s'\n" +
                "OR receiver='%s'\n" +
                "OR receiver='null';", nick, nick);

        StringBuilder sb = new StringBuilder();

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String sender = rs.getString(2);
            String receiver = rs.getString(3);
            String text = rs.getString(4);
            String date = rs.getString(5);

            if (receiver.equals(null)) {
                sb.append(sender + " : " + text + "\n");
            } else {
                sb.append("private [" + sender + " ] to [ " + receiver + " ] :" + text + "\n");
            }
        }
        return sb.toString();
    }

    public void disconnect() throws SQLException {
        conn.close();
    }
}
