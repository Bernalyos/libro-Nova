
package com.codeup.libronova.UI;

import com.codeup.libronova.Repository.UserRepository;
import com.codeup.libronova.Repository.UserRepositoryImpl;
import com.codeup.libronova.domain.User;
import javax.swing.JOptionPane;

/**
 *
 * @author yoshi
 */
public class loginApp {

    public static User iniciarSesion() {
        UserRepository userRepo = new UserRepositoryImpl();
        for (int i = 0; i < 3; i++) {
            String username = JOptionPane.showInputDialog(null, "Usuario:", "Login", JOptionPane.QUESTION_MESSAGE);
            if (username == null) return null;
            String password = JOptionPane.showInputDialog(null, "Contraseña:", "Login", JOptionPane.QUESTION_MESSAGE);
            if (password == null) return null;

            try {
                User user = userRepo.login(username, password);
                if (user != null) {
                    return user;
                } else {
                    JOptionPaneHelper.error("Usuario o contraseña incorrectos.");
                }
            } catch (Exception e) {
                JOptionPaneHelper.error("Error al iniciar sesión: " + e.getMessage());
                return null;
            }
        }
        JOptionPaneHelper.warning("Demasiados intentos fallidos.");
        return null;
    }
}