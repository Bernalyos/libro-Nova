/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.codeup.libronova;

import static com.codeup.libronova.Confing.ConnectinFactory.getConecction;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Coder
 */
public class LibroNova {

    public static void main(String[]args) {
         try (Connection conn = getConecction()) {
            System.out.println("✅ Conexión exitosa a la base de datos: " );
        } catch (SQLException e) {
            e.printStackTrace();
        }
 }
}

