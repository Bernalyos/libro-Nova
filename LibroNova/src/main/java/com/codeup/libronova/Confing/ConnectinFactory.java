
package com.codeup.libronova.Confing;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 *
 * @author Coder
 */
public class ConnectinFactory {
     
    public static Connection getConecction() throws SQLException{
        
        try (InputStream input = ConnectinFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(input);
            
            String vendor = props.getProperty("db.vendor");
            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String db = props.getProperty("db.db");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            String url = String.format(
                    "jdbc:%s://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    vendor, host, port, db
            );

            return DriverManager.getConnection(url, user, pass);
            
        }catch (Exception e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
        }

    }
            
       
}
   

