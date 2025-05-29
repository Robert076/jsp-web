package com.urlmanager.filter;

import com.urlmanager.dao.SessionDAO;
import com.urlmanager.model.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    private static final String[] PUBLIC_PATHS = {
        "/login",
        "/register",
        "/css/",
        "/js/",
        "/images/",
        "/api/popular-urls"
    };
    
    private SessionDAO sessionDAO;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionDAO = new SessionDAO();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // Check if the path is public
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check for session cookie
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            Optional<Cookie> sessionCookie = Arrays.stream(cookies)
                    .filter(cookie -> "sessionId".equals(cookie.getName()))
                    .findFirst();
            
            if (sessionCookie.isPresent()) {
                try {
                    Optional<Session> session = sessionDAO.findById(sessionCookie.get().getValue());
                    if (session.isPresent() && !session.get().isExpired()) {
                        chain.doFilter(request, response);
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // No valid session found, redirect to login
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
    }
    
    @Override
    public void destroy() {
        // Clean up resources if needed
    }
    
    private boolean isPublicPath(String path) {
        return Arrays.stream(PUBLIC_PATHS)
                .anyMatch(publicPath -> path.startsWith(publicPath));
    }
} 