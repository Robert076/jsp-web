package com.urlmanager.servlet;

import com.urlmanager.dao.SessionDAO;
import com.urlmanager.dao.UrlDAO;
import com.urlmanager.dao.UserDAO;
import com.urlmanager.model.Session;
import com.urlmanager.model.Url;
import com.urlmanager.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UrlServlet extends HttpServlet {
    private UrlDAO urlDAO;
    private SessionDAO sessionDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        urlDAO = new UrlDAO();
        sessionDAO = new SessionDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List user's URLs
                Long userId = getUserIdFromSession(request);
                if (userId != null) {
                    List<Url> urls = urlDAO.findByUserId(userId);
                    request.setAttribute("urls", urls);
                    request.getRequestDispatcher("/views/urls.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            } else if (pathInfo.equals("/popular")) {
                // Show popular URLs
                Long userId = getUserIdFromSession(request);
                int limit = 10; // Default for guests
                if (userId != null) {
                    Optional<User> userOpt = userDAO.findById(userId);
                    if (userOpt.isPresent()) {
                        limit = userOpt.get().getMaxPopularUrls();
                    }
                }
                List<Url> popularUrls = urlDAO.findMostPopular(limit);
                request.setAttribute("urls", popularUrls);
                request.setAttribute("isPopular", true);
                request.getRequestDispatcher("/views/urls.jsp").forward(request, response);
            } else if (pathInfo.startsWith("/edit/")) {
                // Edit URL form
                Long urlId = Long.parseLong(pathInfo.substring(6));
                Long userId = getUserIdFromSession(request);
                if (userId != null) {
                    Optional<Url> urlOpt = urlDAO.findById(urlId);
                    if (urlOpt.isPresent() && urlOpt.get().getUserId().equals(userId)) {
                        request.setAttribute("url", urlOpt.get());
                        request.getRequestDispatcher("/views/edit-url.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Create new URL
                Long userId = getUserIdFromSession(request);
                if (userId != null) {
                    String url = request.getParameter("url");
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    if (url == null || url.isEmpty() || title == null || title.isEmpty()) {
                        request.setAttribute("error", "URL and Title are required.");
                        doGet(request, response);
                        return;
                    }
                    Url newUrl = new Url(userId, url, title, description);
                    urlDAO.create(newUrl);
                    response.sendRedirect(request.getContextPath() + "/urls");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            } else if (pathInfo.startsWith("/edit/")) {
                // Update URL
                Long urlId = Long.parseLong(pathInfo.substring(6));
                Long userId = getUserIdFromSession(request);
                if (userId != null) {
                    Optional<Url> urlOpt = urlDAO.findById(urlId);
                    if (urlOpt.isPresent() && urlOpt.get().getUserId().equals(userId)) {
                        Url url = urlOpt.get();
                        url.setUrl(request.getParameter("url"));
                        url.setTitle(request.getParameter("title"));
                        url.setDescription(request.getParameter("description"));
                        urlDAO.update(url);
                        response.sendRedirect(request.getContextPath() + "/urls");
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            } else if (pathInfo.startsWith("/delete/")) {
                // Delete URL
                Long urlId = Long.parseLong(pathInfo.substring(8));
                Long userId = getUserIdFromSession(request);
                if (userId != null) {
                    Optional<Url> urlOpt = urlDAO.findById(urlId);
                    if (urlOpt.isPresent() && urlOpt.get().getUserId().equals(userId)) {
                        urlDAO.delete(urlId, userId);
                        response.sendRedirect(request.getContextPath() + "/urls");
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long getUserIdFromSession(HttpServletRequest request) throws SQLException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    Optional<Session> session = sessionDAO.findById(cookie.getValue());
                    if (session.isPresent() && !session.get().isExpired()) {
                        return session.get().getUserId();
                    }
                }
            }
        }
        return null;
    }
} 