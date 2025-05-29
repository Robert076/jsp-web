package com.urlmanager.servlet;

import com.urlmanager.dao.UserDAO;
import com.urlmanager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        
        // Validate input
        if (!validateInput(username, password, confirmPassword, email)) {
            request.setAttribute("error", "Invalid input. Please check your data.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            return;
        }
        
        try {
            // Check if username or email already exists
            if (userDAO.findByUsername(username).isPresent()) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
                return;
            }
            
            // Create new user
            User user = new User(username, password, email);
            userDAO.create(user);
            
            // Redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }
    
    private boolean validateInput(String username, String password, String confirmPassword, String email) {
        if (username == null || username.length() < 3 || username.length() > 50) {
            return false;
        }
        
        if (password == null || password.length() < 6) {
            return false;
        }
        
        if (!password.equals(confirmPassword)) {
            return false;
        }
        
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }
        
        return true;
    }
} 