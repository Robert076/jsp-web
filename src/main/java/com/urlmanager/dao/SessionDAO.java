package com.urlmanager.dao;

import com.urlmanager.model.Session;
import com.urlmanager.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class SessionDAO {
    
    public Session create(Session session) throws SQLException {
        String sql = "INSERT INTO sessions (id, user_id, expires_at) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, session.getId());
            stmt.setLong(2, session.getUserId());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getExpiresAt()));
            
            stmt.executeUpdate();
            
            return session;
        }
    }
    
    public Optional<Session> findById(String id) throws SQLException {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Session session = new Session();
                    session.setId(rs.getString("id"));
                    session.setUserId(rs.getLong("user_id"));
                    session.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    session.setExpiresAt(rs.getTimestamp("expires_at").toLocalDateTime());
                    return Optional.of(session);
                }
            }
        }
        
        return Optional.empty();
    }
    
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM sessions WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteExpired() throws SQLException {
        String sql = "DELETE FROM sessions WHERE expires_at < ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            
            stmt.executeUpdate();
        }
    }
} 