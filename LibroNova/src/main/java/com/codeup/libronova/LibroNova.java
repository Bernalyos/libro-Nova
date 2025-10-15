

package com.codeup.libronova;


import com.codeup.libronova.Confing.ConnectinFactory;

import com.codeup.libronova.Repository.BookRepositoryImpl;
import com.codeup.libronova.Repository.LoanRepositoryImpl;
import com.codeup.libronova.Repository.MemberRepositoryImpl;
import com.codeup.libronova.Service.BookService;
import com.codeup.libronova.Service.BookServiceImpl;
import com.codeup.libronova.Service.LoanService;
import com.codeup.libronova.Service.LoanServiceImpl;
import com.codeup.libronova.Service.MemberService;
import com.codeup.libronova.Service.MemberServiceImpl;
import com.codeup.libronova.UI.AppUI;

import com.codeup.libronova.UI.loginApp;
import com.codeup.libronova.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Coder
 */
public class LibroNova {

    
   public static void main(String[] args) {

        iniciarApp();
    }

    private static void iniciarApp() {
        try (Connection conn = ConnectinFactory.getConnection()) {

            System.out.println("✅ Conexión exitosa a la base de datos.");

            // Instancia los servicios con sus repositorios
            BookService bookService = new BookServiceImpl(new BookRepositoryImpl());
            MemberService memberService = new MemberServiceImpl(new MemberRepositoryImpl());
            LoanService loanService = new LoanServiceImpl(new LoanRepositoryImpl());

            User usuarioLogueado = loginApp.iniciarSesion();

            if (usuarioLogueado != null) {
                AppUI app = new AppUI(bookService, memberService, loanService);

                JOptionPane.showMessageDialog(null, "📚 Bienvenido a LibroNova, " + usuarioLogueado.getUserName() + "!");
                app.start();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error de conexión a la base de datos: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error inesperado durante la inicialización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }}

    
       




