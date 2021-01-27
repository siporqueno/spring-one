package com.porejemplo;

import com.porejemplo.persist.User;
import com.porejemplo.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class AllUsersServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<table>");
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<th>id</th>");
        resp.getWriter().println("<th>username</th>");
        resp.getWriter().println("</tr>");
        for (User user : userRepository.findAll()) {
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>" + user.getId() + "</td>");
            resp.getWriter().println("<td>" + user.getUsername() + "</td>");
            resp.getWriter().println("</tr>");
        }
        resp.getWriter().println("</table>");
    }
}