package com.porejemplo;

import com.porejemplo.persist.User;
import com.porejemplo.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
public class AllUsersServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>username</th>");
        out.println("</tr>");
        for (User user : userRepository.findAll()) {
            out.println("<tr>");
            out.println("<td>" + user.getId() + "</td>");
            out.println("<td>" + user.getUsername() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.close();
    }
}
