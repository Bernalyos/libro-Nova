
package com.codeup.libronova.Repository;

import com.codeup.libronova.Confing.ConnectinFactory;
import com.codeup.libronova.domain.User;
import com.codeup.libronova.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yoshi
 */
public class UserRepositoryImpl implements UserRepository{
    
    // ...existing code...
@Override
public User login(String username, String password) throws PersistenceException {
    String sql = "SELECT * FROM usuarios WHERE username=? AND password=?";
    try (Connection conn = ConnectinFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, password);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Usa el constructor corregido
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("rol"));
            }
        }
    } catch (SQLException e) {
        throw new PersistenceException("Error al verificar usuario: " + e.getMessage());
    }
    return null;
}
// ...existing code...
}
