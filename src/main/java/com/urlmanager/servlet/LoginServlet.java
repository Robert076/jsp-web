package com.urlmanager.servlet;

import com.urlmanager.dao.SessionDAO;
import com.urlmanager.dao.UserDAO;
import com.urlmanager.model.Session;
import com.urlmanager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private SessionDAO sessionDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        sessionDAO = new SessionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            if (userDAO.verifyPassword(username, password)) {
                Optional<User> userOpt = userDAO.findByUsername(username);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    
                    // Create session
                    String sessionId = UUID.randomUUID().toString();
                    LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
                    Session session = new Session(sessionId, user.getId(), expiresAt);
                    sessionDAO.create(session);
                    
                    // Set session cookie
                    Cookie sessionCookie = new Cookie("sessionId", sessionId);
                    sessionCookie.setHttpOnly(true);
                    sessionCookie.setPath("/");
                    sessionCookie.setMaxAge(24 * 60 * 60); // 24 hours
                    response.addCookie(sessionCookie);
                    
                    response.sendRedirect(request.getContextPath() + "/urls");
                    return;
                }
            }
            
            // Invalid credentials
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
} 