
package com.codeup.libronova.Confing;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;


/**
 *
 * @author Coder
 */
public class ConnectinFactory {
     
    public static Connection getConnection() throws SQLException{
        
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

    public static DataSource getDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/tu_basededatos?serverTimezone=UTC");
        ds.setUser("tu_usuario");
        ds.setPassword("tu_contraseña");
        return ds;
    }
}      
       

   

