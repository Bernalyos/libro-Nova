
package com.codeup.libronova.Repository;

import com.codeup.libronova.Confing.ConnectinFactory;
import com.codeup.libronova.domain.Member;
import com.codeup.libronova.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public class MemberRepositoryImpl implements MemberRepository {
    
    
    @Override
    public void save(Member member) throws PersistenceException {
        String sql = """
            INSERT INTO member (name, active, deleted, role, access_level, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, NOW(), NOW())
            """;

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setBoolean(2, member.isActive());
            ps.setBoolean(3, member.isDeleted());
            ps.setString(4, member.getRole().name());
            ps.setString(5, member.getAccessLevel().name());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error adding member: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Member member) throws PersistenceException {
        String sql = """
            UPDATE member
            SET name=?, active=?, deleted=?, role=?, access_level=?, updated_at=NOW()
            WHERE id=?
            """;

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, member.getName());
            ps.setBoolean(2, member.isActive());
            ps.setBoolean(3, member.isDeleted());
            ps.setString(4, member.getRole().name());
            ps.setString(5, member.getAccessLevel().name());
            ps.setInt(6, member.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error updating member: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws PersistenceException {
        String sql = "UPDATE member SET deleted=TRUE, updated_at=NOW() WHERE id=?";

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error deleting member: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Member> findById(int id) throws PersistenceException {
        String sql = "SELECT * FROM member WHERE id=? AND deleted=FALSE";

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractMember(rs));
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error finding member by ID: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public List<Member> findAll() throws PersistenceException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE deleted=FALSE ORDER BY name";

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(extractMember(rs));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error listing members: " + e.getMessage(), e);
        }

        return members;
    }

    @Override
    public List<Member> findActiveMembers() throws PersistenceException { // CAMBIO 1: Lanza PersistenceException
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM member WHERE active=TRUE AND deleted=FALSE ORDER BY name";

        try (Connection conn = ConnectinFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(extractMember(rs));
            }

        } catch (SQLException e) {
            // CAMBIO 2: Traduce SQLException a PersistenceException
            throw new PersistenceException("Error listing active members: " + e.getMessage(), e);
        }

        return members;
    }
   
 

    // --- Private helper for object mapping ---
    private Member extractMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setActive(rs.getBoolean("active"));
        member.setDeleted(rs.getBoolean("deleted"));

        // Enums
        String role = rs.getString("role");
        String access = rs.getString("access_level");
        if (role != null) {
            member.setRole(Member.Role.valueOf(role));
        }
        if (access != null) {
            member.setAccessLevel(Member.AccessLevel.valueOf(access));
        }

        // Dates
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) {
            member.setCreatedAt(created.toLocalDateTime());
        }
        if (updated != null) {
            member.setUpdatedAt(updated.toLocalDateTime());
        }

        return member;
    }
    
    @Override
    public void deleteById(int id) throws PersistenceException {
    String sql = "DELETE FROM members WHERE id = ?";
    try (Connection conn = ConnectinFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new PersistenceException("Error al eliminar miembro", e);
    }
}
}

    

