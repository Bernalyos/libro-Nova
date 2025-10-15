/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.codeup.libronova;


import com.codeup.libronova.Confing.ConnectinFactory;
import com.codeup.libronova.UI.AppUI;
import com.codeup.libronova.UI.LoginApp;
import com.codeup.libronova.domain.User;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.repository.BookRepositoryImpl;
import com.codeup.libronova.repository.LoanRepositoryImpl;
import com.codeup.libronova.repository.MemberRepositoryImpl;
import com.codeup.libronova.repository.UserRepositoryIpml;
import com.codeup.libronova.service.BookService;
import com.codeup.libronova.service.BookServiceImpl;
import com.codeup.libronova.service.LoanService;
import com.codeup.libronova.service.LoanServiceImpl;
import com.codeup.libronova.service.MemberService;
import com.codeup.libronova.service.MemberServiceImpl;
import com.codeup.libronova.service.UserService;
import com.codeup.libronova.service.UserServiceImpl;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Coder
 */
public class LibroNova {

    
   public static void main(String[] args) {

        SwingUtilities.invokeLater(LibroNova::iniciarApp); 
    }

    private static void iniciarApp() {
        try (Connection conn = ConnectinFactory.getConecction()) {

            System.out.println("✅ Conexión exitosa a la base de datos.");
            
            BookService bookService = new BookServiceImpl();
            MemberService memberService = new MemberServiceImpl();
            LoanService loanService = new LoanServiceImpl();
            
            User usuarioLogueado = LoginApp.iniciarSesion(); 
            
            if (usuarioLogueado != null) {
                AppUI app = new AppUI(bookService, memberService, loanService);
                
                JOptionPane.showMessageDialog(null, "📚 Bienvenido a LibroNova, " + usuarioLogueado.getUsername() + "!");
                app.start();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error de conexión a la base de datos: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error inesperado durante la inicialización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }}}
    

    
       




