package com.urlmanager.dao;

import com.urlmanager.model.Url;
import com.urlmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlDAO {
    
    public Url create(Url url) throws SQLException {
        String sql = "INSERT INTO urls (user_id, url, title, description) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, url.getUserId());
            stmt.setString(2, url.getUrl());
            stmt.setString(3, url.getTitle());
            stmt.setString(4, url.getDescription());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    url.setId(rs.getLong(1));
                }
            }
            
            return url;
        }
    }
    
    public Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUrl(rs));
                }
            }
        }
        
        return Optional.empty();
    }
    
    public List<Url> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM urls WHERE user_id = ? ORDER BY created_at DESC";
        List<Url> urls = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    urls.add(mapResultSetToUrl(rs));
                }
            }
        }
        
        return urls;
    }
    
    public List<Url> findMostPopular(int limit) throws SQLException {
        String sql = "SELECT * FROM urls ORDER BY visit_count DESC LIMIT ?";
        List<Url> urls = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    urls.add(mapResultSetToUrl(rs));
                }
            }
        }
        
        return urls;
    }
    
    public void update(Url url) throws SQLException {
        String sql = "UPDATE urls SET url = ?, title = ?, description = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, url.getUrl());
            stmt.setString(2, url.getTitle());
            stmt.setString(3, url.getDescription());
            stmt.setLong(4, url.getId());
            stmt.setLong(5, url.getUserId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(Long id, Long userId) throws SQLException {
        String sql = "DELETE FROM urls WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.setLong(2, userId);
            
            stmt.executeUpdate();
        }
    }
    
    public void incrementVisitCount(Long id) throws SQLException {
        String sql = "UPDATE urls SET visit_count = visit_count + 1 WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
        }
    }
    
    private Url mapResultSetToUrl(ResultSet rs) throws SQLException {
        Url url = new Url();
        url.setId(rs.getLong("id"));
        url.setUserId(rs.getLong("user_id"));
        url.setUrl(rs.getString("url"));
        url.setTitle(rs.getString("title"));
        url.setDescription(rs.getString("description"));
        url.setVisitCount(rs.getInt("visit_count"));
        url.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return url;
    }
} 